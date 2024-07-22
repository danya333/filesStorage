package com.mrv.filesstorageapp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private String albumName;
    private MultipartFile multipartFile;
}
