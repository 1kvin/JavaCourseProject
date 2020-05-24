package com.company.unit;

import com.company.Application;
import com.company.entity.GroupsEntity;
import com.company.entity.PeopleEntity;
import com.company.entity.SubjectsEntity;
import com.company.repos.GroupsRepository;
import com.company.repos.MarksRepository;
import com.company.repos.PeopleRepository;
import com.company.repos.SubjectsRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RepositoryTest {

    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    SubjectsRepository subjectsRepository;
    @Autowired
    PeopleRepository peopleRepository;
    @Autowired
    MarksRepository marksRepository;

    @Test
    public void groupsRepositoryTest() {
        GroupsEntity group = new GroupsEntity();
        group.setName("TestGroup");
        groupsRepository.save(group);
        int groupId = group.getId();
        Optional<GroupsEntity> createGroup = groupsRepository.findById(groupId);
        assertTrue(createGroup.isPresent());
        groupsRepository.delete(group);
        Optional<GroupsEntity> deleteGroup = groupsRepository.findById(groupId);
        assertTrue(deleteGroup.isEmpty());
    }

    @Test
    public void subjectsRepositoryTest() {
        SubjectsEntity subject = new SubjectsEntity();
        subject.setName("TestSubject");
        subjectsRepository.save(subject);
        int subjectId = subject.getId();
        Optional<SubjectsEntity> createSubject = subjectsRepository.findById(subjectId);
        assertTrue(createSubject.isPresent());
        subjectsRepository.delete(subject);
        Optional<SubjectsEntity> deleteSubject = subjectsRepository.findById(subjectId);
        assertTrue(deleteSubject.isEmpty());
    }

    @Test
    public void peopleRepositoryTest() {
        SubjectsEntity subject = new SubjectsEntity();
        subject.setName("TestSubject");

        GroupsEntity group = new GroupsEntity();
        group.setName("TestGroup");
        groupsRepository.save(group);

        PeopleEntity people = new PeopleEntity();
        people.setGroupsByGroupId(group);
        people.setPatherName("TestPather");
        people.setLastName("TestLastName");
        people.setFirstName("TestFirstName");
        people.setType("P");
        peopleRepository.save(people);
        int peopleId = people.getId();
        Optional<PeopleEntity> createPeople = peopleRepository.findById(peopleId);
        assertTrue(createPeople.isPresent());
        peopleRepository.delete(people);
        Optional<PeopleEntity> deletePeople = peopleRepository.findById(peopleId);
        assertTrue(deletePeople.isEmpty());
        groupsRepository.delete(group);
    }

    @Test
    public void markRepositoryTest() {

    }
}

