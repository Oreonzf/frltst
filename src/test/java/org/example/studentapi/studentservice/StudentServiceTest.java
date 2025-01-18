package org.example.studentapi.studentservice;

import org.example.studentapi.model.Student;
import org.example.studentapi.repository.StudentRepository;
import org.example.studentapi.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals(1L, foundStudent.getId());
    }

    @Test
    void testGetStudentByIdNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setNome("João");
        student.setSobrenome("Silva");
        student.setMatricula("123456");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);

        assertNotNull(createdStudent);
        assertEquals("João", createdStudent.getNome());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testUpdateStudent() {
        Student existingStudent = new Student();
        existingStudent.setId(1L);
        existingStudent.setNome("João");
        existingStudent.setSobrenome("Silva");
        existingStudent.setMatricula("123456");

        Student updatedDetails = new Student();
        updatedDetails.setNome("João Atualizado");
        updatedDetails.setSobrenome("Silva Atualizado");
        updatedDetails.setMatricula("654321");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        Student updatedStudent = studentService.updateStudent(1L, updatedDetails);

        assertNotNull(updatedStudent);
        assertEquals("João Atualizado", updatedStudent.getNome());
        assertEquals("Silva Atualizado", updatedStudent.getSobrenome());
        assertEquals("654321", updatedStudent.getMatricula());
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void testUpdateStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Student updatedDetails = new Student();
        updatedDetails.setNome("João Atualizado");

        assertThrows(NoSuchElementException.class, () -> studentService.updateStudent(1L, updatedDetails));
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.existsById(1L)).thenReturn(true);  // Garantir que existsById também retorne true

        studentService.deleteStudent(1L);

        verify(studentRepository).deleteById(1L);
    }


    @Test
    void testDeleteStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> studentService.deleteStudent(1L));
    }
}
