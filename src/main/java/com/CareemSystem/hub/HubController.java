package com.CareemSystem.hub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/hubs")
public class HubController {
    @Autowired
    private HubService hubService;

    @GetMapping
    public ResponseEntity<?> getAllHubs() {
        return ResponseEntity.ok(hubService.getAllHubs());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getHubById(@PathVariable Integer id) {
        return ResponseEntity.ok(hubService.getHubById(id));
    }

}
