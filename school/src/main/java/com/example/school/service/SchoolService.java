package com.example.school.service;

import com.example.school.client.StudentClient;
import com.example.school.dto.FullSchoolResponse;
import com.example.school.dto.Student;
import com.example.school.entity.School;
import com.example.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final StudentClient studentClient;
    public void saveSchool(School school){
        schoolRepository.save(school);
    }

    public List<School> findAllSchools(){
        return schoolRepository.findAll();
    }

    public FullSchoolResponse findSchoolsWithStudents(Integer schoolId){
        School school = schoolRepository.findById(schoolId)
                .orElse(
                        School.builder()
                                .name("NOT_FOUND")
                                .email("NOT_FOUND").build()
                );
        List<Student> students = studentClient.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
