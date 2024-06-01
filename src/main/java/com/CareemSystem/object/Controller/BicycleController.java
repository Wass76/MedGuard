package com.CareemSystem.object.Controller;

import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Request.BicycleRequest;
import com.CareemSystem.object.Service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bicycle")
public class BicycleController {
    @Autowired
    private BicycleService bicycleService;

    @GetMapping
    public ResponseEntity<?>getAllBicycles() {
        return ResponseEntity.ok( bicycleService.getAllObjects());
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getBicycleById(@PathVariable Integer id) {
        return ResponseEntity.ok( bicycleService.getObjectById(id));
    }
    @PostMapping
    public ResponseEntity<?> createBicycle(@RequestBody BicycleRequest request) {
        return ResponseEntity.ok(bicycleService.createObject(request));
    }
}
