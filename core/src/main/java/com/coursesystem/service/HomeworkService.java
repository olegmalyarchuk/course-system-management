package com.coursesystem.service;

import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.HomeworkDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface HomeworkService {
    String uploadFile(MultipartFile file, final Long courseId);

    Optional<HomeworkDTO> findOne(Long id);

    byte[] downloadFile(HomeworkDTO attachmentDTO);

    String deleteFile(HomeworkDTO fileName);

    HomeworkDTO findByLessonIdOrThrow(Long id) throws SystemException;
}
