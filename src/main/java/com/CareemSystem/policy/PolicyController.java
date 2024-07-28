package com.CareemSystem.policy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/policy")
@CrossOrigin("*")
@Tag(name = "Policy Of app controller")
@RequiredArgsConstructor
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping()
    public ResponseEntity<?> getPolicy() {
        return ResponseEntity.ok(policyService.getPolicy(1));
    }
    @PostMapping()
    @Operation(
            hidden = true
    )
    public ResponseEntity<?> addPolicy(@RequestBody PolicyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(policyService.createPolicy(request));
    }
    @PutMapping
    public ResponseEntity<?> updatePolicy(@RequestBody PolicyRequest request) {
        return ResponseEntity.ok(policyService.updatePolicy(1, request));
    }
    @DeleteMapping("{id}")
    @Operation(
            hidden = true
    )
    public ResponseEntity<?> deletePolicy(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(policyService.deletePolicy(id));
    }

}
