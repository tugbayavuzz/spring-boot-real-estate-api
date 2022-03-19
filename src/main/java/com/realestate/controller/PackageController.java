package com.realestate.controller;

import com.realestate.model.dto.PackageDTO;
import com.realestate.model.response.BaseResponse;
import com.realestate.model.response.PackageResponse;
import com.realestate.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/real-estate/api/package")
@RequiredArgsConstructor
@Tag(name = "Package Services", description = "Package services for some operations with packages")
public class PackageController {

    private final PackageService packageService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get all packages by user id")
    public ResponseEntity<List<PackageDTO>> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(packageService.findAllByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create a new package")
    public ResponseEntity<PackageResponse> createNewPackage(@RequestBody @Valid PackageDTO packageDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(packageService.createNewPackage(packageDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a package")
    public ResponseEntity<BaseResponse> updateById(@PathVariable Long id) {
        return ResponseEntity.ok(packageService.updateById(id));
    }

}
