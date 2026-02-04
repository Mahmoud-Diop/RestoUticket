package sn.ucad.restou.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import sn.ucad.restou.Entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByCardNumber(String cardNumber);

    Iterable<Student> findByLastName(String lastName);

    Iterable<Student> findByFirstNameContaining(String subString);

}
