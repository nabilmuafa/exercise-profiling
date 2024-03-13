package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAll();
        List<StudentCourse> studentCourses = studentCourseRepository.findAll();
        List<StudentCourse> studentsWithCourses = new ArrayList<>();

        Map<Long, Student> studentListByID = new HashMap<Long, Student>();
        for (Student student : students) {
            studentListByID.put(student.getId(), student);
        }
        for (StudentCourse studentCourse : studentCourses) {
            Student student = studentListByID.get(studentCourse.getStudent().getId());
            StudentCourse newStudentCourse = new StudentCourse();
            newStudentCourse.setStudent(student);
            newStudentCourse.setCourse(studentCourse.getCourse());
            studentsWithCourses.add(newStudentCourse);
        }
        return studentsWithCourses;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findFirstByOrderByGpaDesc();
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        StringBuilder resultBuilder = new StringBuilder();
        for (Student student : students){
            resultBuilder.append(student.getName()).append(", ");
        }
        if (resultBuilder.length() > 2){
            resultBuilder.setLength(resultBuilder.length() - 2);
        }
        return resultBuilder.toString();
    }
}

