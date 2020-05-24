package com.company.unit;

import com.company.entity.GroupsEntity;
import com.company.entity.MarksEntity;
import com.company.entity.PeopleEntity;
import com.company.entity.SubjectsEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityCorrectCreateTest {

    @Test
    public void groupCreateEntityTest() {
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setName("TestGroup");

        assertEquals(groupsEntity.getName(), "TestGroup");
    }

    @Test
    public void subjectEntityTest() {
        SubjectsEntity subjectsEntity = new SubjectsEntity();
        subjectsEntity.setName("TestSubject");

        assertEquals(subjectsEntity.getName(), "TestSubject");
    }

    @Test
    public void peopleCreateEntityTest() {
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setName("TestGroup");


        PeopleEntity peopleEntity = new PeopleEntity();
        peopleEntity.setGroupsByGroupId(groupsEntity);
        peopleEntity.setType("S");
        peopleEntity.setFirstName("FirstName");
        peopleEntity.setLastName("LastName");
        peopleEntity.setPatherName("PatherName");

        assertEquals(peopleEntity.getType(), "S");
        assertEquals(peopleEntity.getFirstName(), "FirstName");
        assertEquals(peopleEntity.getLastName(), "LastName");
        assertEquals(peopleEntity.getPatherName(), "PatherName");
        assertEquals(peopleEntity.getGroupsByGroupId(), groupsEntity);
    }

    @Test
    public void markEntityTest() {
        GroupsEntity groupsEntity = new GroupsEntity();
        groupsEntity.setName("TestGroup");

        SubjectsEntity subjectsEntity = new SubjectsEntity();
        subjectsEntity.setName("TestSubject");

        PeopleEntity studentEntity = new PeopleEntity();
        studentEntity.setGroupsByGroupId(groupsEntity);
        studentEntity.setType("S");
        studentEntity.setFirstName("FirstName");
        studentEntity.setLastName("LastName");
        studentEntity.setPatherName("PatherName");

        PeopleEntity teacherEntity = new PeopleEntity();
        teacherEntity.setGroupsByGroupId(groupsEntity);
        teacherEntity.setType("P");
        teacherEntity.setFirstName("FirstName");
        teacherEntity.setLastName("LastName");
        teacherEntity.setPatherName("PatherName");

        MarksEntity marksEntity = new MarksEntity();
        marksEntity.setValue(5);
        marksEntity.setSubjectId(subjectsEntity);
        marksEntity.setTeacherId(teacherEntity);
        marksEntity.setStudentId(studentEntity);

        assertEquals(marksEntity.getStudentId(), studentEntity);
        assertEquals(marksEntity.getTeacherId(), teacherEntity);
        assertEquals(marksEntity.getSubjectId(), subjectsEntity);
        assertEquals(marksEntity.getValue(), 5);
    }
}
