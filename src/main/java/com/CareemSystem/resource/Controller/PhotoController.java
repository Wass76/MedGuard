package com.CareemSystem.resource.Controller;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.resource.Request.FileInformationRequest;
import com.CareemSystem.resource.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/photo")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PhotoController {

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping(name = "/upload",consumes = "multipart/form-data")
    public ApiResponseClass uploadPhoto(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file,null);
    }

//    @GetMapping("/download/{fileName:.+}")
//    public ResponseEntity<?> downloadPhoto(@PathVariable String fileName) {
//        return fileStorageService.loadFileAsResource(fileName);
//    }
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadPhotoById(@PathVariable Integer id) {
        return fileStorageService.loadFileAsResponseEntityById(id);
    }



    @PutMapping("{id}")
    public ApiResponseClass updatePhoto(@PathVariable Integer id,@RequestBody FileInformationRequest request) {
        return fileStorageService.updateFile(id,request);
    }
    @GetMapping("all")
    public ApiResponseClass allPhotos() {
        return  fileStorageService.getAllPhotos();
    }

}
