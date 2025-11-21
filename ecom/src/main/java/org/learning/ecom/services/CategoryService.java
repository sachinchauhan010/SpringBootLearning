package org.learning.ecom.services;

import org.learning.ecom.payload.CategoryDTO;
import org.learning.ecom.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO category);
    CategoryResponse deleteCategory (Long categoryId);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO NewCategoryDTO);
}
