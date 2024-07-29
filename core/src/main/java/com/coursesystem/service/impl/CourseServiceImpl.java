package com.coursesystem.service.impl;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.vo.StudentVO;
import com.coursesystem.mapper.CourseMapper;
import com.coursesystem.model.Student;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.service.CourseService;
import com.coursesystem.model.Course;
import com.coursesystem.model.Instructor;
import com.coursesystem.dto.CourseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public CourseServiceImpl(final CourseRepository courseRepository, final CourseMapper courseMapper,
                             final StudentRepository studentRepository, final InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        if (courseRepository.findByName(courseDTO.getName()).isPresent()) {
            throw new SystemException(String.format("Course with name %s already exist.", courseDTO.getName()), ErrorCode.BAD_REQUEST);
        }
        return saveOrUpdate(courseDTO);
    }

    @Override
    public CourseDTO saveOrUpdate(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        verifyCourse(course);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    private void verifyCourse(final Course course) {
        validateInstructors(course.getInstructors());
        validateStudents(course.getStudents());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> findAll() {
        log.debug("Request to get all Students");
        return courseRepository.findAll().stream().map(courseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Set<StudentVO>> getAllStudentsPerCourse(final Collection<Long> courseIds) {
        log.debug("Request to get all Students");
        final List<Course> courses = courseRepository.findAllByIdIn(courseIds);
        final Map<Long, Set<StudentVO>> courseUserMap = courses.stream()
                .collect(Collectors.toMap(Course::getId, c -> toStudentVO(c.getStudents())));

        return courseUserMap;
    }

    private Set<StudentVO> toStudentVO(final Set<Student> students) {
        return students.stream()
                .map(StudentVO::new)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseDTO> findOne(final Long id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id).map(courseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO findByIdOrThrow(final Long id) throws SystemException {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id)
                .map(courseMapper::toDto)
                .orElseThrow(() -> new SystemException(String.format("Course with id: %d doesn't exist.", id), ErrorCode.BAD_REQUEST));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }

    @Override
    public CourseDTO assignStudentToCourse(final Long courseId, final Set<Long> studentIds) throws SystemException {
        final CourseDTO course = findByIdOrThrow(courseId);
        course.getStudentIds().addAll(studentIds);
        final CourseDTO save = this.saveOrUpdate(course);
        return save;
    }

    @Override
    public CourseDTO assignInstructorToCourse(final Long courseId, final Set<Long> instructorIds) throws SystemException {
        final CourseDTO course = findByIdOrThrow(courseId);
        course.getInstructorIds().addAll(instructorIds);
        final CourseDTO updated = this.saveOrUpdate(course);
        return updated;
    }

    private void validateInstructors(final Set<Instructor> instructors) {
        if (instructors.isEmpty()) {
            throw new SystemException("At least one instructor should be assign to the course", ErrorCode.BAD_REQUEST);
        }
        final Set<Long> wrongInstructors = instructors.stream().map(Instructor::getId).filter(id -> !instructorRepository.existsById(id)).collect(Collectors.toSet());
        if (wrongInstructors.size() > 0) {
            throw new SystemException(String.format("An instructor with one of next ids doesn't exist: %s", wrongInstructors), ErrorCode.BAD_REQUEST);
        }
    }

    private void validateStudents(final Set<Student> students) {
        if (students.isEmpty()) {
            return;
        }
        final Set<Long> wrongStudentIds = students.stream().map(Student::getId).filter(id -> !studentRepository.existsById(id)).collect(Collectors.toSet());
        if (wrongStudentIds.size() > 0) {
            throw new SystemException(String.format("A student(s) with one of next ids don't exist: %s", wrongStudentIds), ErrorCode.BAD_REQUEST);
        }
        final List<Student> studentList = studentRepository.findAllById(students.stream().map(Student::getId).collect(Collectors.toSet()));
        final List<String> errorMessage = new ArrayList<>();
        studentList.forEach(student -> {
            if (student.getCourses().size() >= 5) {
                errorMessage.add(String.format("User %s %s already has 5 courses.", student.getUser().getFirstName(), student.getUser().getSecondName()));
            }
        });
        if (!errorMessage.isEmpty()) {
            throw new SystemException(String.join(" ", errorMessage), ErrorCode.BAD_REQUEST);
        }
    }
}
