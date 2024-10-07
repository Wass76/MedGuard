package com.RideShare.hub.Controller;

import com.RideShare.hub.Request.HubContentRequest;
import com.RideShare.hub.Service.HubContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/hub-content")
@RequiredArgsConstructor
@Tag(name = "Hub-Content")
public class HubContentController {
    private final HubContentService hubContentService;

    @GetMapping("{id}")
    @Operation(
            summary = "get content of hub by id"
    )
    public ResponseEntity<?> getHubContent(@PathVariable Integer id , @RequestParam String bicycleCategory) {
        return ResponseEntity.ok().body(hubContentService.getHubContentByHubId(id, bicycleCategory));
    }
//    @PostMapping
//    public ResponseEntity<?> addHubContent(@RequestBody HubContentRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(hubContentService.createHubContent(request));
//    }

    @PutMapping("{id}")
    @Operation(
            summary = "Edit hub content by id {add bicycles or...."
    )
    public ResponseEntity<?> updateHubContent(@PathVariable Integer id, @RequestBody HubContentRequest hubContentRequest) {
        return ResponseEntity.ok().body(hubContentService.updateHubContent(id, hubContentRequest));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete hub content by id",
            hidden = true
    )
    public ResponseEntity<?> deleteHubContent(@PathVariable Integer id) {
        return ResponseEntity.ok().body(hubContentService.deleteHubContent(id));
    }
}

