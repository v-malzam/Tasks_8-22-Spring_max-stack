package com.cbr.university.controller.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cbr.university.model.Teacher;
import com.cbr.university.model.dto.TeacherDtoRest;
import com.cbr.university.service.BaseService;
import com.cbr.university.validation.IdExistsInDb;
import com.cbr.university.validation.group.Create;
import com.cbr.university.validation.group.Update;

@RestController
@RequestMapping("rest/teachers")
@Validated
public class TeacherRestController {
    private static final String CUSTOM_HEADER_NAME = "X-Query-Result";
    private HttpHeaders headers = new HttpHeaders();
    private ModelMapper modelMapper;
    private BaseService<Teacher> teacherService;

    @Autowired
    public TeacherRestController(BaseService<Teacher> teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAll() {
        List<Teacher> teachers = teacherService.getAll();
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME,
                "All objects Teacher found. Number of objects " + teachers.size());
        return new ResponseEntity<>(teachers, headers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Teacher> add(
            @Validated(Create.class) @RequestBody TeacherDtoRest teacherDtoRest) {
        Teacher teacher = modelMapper.map(teacherDtoRest, Teacher.class);
        Teacher createdTeacher = teacherService.create(teacher);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Created Teacher object with id " + createdTeacher.getId());
        return new ResponseEntity<>(createdTeacher, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Teacher> update(
            @Validated(Update.class) @RequestBody TeacherDtoRest teacherDtoRest) {
        Teacher teacher = modelMapper.map(teacherDtoRest, Teacher.class);
        Teacher updatedTeacher = teacherService.update(teacher);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Updated Teacher object with id " + updatedTeacher.getId());
        return new ResponseEntity<>(updatedTeacher, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Teacher> delete(
            @NotNull(message = "Request must include a Teacher id") @IdExistsInDb(typeObject = "Teacher", message = "This Teacher id is not in the database") @PathVariable Integer id) {
        teacherService.delete(teacherService.getById(id));
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Deleted Teacher object with id " + id);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
