package com.mrv.filesstorageapp.services;

import com.mrv.filesstorageapp.DTOs.ProjectDTO;
import com.mrv.filesstorageapp.models.Project;
import com.mrv.filesstorageapp.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Value("${cover.path}")
    private String coverDir;

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        projects.forEach(p -> p.setCover("/covers/" + p.getCover()));
        return projects;
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setShortName(projectDTO.getShortName());
        project.setCode(projectDTO.getCode());
        project.setCreationDate(LocalDateTime.now());
        try {
            project.setCover(saveCover(projectDTO.getCover()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, ProjectDTO projectDTO) {
        Project project = getProjectById(id);
        project.setName(projectDTO.getName());
        project.setShortName(projectDTO.getShortName());
        project.setCode(projectDTO.getCode());
        try {
            project.setCover(saveCover(projectDTO.getCover()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    private String saveCover(MultipartFile file) throws IOException {
        if (file == null) {
            return null;
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFileName = timeStamp + "_" + fileName;

        Path path = Paths.get(coverDir).resolve(newFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }
}
