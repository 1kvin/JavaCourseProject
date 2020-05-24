package com.company.controllers.entity;

import com.company.entity.GroupsEntity;
import com.company.entity.PeopleEntity;
import com.company.repos.GroupsRepository;
import com.company.repos.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private GroupsRepository groupsRepository;

    @RequestMapping(value = "/addPeople", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String addPeople(@RequestBody PeopleEntity receivedPeople) {
        if (receivedPeople == null) {
            return "Null Data";
        }
        PeopleEntity newPeople = new PeopleEntity();
        newPeople.setType(receivedPeople.getType());
        newPeople.setFirstName(receivedPeople.getFirstName());
        newPeople.setLastName(receivedPeople.getLastName());
        newPeople.setPatherName(receivedPeople.getPatherName());
        if (receivedPeople.getGroupsByGroupId() == null) {
            return "Null Group";
        }
        Optional<GroupsEntity> findGroup = groupsRepository.findById(receivedPeople.getGroupsByGroupId().getId());
        if (findGroup.isPresent()) {
            newPeople.setGroupsByGroupId(findGroup.get());
            peopleRepository.save(newPeople);
            return "OK";
        } else {
            return "Group not found";
        }
    }

    @RequestMapping(value = "/editPeople", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String editorPeople(@RequestBody PeopleEntity receivedPeople) {
        String result = validate(receivedPeople);
        if (result.equals("OK")) {
            PeopleEntity people = peopleRepository.findById(receivedPeople.getId()).get();
            people.setPatherName(receivedPeople.getPatherName());
            people.setLastName(receivedPeople.getLastName());
            people.setFirstName(receivedPeople.getFirstName());
            people.setType(receivedPeople.getType());
            people.setGroupsByGroupId(receivedPeople.getGroupsByGroupId());
            peopleRepository.flush();
        }
        return result;
    }

    public String validate(PeopleEntity receivedPeople) {
        if (receivedPeople == null) {
            return "Null Data";
        }
        //Try find People
        Optional<PeopleEntity> findPeople = peopleRepository.findById(receivedPeople.getId());
        if (findPeople.isPresent()) {
            PeopleEntity people = findPeople.get();
            //Try find Group
            if (receivedPeople.getGroupsByGroupId() == null) {
                return "Null Group";
            }
            Optional<GroupsEntity> findGroup = groupsRepository.findById(receivedPeople.getGroupsByGroupId().getId());
            if (findGroup.isPresent()) {
                return "OK";
            } else {
                return "Group Not Found ";
            }
        } else {
            return "People Not Found ";
        }
    }
}
