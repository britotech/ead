package tech.brito.ead.course.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.course.core.configs.security.AuthenticationCurrentUserService;
import tech.brito.ead.course.core.specifications.SpecificationTemplate;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.models.CourseDto;
import tech.brito.ead.course.domain.services.CourseService;

import javax.validation.Valid;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    private final CourseService courseService;
    private final ModelMapper modelMapper;

    private final AuthenticationCurrentUserService currentUserService;

    public CourseController(CourseService courseService, ModelMapper modelMapper, AuthenticationCurrentUserService currentUserService) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
        this.currentUserService = currentUserService;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public Page<Course> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                      @PageableDefault(sort = "name") Pageable pageable,
                                      @RequestParam(required = false) UUID userId) {

        Page<Course> userPage = null;

        if (nonNull(userId)) {
            userPage = courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable);
        } else {
            userPage = courseService.findAll(spec, pageable);
        }

        userPage.toList().forEach(course -> {
            course.add(linkTo(methodOn(CourseController.class).getCourse(course.getId())).withSelfRel());
        });

        return userPage;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable UUID courseId) {
        return courseService.findById(courseId);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course saveCourse(@RequestBody @Valid CourseDto courseDto) {

        if (!currentUserService.getCurrentUSer().getUserId().equals(courseDto.getUserInstructor())) {
            throw new AccessDeniedException("Forbidden");
        }

        var course = new Course();
        modelMapper.map(courseDto, course);
        return courseService.save(course);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable UUID courseId) {
        var course = courseService.findById(courseId);
        courseService.delete(course);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("/{courseId}")
    public Course updateCourse(@PathVariable UUID courseId, @RequestBody @Valid CourseDto courseDto) {
        var course = courseService.findById(courseId);
        modelMapper.map(courseDto, course);
        return courseService.save(course);
    }
}
