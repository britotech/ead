package tech.brito.ead.course.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.course.api.models.CourseDto;
import tech.brito.ead.course.api.models.ModuleDto;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.models.Module;
import tech.brito.ead.course.domain.services.CourseService;
import tech.brito.ead.course.domain.services.ModuleService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    private final CourseService courseService;
    private final ModuleService moduleService;

    public ModuleController(CourseService courseService, ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @PostMapping("/courses/{courseId}/modules")
    @ResponseStatus(HttpStatus.CREATED)
    public Module saveModule(@PathVariable UUID courseId, @RequestBody @Valid ModuleDto moduleDto) {

        var course = courseService.findById(courseId);

        var module = new Module();
        module.setCourse(course);
        module.setDescription(moduleDto.getDescription());
        module.setTitle(moduleDto.getTitle());

        return moduleService.save(module);
    }

    @DeleteMapping("/courses/{courseId}/modules/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable UUID courseId, @PathVariable UUID id) {
        var module = moduleService.findModuleIntoCourse(courseId, id);
        moduleService.delete(module);
    }
}
