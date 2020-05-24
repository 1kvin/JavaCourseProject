package com.company.controllers;

import com.company.entity.GroupsEntity;
import com.company.entity.MarksEntity;
import com.company.entity.PeopleEntity;
import com.company.entity.SubjectsEntity;
import com.company.repos.GroupsRepository;
import com.company.repos.MarksRepository;
import com.company.repos.PeopleRepository;
import com.company.repos.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private MarksRepository marksRepository;
    @Autowired
    private SubjectsRepository subjectsRepository;

    @RequestMapping("/addTest")
    public Iterable<MarksEntity> addTestEntity() {
        GroupsEntity studentGroup = new GroupsEntity();
        studentGroup.setName("TestStudentGroup");
        groupsRepository.save(studentGroup);

        GroupsEntity teacherGroup = new GroupsEntity();
        teacherGroup.setName("TestTeacherGroup");
        groupsRepository.save(teacherGroup);

        PeopleEntity studentPeople = new PeopleEntity();
        studentPeople.setFirstName("TestStudentFirstName");
        studentPeople.setLastName("TestStudentLastName");
        studentPeople.setType("S");
        studentPeople.setPatherName("TestPatherName");
        studentPeople.setGroupsByGroupId(studentGroup);
        peopleRepository.save(studentPeople);

        PeopleEntity teacherPeople = new PeopleEntity();
        teacherPeople.setFirstName("TestTeacherFirstName");
        teacherPeople.setLastName("TestTeacherLastName");
        teacherPeople.setType("P");
        teacherPeople.setPatherName("TestPatherName");
        teacherPeople.setGroupsByGroupId(teacherGroup);
        peopleRepository.save(teacherPeople);

        SubjectsEntity subject = new SubjectsEntity();
        subject.setName("TestSubject");
        subjectsRepository.save(subject);

        MarksEntity mark = new MarksEntity();
        mark.setStudentId(studentPeople);
        mark.setTeacherId(teacherPeople);
        mark.setSubjectId(subject);
        mark.setValue(5);
        marksRepository.save(mark);
        return marksRepository.findAll();
    }
}
