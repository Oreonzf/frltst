package org.example.studentapi.service;

import org.example.studentapi.model.Student;
import org.example.studentapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    private static final String STUDENT_NOT_FOUND = "Student not found for this id: ";
    private static final String MATRICULA_EXISTS = "Matricula already exists: ";
    private static final String INVALID_FIELD = "Invalid field: ";

    private static final String NOME = "nome";
    private static final String SOBRENOME = "sobrenome";
    private static final String MATRICULA = "matricula";
    private static final String TELEFONES = "telefones";

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(STUDENT_NOT_FOUND + id));
    }
    public Student createStudent(Student student) {
        validateStudent(student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(STUDENT_NOT_FOUND + id));

        student.setNome(studentDetails.getNome());
        student.setSobrenome(studentDetails.getSobrenome());
        student.setMatricula(studentDetails.getMatricula());
        student.setTelefones(studentDetails.getTelefones());

        validateStudent(student);
        return studentRepository.save(student);
    }

    public Student patchStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(STUDENT_NOT_FOUND + id));

        updates.forEach((key, value) -> {
            if (key.equals("id")) {
                throw new IllegalArgumentException("ID cannot be updated");
            }

            switch (key) {
                case NOME -> student.setNome((String) value);
                case SOBRENOME -> student.setSobrenome((String) value);
                case MATRICULA -> student.setMatricula((String) value);
                case TELEFONES -> student.setTelefones((List<String>) value);
                default -> throw new IllegalArgumentException(INVALID_FIELD + key);
            }
        });

        return studentRepository.save(student);
    }


    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NoSuchElementException(STUDENT_NOT_FOUND + id);
        }
        studentRepository.deleteById(id);
    }

    private void validateStudent(Student student) {
        if (studentRepository.findByMatricula(student.getMatricula()).isPresent()) {
            throw new IllegalArgumentException(MATRICULA_EXISTS + student.getMatricula());
        }
    }

}

