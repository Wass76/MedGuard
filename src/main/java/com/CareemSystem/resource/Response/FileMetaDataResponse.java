package com.CareemSystem.resource.Response;

import com.CareemSystem.resource.Enum.ResourceType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileMetaDataResponse {
    private Integer id;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
    private ResourceType relationType;
    private Integer relationId;
}
