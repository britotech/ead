package tech.brito.ead.course.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.brito.ead.course.api.models.LessonDto;
import tech.brito.ead.course.core.specifications.SpecificationTemplate;
import tech.brito.ead.course.domain.models.Lesson;
import tech.brito.ead.course.domain.services.LessonService;
import tech.brito.ead.course.domain.services.ModuleService;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    private final ModuleService moduleService;

    private final LessonService lessonService;

    public LessonController(ModuleService moduleService, LessonService lessonService) {
        this.moduleService = moduleService;
        this.lessonService = lessonService;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public Lesson saveLesson(@PathVariable UUID moduleId, @RequestBody @Valid LessonDto lessonDto) {

        var module = moduleService.findById(moduleId);

        var lesson = new Lesson();
        lesson.setModule(module);
        lesson.setDescription(lessonDto.getDescription());
        lesson.setTitle(lessonDto.getTitle());
        lesson.setVideoUrl(lessonDto.getVideoUrl());

        return lessonService.save(lesson);
    }

    @PutMapping("/modules/{moduleId}/lessons/{id}")
    public Lesson updateModule(@PathVariable UUID moduleId, @PathVariable UUID id, @RequestBody @Valid LessonDto lessonDto) {
        var lesson = lessonService.findLessonIntoModule(moduleId, id);
        lesson.setDescription(lessonDto.getDescription());
        lesson.setTitle(lessonDto.getTitle());
        lesson.setVideoUrl(lessonDto.getVideoUrl());

        return lessonService.save(lesson);
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModule(@PathVariable UUID moduleId, @PathVariable UUID id) {
        var lesson = lessonService.findLessonIntoModule(moduleId, id);
        lessonService.delete(lesson);
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public Page<Lesson> getAllLessons(@PathVariable UUID moduleId,
                                      SpecificationTemplate.LessonSpec spec,
                                      @PageableDefault(sort = "title") Pageable pageable) {

        var lessonPage = lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable);
        lessonPage.toList().forEach(lesson -> {
            lesson.add(linkTo(methodOn(LessonController.class).getLesson(moduleId, lesson.getId())).withSelfRel());
        });
        return lessonPage;
    }

    @GetMapping("/modules/{moduleId}/lessons/{id}")
    public Lesson getLesson(@PathVariable UUID moduleId, @PathVariable UUID id) {
        return lessonService.findLessonIntoModule(moduleId, id);
    }
}