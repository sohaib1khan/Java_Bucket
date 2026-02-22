package com.sohaib.trackmystacks.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.service.PaycheckService;
import com.sohaib.trackmystacks.service.UserService;

@Controller
@RequestMapping("/paychecks")
public class PaycheckController {

    @Autowired
    private PaycheckService paycheckService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addPaycheck(
            @RequestParam BigDecimal amount,
            @RequestParam String month,        // "yyyy-MM" from <input type="month">
            @RequestParam(required = false, defaultValue = "") String description,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        User user = userService.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Parse "yyyy-MM" → first of that month
        LocalDate monthDate = LocalDate.parse(month + "-01");
        paycheckService.addPaycheck(user, amount, monthDate, description.isBlank() ? null : description);
        redirectAttributes.addFlashAttribute("success", "Paycheck added for " + monthDate.getMonth().toString().charAt(0)
                + monthDate.getMonth().toString().substring(1).toLowerCase() + " " + monthDate.getYear() + "!");

        return "redirect:/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deletePaycheck(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        paycheckService.deletePaycheck(id);
        redirectAttributes.addFlashAttribute("success", "Paycheck entry deleted.");
        return "redirect:/dashboard";
    }
}
