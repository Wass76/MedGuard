package com.CareemSystem.resource.service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.resource.Enum.ResourceType;
import com.CareemSystem.resource.Model.FileMetaData;
import com.CareemSystem.resource.Repository.FileMetaDataRepository;
import com.CareemSystem.resource.Request.FileInformationRequest;
import java.security.SecureRandom;
import com.CareemSystem.resource.Response.FileMetaDataResponse;
import com.CareemSystem.utils.exception.ApiRequestException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service
//@RequiredArgsConstructor
public class FileStorageService {

    private final Path fileStorageLocation;
    private final FileMetaDataRepository fileMetaDataRepository;
    private final ResourceLoader resourceLoader;
    @Autowired
    private ServerProperties serverProperties;

    private String uploadDir = "src/main/resources/static/images";


    public FileStorageService(@Value("${file: upload-dir}") String uploadDir, FileMetaDataRepository fileMetaDataRepository, ResourceLoader resourceLoader, ServerProperties serverProperties) {
        this.resourceLoader=resourceLoader;
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.fileMetaDataRepository = fileMetaDataRepository;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public ApiResponseClass storeFile(MultipartFile file, ResourceType type) {
        FileMetaData meta = storeFileFromOtherEntity(file, type);
        FileMetaDataResponse response = FileMetaDataResponse.builder()
                .id(meta.getId())
                .filePath(meta.getFilePath())
                .fileName(meta.getFileName())
                .fileType(meta.getFileType())
                .fileSize(meta.getFileSize())
                .relationId(meta.getRelationId())
                .relationType(meta.getRelationType())
                .build();
        return new ApiResponseClass("Photo uploaded successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

//    private static final SecureRandom random = new SecureRandom();
//    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//
//    public static String generateRandomString(int length) {
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(characters.length());
//            sb.append(characters.charAt(index));
//        }
//        return sb.toString();
//    }


    @Transactional
    public FileMetaData storeFileFromOtherEntity(MultipartFile file, ResourceType type) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path uploadPath = Path.of(uploadDir);
            Path filePath = uploadPath.resolve(fileName);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            FileMetaData meta = FileMetaData.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .relationType(type)
                    .build();


            fileMetaDataRepository.save(meta);

            String  server = serverProperties.getAddress().toString();
            String port = serverProperties.getPort().toString();
            System.out.println(server);


            System.out.println(server + "/"+port);

            if (Objects.equals(server, "localhost/127.0.0.1")
                    && Objects.equals(port,"3010")
            ){
                String[] parts = server.split("/");
                String localhost = parts[0];
                String[] dirParts = uploadDir.split("/");
                String publicDir = dirParts[dirParts.length-1];
                meta.setFilePath(localhost+":"+port+ "/"+publicDir + "/"+  fileName);
                // System.out.println(server + "/"+port);
            }

            if (Objects.equals(server, "localhost/127.0.0.1")
                    && Objects.equals(port,"8088")
            ){
                String[] dirParts = uploadDir.split("/");
                String publicDir = dirParts[dirParts.length-1];
                meta.setFilePath("rideshare.devscape.online"+"/" +publicDir +"/"+ fileName );
                // System.out.println(server + "/"+port);
            }
            fileMetaDataRepository.save(meta);
            // System.out.println(meta.getFilePath);


            return  FileMetaData.builder()
                    .id(meta.getId())
                    .fileName(meta.getFileName())
                    .filePath(meta.getFilePath())
                    .fileType(meta.getFileType())
                    .fileSize(meta.getFileSize())
                    .relationId(meta.getRelationId())
                    .relationType(meta.getRelationType())
                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }



    public ResponseEntity<?> loadFileAsResponseEntityById(Integer id) {

        try {
            FileMetaData metaData = fileMetaDataRepository.findById(id).orElseThrow(
                    ()->new ApiRequestException("Photo not found"));
            Path filePath = this.fileStorageLocation.resolve(metaData.getFileName()).normalize();
            System.out.println(filePath);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = "application/octet-stream";
                try {
                    contentType = filePath.toUri().toURL().openConnection().getContentType();
                } catch (IOException ex) {
                    System.out.println("Could not determine file type.");
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new ApiRequestException("Could not find file: " + metaData.getFileName());
            }
        } catch (IOException ex) {
            throw new ApiRequestException("Error: " + ex.getMessage());
        }
    }

    public FileMetaDataResponse loadFileAsFileMetaDataById(Integer id) {
        FileMetaData metaData = fileMetaDataRepository.findById(id).orElseThrow(
                ()->new ApiRequestException("Photo not found")
        );
        return FileMetaDataResponse.builder()
                .id(metaData.getId())
                .fileName(metaData.getFileName())
                .fileType(metaData.getFileType())
                .fileSize(metaData.getFileSize())
                .filePath(metaData.getFilePath())
                .relationId(metaData.getRelationId())
                .relationType(metaData.getRelationType())
                .build();

    }


    public ApiResponseClass getAllPhotos() {
        List<FileMetaData> files = fileMetaDataRepository.findAll();
        List<FileMetaDataResponse> responses=new ArrayList<>();
        for (FileMetaData file : files) {
            FileMetaDataResponse response = FileMetaDataResponse.builder()
                    .id(file.getId())
                    .fileName(file.getFileName())
                    .fileType(file.getFileType())
                    .fileSize(file.getFileSize())
                    .filePath(file.getFilePath())
                    .relationId(file.getRelationId())
                    .relationType(file.getRelationType())
                    .build();
            responses.add(response);
        }
        return new ApiResponseClass("All photos got successfully", HttpStatus.OK, LocalDateTime.now(),responses);
    }

    public ApiResponseClass updateFile(Integer id, FileInformationRequest request) {
        FileMetaData file = fileMetaDataRepository.findById(id).orElseThrow(
                ()->new ApiRequestException("Photo not found"));
        file.setRelationId(request.getRelationId());
        file.setRelationType(ResourceType.valueOf(request.getRelationType()));
        fileMetaDataRepository.save(file);
        FileMetaDataResponse response = FileMetaDataResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .fileSize(file.getFileSize())
                .filePath(file.getFilePath())
                .relationId(file.getRelationId())
                .relationType(file.getRelationType())
                .build();
        return new ApiResponseClass("File updated successfully", HttpStatus.OK, LocalDateTime.now(),response);

    }
}
