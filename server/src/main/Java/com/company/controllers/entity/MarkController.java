package com.company.controllers.entity;

import com.company.entity.MarksEntity;
import com.company.entity.PeopleEntity;
import com.company.entity.SubjectsEntity;
import com.company.repos.GroupsRepository;
import com.company.repos.MarksRepository;
import com.company.repos.PeopleRepository;
import com.company.repos.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MarkController {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private SubjectsRepository subjectsRepository;
    @Autowired
    private MarksRepository marksRepository;

    @RequestMapping(value = "/editMark", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String editorMark(@RequestBody MarksEntity receivedMark) {
        String result = validate(receivedMark);
        if (result.equals("OK")) {
            MarksEntity mark = marksRepository.findById(receivedMark.getId()).get();
            mark.setStudentId(receivedMark.getStudentId());
            mark.setTeacherId(receivedMark.getTeacherId());
            mark.setSubjectId(receivedMark.getSubjectId());
            mark.setValue(receivedMark.getValue());
            marksRepository.flush();
        }
        return result;
    }

    @RequestMapping(value = "/addMark", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String addPeople(@RequestBody MarksEntity receivedMark) {
        if (receivedMark == null) {
            return "Null Data";
        }
        MarksEntity newMark = new MarksEntity();
        newMark.setValue(receivedMark.getValue());
        newMark.setTeacherId(receivedMark.getTeacherId());
        newMark.setStudentId(receivedMark.getStudentId());
        newMark.setSubjectId(receivedMark.getSubjectId());

        marksRepository.save(newMark);
        return "OK";
    }

    public String validate(MarksEntity receivedMark) {
        if (receivedMark == null) {
            return "Null Data";
        }
        Optional<MarksEntity> findMark = marksRepository.findById(receivedMark.getId());
        if (findMark.isPresent()) {
            MarksEntity mark = findMark.get();
            if (mark.getSubjectId() == null) {
                return "Null Subject";
            }
            if (mark.getStudentId() == null) {
                return "Null Student";
            }
            if (mark.getTeacherId() == null) {
                return "Null Teacher";
            }
            Optional<SubjectsEntity> findSubject = subjectsRepository.findById(mark.getSubjectId().getId());
            Optional<PeopleEntity> findStudent = peopleRepository.findById(mark.getStudentId().getId());
            Optional<PeopleEntity> findTeacher = peopleRepository.findById(mark.getTeacherId().getId());
            if (findSubject.isEmpty()) {
                return "Subject Not Found";
            }
            if (findStudent.isEmpty()) {
                return "Subject Not Found";
            }
            if (findTeacher.isEmpty()) {
                return "Subject Not Found";
            }
        } else {
            return "Mark Not Found";
        }
        return "OK";
    }
}
