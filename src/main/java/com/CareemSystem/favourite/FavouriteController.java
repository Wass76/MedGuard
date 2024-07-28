package com.CareemSystem.favourite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/favourite-bicycles")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Favourite")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("{id}")
    @Operation(
            description = "Get favourite item by id from our system",
            summary = "Get favourite item",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "There is no item with this id",
                            responseCode = "204"
                    )
            }
    )
    public ResponseEntity<?> getFavouriteById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(favouriteService.getFavouritesById(id));
    }

    @GetMapping("by-clientId/{clientId}")
    @Operation(
            description = "Get favourite item by client id from our system",
            summary = "Get favourite items for user",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "There is no item with this id",
                            responseCode = "204"
                    )
            }
    )
    public ResponseEntity<?> getFavouriteByClientId(@PathVariable Integer clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(favouriteService.getFavouritesByClient(clientId));
    }
    @GetMapping("by-favouriteId/{bicycleId}")
    @Operation(
            description = "Get favourite items by bicycle id from our system",
            summary = "Get users which favour this bicycle",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "There is no item with this id",
                            responseCode = "204"
                    )
            }
    )
    public ResponseEntity<?> getFavouriteByBicycleId(@PathVariable Integer bicycleId) {
        return ResponseEntity.status(HttpStatus.OK).body(favouriteService.getFavouritesByBicycle(bicycleId));
    }
    @PostMapping
    @Operation(
            description = "Add favourite item to our system",
            summary = "Add new favourite item",
            responses = {
                    @ApiResponse(
                            description = "Add item done successfully",
                            responseCode = "200"
                    )
            }
    )
    final public ResponseEntity<?> addFavourite(@RequestBody FavouriteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favouriteService.AddNewFavourite(request));
    }
    @DeleteMapping("{id}")
    @Operation(
            description = "Delete favourite item by id from our system",
            summary = "Delete favourite item",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "There is no item with this id",
                            responseCode = "400"
                    )
            }
    )
    final public ResponseEntity<?> deleteFavourite(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(favouriteService.DeleteFavourite(id));
    }


}
