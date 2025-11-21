package org.learning.ecom.controllers;


import jakarta.validation.Valid;
import org.learning.ecom.config.AppConstant;
import org.learning.ecom.exceptions.ResourceNotFound;
import org.learning.ecom.models.Category;
import org.learning.ecom.payload.CategoryDTO;
import org.learning.ecom.payload.CategoryResponse;
import org.learning.ecom.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/public/categories")
    public CategoryResponse getCategories(
            @RequestParam (name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam (name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam (name = "sortBy", defaultValue = AppConstant.SORT_CATEGORY_BY, required = false) String sortBy,
            @RequestParam (name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder
            ) {
        return categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
    }

    @PostMapping("/api/public/add-category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO category) {
        CategoryDTO savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/delete/{categoryId}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long categoryId) {
        CategoryResponse response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(response);  // HTTP 200 OK
    }


    @PutMapping("/api/admin/categories/update/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO newCategory) {

        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, newCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/api/echo")
    public String echoMessage(@RequestParam(name = "message", required = false) String message){
        return "Eched Message: " + message;
    }

}
