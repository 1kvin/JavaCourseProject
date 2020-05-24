package com.company.controllers;

import com.company.entity.MarksEntity;
import com.company.entity.PeopleEntity;
import com.company.entity.SubjectsEntity;
import com.company.repos.GroupsRepository;
import com.company.repos.MarksRepository;
import com.company.repos.PeopleRepository;
import com.company.repos.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RemoveController {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private MarksRepository marksRepository;
    @Autowired
    private SubjectsRepository subjectsRepository;

    @RequestMapping("/remove")
    public String remover(@RequestParam(value = "table") String table, @RequestParam(value = "id") int id) {
        switch (table) {
            case "people":
                return removePeople(id);
            case "subject":
                return removeSubject(id);
            case "mark":
                return removeMark(id);
            default:
                throw new IllegalArgumentException();
        }
    }


    private String removeSubject(int id) {
        Optional<SubjectsEntity> subjectOp = subjectsRepository.findById(id);
        if (subjectOp.isPresent()) {
            SubjectsEntity subject = subjectOp.get();
            boolean flag = false;
            for (MarksEntity mark : marksRepository.findAll()) {
                if (mark.getSubjectId() == subject) {
                    marksRepository.delete(mark);
                    flag = true;
                }
            }
            if (!flag) {
                subjectsRepository.deleteById(id);
            }
        } else {
            return "NOT FOUND";
        }
        return "OK";
    }

    private String removeMark(int id) {
        Optional<MarksEntity> findMark = marksRepository.findById(id);
        if (findMark.isPresent()) marksRepository.deleteById(id);
        else return "NOT FOUND";
        return "OK";
    }

    private String removePeople(int id) {
        Optional<PeopleEntity> peopleOp = peopleRepository.findById(id);

        if (peopleOp.isPresent()) {
            PeopleEntity people = peopleOp.get();
            boolean flag = false;
            for (MarksEntity mark : marksRepository.findAll()) {
                if (mark.getStudentId() == people) {
                    marksRepository.delete(mark);
                    flag = true;
                }
            }
            if (!flag) {
                peopleRepository.deleteById(id);
            }
        } else {
            return "NOT FOUND";
        }
        return "OK";
    }
}
