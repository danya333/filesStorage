package com.mrv.filesstorageapp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String name;
    private String shortName;
    private String code;
    private MultipartFile cover;
}
