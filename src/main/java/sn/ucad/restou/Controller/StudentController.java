package sn.ucad.restou.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sn.ucad.restou.Entity.Student;
import sn.ucad.restou.Exception.ResourceNotFoundException;
import sn.ucad.restou.Service.StudentService;


@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Iterable<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id)   {
        return studentService.getStudentById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
                
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student newStudent = studentService.create(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                newStudent);
    }

    @PutMapping("/{id}")
    public Student updateStudent( @PathVariable Long id,
            @Valid @RequestBody Student student) {
            return studentService.updateStudent(id, student);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/NameContains/{subString}")
    public Iterable<Student> getStudentsByFirstNameContaining(@PathVariable String subString) {
        return studentService.findByFirstNameContaining(subString);

    }
  
    

    
}
