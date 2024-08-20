package org.example.studentapi;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudentById() {
        // If estudante exists
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Quando chamado para obter id
        Student foundStudent = studentService.getStudentById(1L);

        // Encontrar estudante
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
        // Estudante criado
        Student student = new Student();
        student.setNome("João");
        student.setSobrenome("Silva");
        student.setMatricula("123456");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Chamado para salvar
        Student createdStudent = studentService.createStudent(student);

        // Se salvo corretamente
        assertNotNull(createdStudent);
        assertEquals("João", createdStudent.getNome());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testUpdateStudent() {
        // Estudante existente
        Student existingStudent = new Student();
        existingStudent.setId(1L);
        existingStudent.setNome("João");
        existingStudent.setSobrenome("Silva");
        existingStudent.setMatricula("123456");

        // Novos detalhes do estudante
        Student updatedDetails = new Student();
        updatedDetails.setNome("João Att");
        updatedDetails.setSobrenome("Silva Att");
        updatedDetails.setMatricula("654321");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        // Chamado para atualizar
        Student updatedStudent = studentService.updateStudent(1L, updatedDetails);

        // Verifica se foi atualizado corretamente
        assertNotNull(updatedStudent);
        assertEquals("João Atualizado", updatedStudent.getNome());
        assertEquals("Silva Atualizado", updatedStudent.getSobrenome());
        assertEquals("654321", updatedStudent.getMatricula());
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void testUpdateStudentNotFound() {
        // Estudante não existe
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Student updatedDetails = new Student();
        updatedDetails.setNome("João Atualizado");

        // Verifica se lança NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> studentService.updateStudent(1L, updatedDetails));
    }

    @Test
    void testDeleteStudent() {
        // Estudante existente
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Chamado para deletar
        studentService.deleteStudent(1L);

        // Verificar se deletado corretamente
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void testDeleteStudentNotFound() {
        // Estudante não existe
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se lança NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> studentService.deleteStudent(1L));
    }
}
