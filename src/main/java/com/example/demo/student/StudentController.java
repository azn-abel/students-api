package com.example.demo.student;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;
    private final Counter successfulApiCallsCounter;
    private final Counter failedApiCallsCounter;

    @Autowired
    public StudentController(
            StudentService studentService,
            Counter successfulApiCallsCounter,
            Counter failedApiCallsCounter
    ) {
        this.studentService = studentService;
        this.successfulApiCallsCounter = successfulApiCallsCounter;
        this.failedApiCallsCounter = failedApiCallsCounter;

    }

    @GetMapping
    @Timed(value = "get.all.students.time", description = "GET /student")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    @Timed(value = "get.one.student.time", description = "GET /student/:id")
    public ResponseEntity<Object> getStudent(@PathVariable("studentId") Long studentId) {
        try {
            Student s = studentService.getStudent(studentId);
            successfulApiCallsCounter.increment();
            return ResponseEntity.ok(s);
        } catch (Exception e) {
            failedApiCallsCounter.increment();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    @Timed(value = "register.student.time", description = "POST /student")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    @Timed(value = "delete.student.time", description = "DELETE /student/:id")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    @Timed(value = "update.student.time", description = "PUT /student/:id")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        studentService.updateStudent(studentId, name, email);
    }
}
