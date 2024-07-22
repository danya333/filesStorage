package com.mrv.filesstorageapp.repositories;

import com.mrv.filesstorageapp.models.Album;
import com.mrv.filesstorageapp.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<List<Album>> findAlbumBySection(Section section);
}
