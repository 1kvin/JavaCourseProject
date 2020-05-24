package com.company.controllers;

import com.company.repos.GroupsRepository;
import com.company.repos.MarksRepository;
import com.company.repos.PeopleRepository;
import com.company.repos.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowController {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private MarksRepository marksRepository;
    @Autowired
    private SubjectsRepository subjectsRepository;

    @RequestMapping("/show")
    public List<?> shower(@RequestParam(value = "table") String table) {
        switch (table) {
            case "people":
                return peopleRepository.findAll();
            case "group":
                return groupsRepository.findAll();
            case "subject":
                return subjectsRepository.findAll();
            case "mark":
                return marksRepository.findAll();
            default:
                throw new IllegalArgumentException();
        }
    }
}
