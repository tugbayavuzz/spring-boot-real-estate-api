package com.realestate.controller;

import com.realestate.model.dto.UserDTO;
import com.realestate.model.response.BaseResponse;
import com.realestate.model.response.UserResponse;
import com.realestate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/real-estate/api/user")
@RequiredArgsConstructor
@Tag(name = "User Services", description = "User services for some operations with users")
public class UserController {

    private final UserService userService;

    @GetMapping("/info/{id}")
    @Operation(summary = "Get a user by its id")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get a user by its email")
    public ResponseEntity<UserResponse> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponse> createNewUser(@RequestBody @Valid UserDTO userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(userDto));
    }

    @PutMapping
    @Operation(summary = "Update a user")
    public ResponseEntity<UserResponse> updateById(@RequestBody @Valid UserDTO userDto) {
        return ResponseEntity.ok(userService.updateById(userDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by its id")
    public ResponseEntity<BaseResponse> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

}