package com.CareemSystem.hub.Controller;

import com.CareemSystem.hub.Request.HubRequest;
import com.CareemSystem.hub.Service.HubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hubs")
@Tag(name = "Hub")
@CrossOrigin(origins = "*")
public class HubController {
    @Autowired
    private HubService hubService;

    @GetMapping("")
    @Operation(
            description = "This endpoint build to Get All Hubs which is in our system",
            summary = "Get All Hubs",
            responses = {
                    @ApiResponse(
                            description ="Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getAllHubs(@RequestParam Double longtitude, @RequestParam Double latitude) {
        return ResponseEntity.ok(hubService.getAllHubs(longtitude,latitude));
    }

    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get hun by id which is in our system",
            summary = "Get hub by id",
            responses = {
                    @ApiResponse(
                            description ="Get hub by idl done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getHubById(@PathVariable Integer id) {
        return ResponseEntity.ok(hubService.getHubById(id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to Create hub which is in our system",
            summary = "Create new hub",
            responses = {
                    @ApiResponse(
                            description ="Create done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> addNewHub(@RequestBody HubRequest request) {
        return ResponseEntity.status(201).body (hubService.createHub(request));
    }
    @PutMapping("{id}")
    @Operation(
            description = "This endpoint build to update hub by id which is in our system",
            summary = "Edit hub by id",
            responses = {
                    @ApiResponse(
                            description ="Edit hub by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> updateHub(@PathVariable Integer id, @RequestBody HubRequest request) {
        return ResponseEntity.status(202).body(hubService.updateHub(id, request));
    }
    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to Delete hub by id which is in our system",
            summary = "Delete hub by id",
            responses = {
                    @ApiResponse(
                            description ="Delete by id done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> deleteHub(@PathVariable Integer id) {
        return ResponseEntity.status(204).body(hubService.deleteHub(id));
    }

}
