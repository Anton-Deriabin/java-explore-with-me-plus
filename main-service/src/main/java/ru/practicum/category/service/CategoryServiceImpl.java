package ru.practicum.category.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.NameExistException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
//    EventRepository eventRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        log.info("Creating category with name: {}", newCategoryDto.getName());
        if (categoryRepository.existsByName(newCategoryDto.getName())) {
            log.warn("Category creation failed - name {} already exists", newCategoryDto.getName());
            throw new NameExistException(String.format("Can't create category because name: %s already used by another category", newCategoryDto.getName()));
        }
        Category category = CategoryMapper.toCategory(newCategoryDto);
        CategoryDto savedCategory = CategoryMapper.toCategoryDto(categoryRepository.save(category));
        log.info("Category created successfully: {}", savedCategory);
        return savedCategory;
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        log.info("Fetching categories with from={} and size={}", from, size);
        Pageable page = PageRequest.of(from / size, size);
        List<CategoryDto> categories = categoryRepository.findAll(page)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .toList();
        log.info("Fetched {} categories", categories.size());
        return categories;
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        log.info("Fetching category with id={}", catId);
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> {
                    log.error("Category with id={} not found", catId);
                    return new NotFoundException(String.format("Category with id=%d was not found", catId),
                            "The required object was not found.");
                });
        log.info("Category found: {}", category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        log.info("Updating category with id={} to new name={}", catId, categoryDto.getName());
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> {
                    log.error("Category with id={} not found for update", catId);
                    return new NotFoundException(String.format("Category with id=%d was not found", catId),
                            "The required object was not found.");
                });
        if (categoryRepository.existsByName(categoryDto.getName())) {
            log.warn("Category update failed - name {} already exists", categoryDto.getName());
            throw new NameExistException(String.format("Can't update category because name: %s already used by another category", categoryDto.getName()));
        }
        category.setName(categoryDto.getName());
        CategoryDto updatedCategory = CategoryMapper.toCategoryDto(categoryRepository.save(category));
        log.info("Category updated successfully: {}", updatedCategory);
        return updatedCategory;
    }

//    @Override
//    public void deleteCategory(Long catId) {
//        if (eventRepository.existsByCategoryId(catId)) {
//            throw new CategoryNotEmptyException("The category is not empty");
//        }
//        categoryRepository.deleteById(catId);
//    }

}
