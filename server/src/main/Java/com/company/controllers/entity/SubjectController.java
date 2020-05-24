package com.company.controllers.entity;

import com.company.entity.SubjectsEntity;
import com.company.repos.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SubjectController {
    @Autowired
    private SubjectsRepository subjectsRepository;

    @RequestMapping(value = "/addSubject", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String addSubject(@RequestBody SubjectsEntity receivedSubject) {
        if (receivedSubject == null) {
            return "Null Data";
        }
        SubjectsEntity newSubject = new SubjectsEntity();
        newSubject.setName(receivedSubject.getName());
        subjectsRepository.save(newSubject);
        return "OK";
    }

    @RequestMapping(value = "/editSubject", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String editorSubject(@RequestBody SubjectsEntity receivedSubject) {
        String result = validate(receivedSubject);
        if (result.equals("OK")) {
            SubjectsEntity subject = subjectsRepository.findById(receivedSubject.getId()).get();
            subject.setName(receivedSubject.getName());
            subjectsRepository.flush();
        }
        return result;
    }

    public String validate(SubjectsEntity receivedSubject) {
        if (receivedSubject == null) {
            return "Null Data";
        }
        Optional<SubjectsEntity> findSubject = subjectsRepository.findById(receivedSubject.getId());
        if (findSubject.isPresent()) {
            return "OK";
        } else {
            return "Subject Not Found";
        }
    }
}
