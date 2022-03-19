package com.realestate.controller;

import com.realestate.model.dto.AdvertDTO;
import com.realestate.model.response.AdvertResponse;
import com.realestate.model.response.BaseResponse;
import com.realestate.service.AdvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/real-estate/api/advert")
@RequiredArgsConstructor
@Tag(name = "Advert Services", description = "Advert services for some operations with adverts")
public class AdvertController {

    private final AdvertService advertService;

    @GetMapping("/info/{id}")
    @Operation(summary = "Get a advert by its id")
    public ResponseEntity<AdvertResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(advertService.getById(id));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get all adverts by user id")
    public ResponseEntity<List<AdvertDTO>> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(advertService.getAllByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create a new advert")
    public ResponseEntity<AdvertResponse> createNewAdvert(@RequestBody @Valid AdvertDTO advertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(advertService.createNewAdvert(advertDTO));
    }

    @PutMapping
    @Operation(summary = "Update an advert")
    public ResponseEntity<AdvertResponse> updateById(@RequestBody @Valid AdvertDTO advertDTO) {
        return ResponseEntity.ok(advertService.updateById(advertDTO));
    }

    @PutMapping("/active/{id}")
    @Operation(summary = "Update advert status to active")
    public ResponseEntity<BaseResponse> setActiveStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(advertService.setActiveStatusById(id));
    }

    @PutMapping("/passive/{id}")
    @Operation(summary = "Update advert status to passive")
    public ResponseEntity<BaseResponse> setPassiveStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(advertService.setPassiveStatusById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a advert by its id")
    public ResponseEntity<BaseResponse> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(advertService.deleteById(id));
    }

}