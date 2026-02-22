package com.sohaib.trackmystacks.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sohaib.trackmystacks.dto.BackupData;
import com.sohaib.trackmystacks.service.BackupService;

/**
 * Admin-only endpoints for backup export and import.
 * These live under /admin/backup so Spring Security's existing
 * rule (.requestMatchers("/admin/**").hasRole("ADMIN")) already
 * gates access — no extra security config needed.
 */
@Controller
@RequestMapping("/admin/backup")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @Autowired
    private ObjectMapper objectMapper;

    // -------------------------------------------------------------------------
    // Export  →  GET /admin/backup/export
    // Returns a pretty-printed JSON file as a browser download.
    // -------------------------------------------------------------------------
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportBackup() {
        try {
            BackupData data = backupService.exportData();
            byte[] json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(data);
            String filename = "trackmystacks-backup-" + LocalDate.now() + ".json";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);

        } catch (Exception e) {
            byte[] error = ("Export failed: " + e.getMessage()).getBytes();
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(error);
        }
    }

    // -------------------------------------------------------------------------
    // Import  →  POST /admin/backup/import
    // Accepts a multipart JSON file upload and restores the snapshot.
    // -------------------------------------------------------------------------
    @PostMapping("/import")
    public String importBackup(
            @RequestParam("backupFile") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a backup file to import.");
            return "redirect:/admin";
        }

        if (!file.getOriginalFilename().endsWith(".json")) {
            redirectAttributes.addFlashAttribute("error", "Only .json backup files are supported.");
            return "redirect:/admin";
        }

        try {
            BackupData data = objectMapper.readValue(file.getBytes(), BackupData.class);
            backupService.importData(data);
            redirectAttributes.addFlashAttribute("success",
                    "Backup imported successfully! All data has been restored from the file.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Import failed — " + e.getMessage() + ". Your existing data has not been changed.");
        }

        return "redirect:/admin";
    }
}
