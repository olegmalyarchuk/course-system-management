package com.coursesystem.controller;


import com.coursesystem.dto.HomeworkDTO;
import com.coursesystem.service.HomeworkService;
import com.coursesystem.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class HomeworkController {

    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final HomeworkService homeworkService;

    public HomeworkController(final HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @PostMapping("/homework/upload/{lessonId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    public ResponseEntity<String> uploadFile(@PathVariable(value = "lessonId") final Long lessonId,
                                             @RequestParam(value = "file") final MultipartFile file) {
        log.debug("REST request to upload file with name: {}", file.getOriginalFilename());
        return new ResponseEntity<>(homeworkService.uploadFile(file, lessonId), HttpStatus.OK);
    }

    @GetMapping("/homework/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<HomeworkDTO> getHomework(@PathVariable Long id) {
        log.debug("REST request to get Homework : {}", id);
        Optional<HomeworkDTO> homeworkDTO = homeworkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(homeworkDTO);
    }

    @PostMapping("/homework/download")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestBody final HomeworkDTO homeworkDTO) {
        log.debug("REST request to get file with name: {}", homeworkDTO.getOriginalFileName());
        byte[] data = homeworkService.downloadFile(homeworkDTO);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "homework; fileName=\"" + homeworkDTO.getOriginalFileName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/homework/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    public ResponseEntity<String> deleteFile(@RequestBody final HomeworkDTO homeworkDTO) {
        log.debug("REST request to delete file with name: {}", homeworkDTO.getOriginalFileName());
        return new ResponseEntity<>(homeworkService.deleteFile(homeworkDTO), HttpStatus.OK);
    }
}
