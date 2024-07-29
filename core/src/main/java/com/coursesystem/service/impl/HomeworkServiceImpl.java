package com.coursesystem.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.HomeworkDTO;
import com.coursesystem.mapper.HomeworkMapper;
import com.coursesystem.mapper.LessonMapper;
import com.coursesystem.model.Homework;
import com.coursesystem.repository.HomeworkRepository;
import com.coursesystem.service.HomeworkService;
import com.coursesystem.service.LessonService;
import com.coursesystem.dto.LessonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService {

    private final Logger log = LoggerFactory.getLogger(HomeworkServiceImpl.class);

    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final HomeworkMapper homeworkMapper;
    private final HomeworkRepository homeworkRepository;

    public HomeworkServiceImpl(final AmazonS3 s3Client,
                               final LessonService lessonService,
                               final LessonMapper lessonMapper,
                               final HomeworkMapper homeworkMapper,
                               final HomeworkRepository homeworkRepository) {
        this.s3Client = s3Client;
        this.lessonService = lessonService;
        this.lessonMapper = lessonMapper;
        this.homeworkMapper = homeworkMapper;
        this.homeworkRepository = homeworkRepository;
    }

    @Override
    public String uploadFile(final MultipartFile file, final Long lessonId) {
        final LessonDTO lessonDTO = lessonService.findByIdOrThrow(lessonId);
        final File fileObj = convertMultiPartFileToFile(file);
        final String fullBucketName = bucketName + "/" + lessonId;
        final String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(fullBucketName, fileName, fileObj));
        saveHomework(file.getOriginalFilename(), fileName, lessonDTO);
        fileObj.delete();
        return "File uploaded : " + fileName;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HomeworkDTO> findOne(final Long id) {
        log.debug("Request to get Homework : {}", id);
        return homeworkRepository.findById(id).map(homeworkMapper::toDto);
    }

    private void saveHomework(final String name, final String fileName, final LessonDTO lessonDTO) {
        final Homework homework = new Homework();
        homework.setOriginalFileName(name);
        homework.setStoredFileName(fileName);
        homework.setLesson(lessonMapper.toEntity(lessonDTO));
        homeworkRepository.save(homework);
    }


    @Override
    public byte[] downloadFile(final HomeworkDTO homeworkDTO) {
        final HomeworkDTO foundHomeworkDTO = findByLessonIdOrThrow(homeworkDTO.getLessonId());
        final String fullBucketName = bucketName + "/" + foundHomeworkDTO.getLessonId();
        S3Object s3Object = s3Client.getObject(fullBucketName, foundHomeworkDTO.getStoredFileName());
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String deleteFile(final HomeworkDTO homeworkDTO) {
        final String fullBucketName = bucketName + "/" + homeworkDTO.getLessonId();
        s3Client.deleteObject(fullBucketName, homeworkDTO.getStoredFileName());
        return homeworkDTO.getOriginalFileName() + " removed ...";
    }

    private File convertMultiPartFileToFile(final MultipartFile file) {
        final File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    @Override
    @Transactional(readOnly = true)
    public HomeworkDTO findByLessonIdOrThrow(final Long id) throws SystemException {
        log.debug("Request to get Course : {}", id);
        return homeworkRepository.findByLessonId(id)
                .map(homeworkMapper::toDto)
                .orElseThrow(() -> new SystemException(String.format("Homework with id: %d doesn't exist.", id), ErrorCode.BAD_REQUEST));
    }


}
