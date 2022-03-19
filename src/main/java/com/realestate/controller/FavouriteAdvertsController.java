package com.realestate.controller;

import com.realestate.model.dto.AdvertDTO;
import com.realestate.model.dto.FavouriteAdvertsDTO;
import com.realestate.model.response.AdvertResponse;
import com.realestate.model.response.FavouriteAdvertsResponse;
import com.realestate.service.FavouriteAdvertsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/real-estate/api/favourite-adverts")
@RequiredArgsConstructor
@Tag(name = "Favourite Adverts Services", description = "Favourite Advert services for some operations with adverts")
public class FavouriteAdvertsController {

    private final FavouriteAdvertsService favouriteAdvertsService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get all favourite adverts by user id")
    public ResponseEntity<FavouriteAdvertsResponse> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(favouriteAdvertsService.findAllByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create a new favourite advert")
    public ResponseEntity<FavouriteAdvertsDTO> createNewFavouriteAdvert(@RequestBody @Valid FavouriteAdvertsDTO favouriteAdvertsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favouriteAdvertsService.createNewFavouriteAdvert(favouriteAdvertsDTO));
    }

}
