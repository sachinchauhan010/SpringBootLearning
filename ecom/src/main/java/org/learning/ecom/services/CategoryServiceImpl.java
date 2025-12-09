package org.learning.ecom.services;

import org.learning.ecom.exceptions.APIException;
import org.learning.ecom.exceptions.ResourceNotFound;
import org.learning.ecom.models.Category;
import org.learning.ecom.payload.CategoryDTO;
import org.learning.ecom.payload.CategoryResponse;
import org.learning.ecom.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

//    private final List<Category> categories = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails= PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();

        if (categories.isEmpty()) {
            throw new ResourceNotFound();
        }

        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        // wrap inside CategoryResponse
        CategoryResponse response = new CategoryResponse();
        response.setContent(categoryDTOs);
        response.setPageNumber(categoryPage.getNumber());
        response.setPageSize(categoryPage.getSize());
        response.setTotalElements(categoryPage.getTotalElements());
        response.setTotalPages((long) categoryPage.getTotalPages());
        response.setLastPage(categoryPage.isLast());

        return response;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        //Map CategoryDTO to Category
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category isExistCategory= categoryRepository.findByCategoryName(category.getCategoryName());
        if(isExistCategory!=null){
            throw new APIException("Category with this name "+ categoryDTO.getCategoryName()+ " already exists");
        }
       Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }

    @Override
    public CategoryResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFound("category","CategoryId", categoryId));
        categoryRepository.delete(category);

        List<Category> categoryList=categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs= categoryList.stream()
                .map(cat -> modelMapper.map(cat, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);
        return categoryResponse;
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO newCategoryDataDTO) {
        Category categoryData= modelMapper.map(newCategoryDataDTO, Category.class);
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", "categoryId", categoryId));

        existingCategory.setCategoryName(categoryData.getCategoryName());

        Category updateCategory= categoryRepository.save(existingCategory);
        return modelMapper.map(updateCategory, CategoryDTO.class);
    }

}
