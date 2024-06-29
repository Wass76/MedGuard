package com.CareemSystem.object.Controller;

import com.CareemSystem.object.Request.BicycleRequest;
import com.CareemSystem.object.Service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bicycle")
public class BicycleController {
    @Autowired
    private BicycleService bicycleService;

    @GetMapping
    public ResponseEntity<?>getAllBicycles(@RequestParam Long longtitude, @RequestParam Long latitude) {
        return ResponseEntity
                .ok( bicycleService.getAllObjects());
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getBicycleById(@PathVariable Integer id) {
        return ResponseEntity
                .ok( bicycleService.getObjectById(id));
    }
    @GetMapping("bicycles-by-category")
    public ResponseEntity<?> getBicyclesByCategory(@RequestParam String category ,@RequestParam Long longtitude, @RequestParam Long latitude) {
        return ResponseEntity.ok(bicycleService.getObjectByCategory(category));
    }
    @GetMapping("bicycles-categories")
    public ResponseEntity<?> getCategoriesBicycles() {
        return ResponseEntity.ok(bicycleService.getAllCategories());
    }
    @PostMapping
    public ResponseEntity<?> createBicycle(@RequestBody BicycleRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bicycleService.createObject(request));
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateBicycle(@PathVariable Integer id, @RequestBody BicycleRequest request) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(bicycleService.updateObject(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBicycle(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(bicycleService.deleteObject(id));
    }
}
