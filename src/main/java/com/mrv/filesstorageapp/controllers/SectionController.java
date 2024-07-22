package com.mrv.filesstorageapp.controllers;

import com.mrv.filesstorageapp.DTOs.SectionDTO;
import com.mrv.filesstorageapp.models.Section;
import com.mrv.filesstorageapp.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/sections")
public class SectionController {

    private final SectionService sectionService;

    @GetMapping({"{id}"})
    public ResponseEntity<Section> getSectionById(@PathVariable("id") Long sectionId){
        Section section = sectionService.getSectionById(sectionId);
        return ResponseEntity.ok(section);
    }

    @PostMapping("create")
    public ResponseEntity<SectionDTO> createSection(@PathVariable("projectId") Long projectId,
                                                    @RequestBody SectionDTO sectionDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {

        Section section = sectionService.createSection(projectId, sectionDTO);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/projects/{projectId}/sections/{sectionId}")
                        .build(Map.of("projectId", projectId, "sectionId", section.getId())))
                .body(sectionDTO);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Section> updateSection(@PathVariable("id") Long id,
                                                 @RequestBody SectionDTO sectionDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        Section section = sectionService.updateSection(id, sectionDTO);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/projects/{projectId}/sections/{sectionId}")
                        .build(Map.of("projectId", section.getProject().getId(), "sectionId", section.getId())))
                .body(section);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<Section> deleteSection(@PathVariable("id") Long id){
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }

}
