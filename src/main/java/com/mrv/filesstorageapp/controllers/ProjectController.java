package com.mrv.filesstorageapp.controllers;

import com.mrv.filesstorageapp.DTOs.ProjectDTO;
import com.mrv.filesstorageapp.models.Project;
import com.mrv.filesstorageapp.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("list")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("create")
    public ResponseEntity<Project> createProject(@RequestParam("name") String name,
                                                 @RequestParam("shortName") String shortname,
                                                 @RequestParam("code") String code,
                                                 @RequestParam(value = "cover", required = false) MultipartFile cover,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        ProjectDTO projectDTO = new ProjectDTO(name, shortname, code, cover);
        Project project = projectService.createProject(projectDTO);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/storage/projects/" + project.getId())
                        .build()
                        .toUri())
                .body(project);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("shortName") String shortname,
                                                 @RequestParam("code") String code,
                                                 @RequestParam(value = "cover", required = false) MultipartFile cover,
                                                 UriComponentsBuilder uriComponentsBuilder){
        ProjectDTO projectDTO = new ProjectDTO(name, shortname, code, cover);
        Project project = projectService.updateProject(id, projectDTO);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/storage/projects/" + project.getId())
                        .build()
                        .toUri())
                .body(project);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
