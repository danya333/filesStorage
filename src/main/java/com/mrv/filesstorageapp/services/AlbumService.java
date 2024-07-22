package com.mrv.filesstorageapp.services;

import com.mrv.filesstorageapp.DTOs.AlbumDTO;
import com.mrv.filesstorageapp.models.Album;
import com.mrv.filesstorageapp.models.User;
import com.mrv.filesstorageapp.repositories.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UserService userService;
    private final SectionService sectionService;

    @Value("${file.path}")
    private String filesDir;

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    public List<Album> getAllAlbums(Long sectionId) {
        return albumRepository.findAlbumBySection(sectionService.getSectionById(sectionId))
                .orElseThrow(NoSuchElementException::new);
    }

    public Album createAlbum(Long sectionId, AlbumDTO albumDTO) {
        User user = userService.getCurrentUser();
        Album album = new Album();
        album.setAlbumName(albumDTO.getAlbumName());
        album.setUser(user);
        album.setAuthor(user.getId());
        album.setCreatedAt(LocalDateTime.now());
        album.setUpdatedAt(LocalDateTime.now());
        album.setSection(sectionService.getSectionById(sectionId));

        try {
            album.setFileName(saveFile(albumDTO.getMultipartFile()));
            if (albumDTO.getMultipartFile() != null){
                album.setAlbumName(albumDTO.getMultipartFile().getOriginalFilename());
                album.setSize(albumDTO.getMultipartFile().getSize());
                album.setType(albumDTO.getMultipartFile().getContentType());
            } else{
                album.setSize(null);
                album.setType(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return albumRepository.save(album);
    }


    public Album updateAlbum(Long albumId, AlbumDTO albumDTO) {
        User user = userService.getCurrentUser();
        Album album = getAlbumById(albumId);
        album.setAlbumName(albumDTO.getAlbumName());
        album.setUser(user);
        album.setAuthor(user.getId());
        album.setUpdatedAt(LocalDateTime.now());

        try {
            album.setFileName(saveFile(albumDTO.getMultipartFile()));
            if (albumDTO.getMultipartFile() != null){
                album.setAlbumName(albumDTO.getMultipartFile().getOriginalFilename());
                album.setSize(albumDTO.getMultipartFile().getSize());
                album.setType(albumDTO.getMultipartFile().getContentType());
            } else{
                album.setSize(null);
                album.setType(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return albumRepository.save(album);
    }

    public Resource loadAlbum(Long albumId) throws IOException {
        Album album = getAlbumById(albumId);

//        String fileName = album.getFileName().substring(album.getFileName().indexOf('_') + 1);
//        Path currentFile = Paths.get(filesDir).resolve(album.getFileName());
//        Path renamedFile = Paths.get(filesDir).resolve(fileName);
//        Files.move(currentFile, renamedFile);

        return new UrlResource(Paths.get(filesDir).resolve(album.getFileName()).toUri());
    }


    public void deleteAlbum(Long albumId) {
        albumRepository.deleteById(albumId);
    }


    private String saveFile(MultipartFile file) throws IOException {
        if (file == null){
            return null;
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename()).replace(' ', '_');

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFileName = timeStamp + "_" + fileName;

        Path path = Paths.get(filesDir).resolve(newFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }
}
