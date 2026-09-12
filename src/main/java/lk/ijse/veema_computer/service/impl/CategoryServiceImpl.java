package lk.ijse.veema_computer.service.impl;

import lk.ijse.veema_computer.dto.request.CategoryPatchRequestDTO;
import lk.ijse.veema_computer.dto.request.CategoryRequestDTO;
import lk.ijse.veema_computer.dto.response.CategoryResponseDTO;
import lk.ijse.veema_computer.entity.Category;
import lk.ijse.veema_computer.exception.DuplicateResourceException;
import lk.ijse.veema_computer.exception.ResourceNotFoundException;
import lk.ijse.veema_computer.repository.CategoryRepository;
import lk.ijse.veema_computer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log =
            LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(
            CategoryRequestDTO request
    ) {
        String name = normalizeName(request.getName());

        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException(
                    "Category already exists: " + name
            );
        }

        Category category = new Category();
        category.setName(name);
        category.setDescription(
                normalizeDescription(request.getDescription())
        );
        category.setActive(request.isActive());

        Category savedCategory =
                categoryRepository.save(category);

        log.info(
                "Category created successfully with id={}",
                savedCategory.getId()
        );

        return convertToResponse(savedCategory);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        return categoryRepository.findAll(
                        Sort.by(
                                Sort.Direction.ASC,
                                "name"
                        )
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = findCategory(id);

        return convertToResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(
            Long id,
            CategoryRequestDTO request
    ) {
        Category category = findCategory(id);

        String name = normalizeName(request.getName());

        if (categoryRepository
                .existsByNameIgnoreCaseAndIdNot(name, id)) {

            throw new DuplicateResourceException(
                    "Category already exists: " + name
            );
        }

        category.setName(name);
        category.setDescription(
                normalizeDescription(request.getDescription())
        );
        category.setActive(request.isActive());

        Category updatedCategory =
                categoryRepository.save(category);

        log.info("Category updated: id={}", id);

        return convertToResponse(updatedCategory);
    }

    @Override
    @Transactional
    public CategoryResponseDTO patchCategory(
            Long id,
            CategoryPatchRequestDTO request
    ) {
        Category category = findCategory(id);

        if (request.getName() != null) {

            String name = normalizeName(request.getName());

            if (categoryRepository
                    .existsByNameIgnoreCaseAndIdNot(name, id)) {

                throw new DuplicateResourceException(
                        "Category already exists: " + name
                );
            }

            category.setName(name);
        }

        if (request.getDescription() != null) {
            category.setDescription(
                    normalizeDescription(
                            request.getDescription()
                    )
            );
        }

        if (request.getActive() != null) {
            category.setActive(request.getActive());
        }

        Category updatedCategory =
                categoryRepository.save(category);

        log.info("Category partially updated: id={}", id);

        return convertToResponse(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {

        Category category = findCategory(id);

        categoryRepository.delete(category);

        log.info("Category deleted: id={}", id);
    }

    private Category findCategory(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        )
                );
    }

    private String normalizeName(String name) {
        return name.trim();
    }

    private String normalizeDescription(String description) {

        if (description == null
                || description.isBlank()) {
            return null;
        }

        return description.trim();
    }

    private CategoryResponseDTO convertToResponse(
            Category category
    ) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
