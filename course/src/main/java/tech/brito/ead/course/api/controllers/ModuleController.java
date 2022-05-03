package tech.brito.ead.course.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.course.api.models.ModuleDto;
import tech.brito.ead.course.core.specifications.SpecificationTemplate;
import tech.brito.ead.course.domain.models.Module;
import tech.brito.ead.course.domain.services.CourseService;
import tech.brito.ead.course.domain.services.ModuleService;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public Module updateModule(@PathVariable UUID courseId, @PathVariable UUID moduleId, @RequestBody @Valid ModuleDto moduleDto) {
        var module = moduleService.findModuleIntoCourse(courseId, moduleId);
        module.setDescription(moduleDto.getDescription());
        module.setTitle(moduleDto.getTitle());
        return moduleService.save(module);
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        var module = moduleService.findModuleIntoCourse(courseId, moduleId);
        moduleService.delete(module);
    }

    @GetMapping("/courses/{courseId}/modules")
    public Page<Module> getAllModules(@PathVariable UUID courseId,
                                      SpecificationTemplate.ModuleSpec spec,
                                      @PageableDefault(sort = "title") Pageable pageable) {
        var modulePage = moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);
        modulePage.toList().forEach(module -> {
            module.add(linkTo(methodOn(ModuleController.class).getModule(courseId, module.getId())).withSelfRel());
        });
        return modulePage;
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public Module getModule(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        return moduleService.findModuleIntoCourse(courseId, moduleId);
    }
}
