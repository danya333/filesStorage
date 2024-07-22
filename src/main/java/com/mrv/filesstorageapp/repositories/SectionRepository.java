package com.mrv.filesstorageapp.repositories;

import com.mrv.filesstorageapp.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<List<Section>> findByProjectId(Long projectId);
}
