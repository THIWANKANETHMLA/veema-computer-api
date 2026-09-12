package lk.ijse.veema_computer.controller;


import jakarta.validation.Valid;
import lk.ijse.veema_computer.constant.CommonResponse;
import lk.ijse.veema_computer.constant.ResponseCode;
import lk.ijse.veema_computer.constant.ResponseMessage;
import lk.ijse.veema_computer.dto.request.CategoryPatchRequestDTO;
import lk.ijse.veema_computer.dto.request.CategoryRequestDTO;
import lk.ijse.veema_computer.dto.response.CategoryResponseDTO;
import lk.ijse.veema_computer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<
            CommonResponse<CategoryResponseDTO>
            > createCategory(
            @Valid @RequestBody CategoryRequestDTO request
    ) {
        CategoryResponseDTO category =
                categoryService.createCategory(request);

        CommonResponse<CategoryResponseDTO> response =
                new CommonResponse<>(
                        ResponseCode.CREATED,
                        category,
                        ResponseMessage.RESOURCE_CREATED
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<
            CommonResponse<List<CategoryResponseDTO>>
            > getAllCategories() {

        List<CategoryResponseDTO> categories =
                categoryService.getAllCategories();

        CommonResponse<List<CategoryResponseDTO>> response =
                new CommonResponse<>(
                        ResponseCode.SUCCESS,
                        categories,
                        ResponseMessage.OPERATION_SUCCESS
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<
            CommonResponse<CategoryResponseDTO>
            > getCategoryById(
            @PathVariable Long id
    ) {
        CategoryResponseDTO category =
                categoryService.getCategoryById(id);

        CommonResponse<CategoryResponseDTO> response =
                new CommonResponse<>(
                        ResponseCode.SUCCESS,
                        category,
                        ResponseMessage.OPERATION_SUCCESS
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<
            CommonResponse<CategoryResponseDTO>
            > updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO request
    ) {
        CategoryResponseDTO category =
                categoryService.updateCategory(id, request);

        CommonResponse<CategoryResponseDTO> response =
                new CommonResponse<>(
                        ResponseCode.SUCCESS,
                        category,
                        ResponseMessage.RESOURCE_UPDATED
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<
            CommonResponse<CategoryResponseDTO>
            > patchCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryPatchRequestDTO request
    ) {
        CategoryResponseDTO category =
                categoryService.patchCategory(id, request);

        CommonResponse<CategoryResponseDTO> response =
                new CommonResponse<>(
                        ResponseCode.SUCCESS,
                        category,
                        ResponseMessage.RESOURCE_UPDATED
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);

        CommonResponse<Void> response =
                new CommonResponse<>(
                        ResponseCode.SUCCESS,
                        null,
                        ResponseMessage.RESOURCE_DELETED
                );

        return ResponseEntity.ok(response);
    }
}
