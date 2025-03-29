package com.edureka.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edureka.sms.entity.Student;
import com.edureka.sms.repository.StudentRepository;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository repository;
    
    public Student saveStudent(Student student) {
        return repository.save(student);
    }
    
    public List<Student> getAllStudents() {
        return repository.findAll();
    }
    
    public Student getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
