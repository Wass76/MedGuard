package com.CareemSystem.hub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hubs")
public class HubController {
    @Autowired
    private HubService hubService;

    @GetMapping("")
    public ResponseEntity<?> getAllHubs(@RequestParam Double longtitude, @RequestParam Double latitude) {
        return ResponseEntity.ok(hubService.getAllHubs());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getHubById(@PathVariable Integer id) {
        return ResponseEntity.ok(hubService.getHubById(id));
    }

    @PostMapping
    public ResponseEntity<?> addNewHub(@RequestBody HubRequest request) {
        return ResponseEntity.status(201).body (hubService.createHub(request));
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateHub(@PathVariable Integer id, @RequestBody HubRequest request) {
        return ResponseEntity.status(202).body(hubService.updateHub(id, request));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteHub(@PathVariable Integer id) {
        return ResponseEntity.status(204).body(hubService.deleteHub(id));
    }

}
