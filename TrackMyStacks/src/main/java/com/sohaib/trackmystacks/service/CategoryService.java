package com.sohaib.trackmystacks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sohaib.trackmystacks.model.Category;
import com.sohaib.trackmystacks.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public Category createCategory(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }
    
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    public boolean categoryExists(String name) {
        return categoryRepository.existsByName(name);
    }
}