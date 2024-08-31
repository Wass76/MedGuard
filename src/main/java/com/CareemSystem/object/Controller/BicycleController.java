package com.CareemSystem.object.Controller;

import com.CareemSystem.object.Request.BicycleRequest;
import com.CareemSystem.object.Service.BicycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/bicycle")
@Tag(name = "Bicycle")
public class BicycleController {
    @Autowired
    private BicycleService bicycleService;

    @GetMapping
    @Operation(
            description = "This endpoint build to Get All bicycles which is in our system",
            summary = "Get All bicycles",
            responses = {
                    @ApiResponse(
                            description ="Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?>getAllBicycles() {
        return ResponseEntity
                .ok( bicycleService.getAllBicyclesForClient());
    }
    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get bicycle by id which is in our system",
            summary = "Get bicycle by id",
            responses = {
                    @ApiResponse(
                            description ="Get bicycle done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getBicycleById(@PathVariable Integer id) {
        return ResponseEntity
                .ok( bicycleService.getBicycleByIdForClient(id));
    }
    @GetMapping("bicycles-by-category")
    @Operation(
            description = "This endpoint build to All bicycles by category name which is in our system",
            summary = "Get All bicycles by category name",
            responses = {
                    @ApiResponse(
                            description ="Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getBicyclesByCategory(@RequestParam String category) {
        return ResponseEntity.ok(bicycleService.getBicycleByCategoryForClient(category));
    }
    @GetMapping("bicycles-categories")
    @Operation(
            description = "This endpoint build to Get All bicycle's category which is in our system",
            summary = "Get All bicycle's category",
            responses = {
                    @ApiResponse(
                            description = "Get bicycle's category done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getCategoriesBicycles() {
        return ResponseEntity.ok(bicycleService.getAllCategories());
    }
//    @PostMapping(consumes = {"multipart/form-data" , "application/json"})
    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            description = "This endpoint build to create new bicycle in our system",
            summary = "Create new bicycle",
            responses = {
                    @ApiResponse(
                            description ="Create done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> createBicycle(@RequestPart BicycleRequest request , @RequestPart  MultipartFile file) throws IOException {
//        file.getContentType();
        System.out.println(request.getType());
        request.setPhoto(file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bicycleService.createObject(request));
    }
    @PutMapping("{id}")
    @Operation(
            description = "This endpoint build to Edit bicycle by id which is in our system",
            summary = "Edit bicycle by id",
            responses = {
                    @ApiResponse(
                            description ="Edit bicycle by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> updateBicycle(@PathVariable Integer id, @RequestBody BicycleRequest request) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(bicycleService.updateObject(id, request));
    }

    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to Delete bicycle by id which is in our system",
            summary = "Delete bicycle by id",
            responses = {
                    @ApiResponse(
                            description ="Delete bicycle by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> deleteBicycle(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(bicycleService.deleteObject(id));
    }

    @GetMapping("discountBicycles")
    @Operation(
            description = "This endpoint build to Get offers which is in our system",
            summary = "Get offers",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "There is no offers now",
                            responseCode = "204"
                    )
            }
    )
    public ResponseEntity<?> getOffers(){
        return ResponseEntity.ok(bicycleService.getOfferBicycles());
    }
}
