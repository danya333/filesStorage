package com.mrv.filesstorageapp.controllers;

import com.mrv.filesstorageapp.DTOs.AlbumDTO;
import com.mrv.filesstorageapp.models.Album;
import com.mrv.filesstorageapp.services.AlbumService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/sections/{sectionId}/albums")
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping("create")
    public ResponseEntity<?> createAlbum(@PathVariable("sectionId") Long sectionId,
                                              @RequestParam("name") String albumName,
                                              @RequestParam("file") MultipartFile file){
        AlbumDTO albumDTO = new AlbumDTO(albumName, file);
        Album album = albumService.createAlbum(sectionId, albumDTO);
        album.setFileName("/files/" + album.getFileName());
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    @GetMapping("list")
    public ResponseEntity<?> getAllAlbums(@PathVariable("sectionId") Long sectionId){
        List<Album> albums = albumService.getAllAlbums(sectionId);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> loadFile(@PathVariable("id") Long id,
                                                 HttpServletRequest request) {
        try {
            Resource resource = albumService.loadAlbum(id);
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException | FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable("id") Long id,
                                         @RequestParam("name") String albumName,
                                         @RequestParam("file") MultipartFile file){
        AlbumDTO albumDTO = new AlbumDTO(albumName, file);
        Album album = albumService.updateAlbum(id, albumDTO);
        album.setFileName("/files/" + album.getFileName());
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void deleteAlbum(@PathVariable("id") Long id){
        albumService.deleteAlbum(id);
    }

}
