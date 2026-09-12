package lk.ijse.veema_computer.service;

import lk.ijse.veema_computer.dto.request.CategoryPatchRequestDTO;
import lk.ijse.veema_computer.dto.request.CategoryRequestDTO;
import lk.ijse.veema_computer.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(
            CategoryRequestDTO request
    );

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id);

    CategoryResponseDTO updateCategory(
            Long id,
            CategoryRequestDTO request
    );

    CategoryResponseDTO patchCategory(
            Long id,
            CategoryPatchRequestDTO request
    );

    void deleteCategory(Long id);
}
