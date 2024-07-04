package com.CareemSystem.hub.Controller;

import com.CareemSystem.hub.Entity.HubContent;
import com.CareemSystem.hub.Request.HubContentRequest;
import com.CareemSystem.hub.Service.HubContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/hub-content")
public class HubContentController {
    private final HubContentService hubContentService;
    @GetMapping("hub-content/{id}")
    public ResponseEntity<?> getAllHubContent(@PathVariable Integer id) {
        return ResponseEntity.ok().body(hubContentService.getHubContentByHubId(id));
    }
    @PostMapping
    public ResponseEntity<?> addHubContent(@RequestBody HubContentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hubContentService.createHubContent(request));
    }
}
