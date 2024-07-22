package com.mrv.filesstorageapp.services;

import com.mrv.filesstorageapp.DTOs.SectionDTO;
import com.mrv.filesstorageapp.models.Section;
import com.mrv.filesstorageapp.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ProjectService projectService;

    public Section getSectionById(Long id) {
        return sectionRepository.findById(id).orElse(null);
    }

    public List<Section> getAllSectionsByProjectId(Long projectId) {
        return sectionRepository.findByProjectId(projectId)
                .orElseThrow(NoSuchElementException::new);
    }

    public Section createSection(Long projectId, SectionDTO sectionDTO) {
        Section section = new Section();
        section.setName(sectionDTO.getName());
        section.setShortName(sectionDTO.getShortName());
        section.setCode(sectionDTO.getCode());
        section.setProject(projectService.getProjectById(projectId));
        return sectionRepository.save(section);
    }

    public Section updateSection(Long id, SectionDTO sectionDTO) {
        Section section = getSectionById(id);
        section.setName(sectionDTO.getName());
        section.setShortName(sectionDTO.getShortName());
        section.setCode(sectionDTO.getCode());
        return sectionRepository.save(section);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }
}
