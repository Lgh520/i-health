package com.project.ihealth.dao.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionEntry {
    private Long id;
    private String userName;
    private String ip;
    private Long questionId;
    private String phoneNumber;
    private String collectionInfo;
    private LocalDateTime createdAt;
    private String randomImage;
    private String doctorRecommendation;
    private String token;
    private String pngName;
}
