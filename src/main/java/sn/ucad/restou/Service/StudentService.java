package sn.ucad.restou.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import sn.ucad.restou.Entity.Student;
import sn.ucad.restou.Exception.ResourceNotFoundException;
import sn.ucad.restou.Repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setCardNumber(studentDetails.getCardNumber());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
        Student etudiant = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        studentRepository.delete(etudiant);
    }

    public Iterable<Student> findByFirstNameContaining(String subString) {
        return studentRepository.findByFirstNameContainingIgnoringCase(subString);
    }

    
}
