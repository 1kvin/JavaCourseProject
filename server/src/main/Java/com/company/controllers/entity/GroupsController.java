package com.company.controllers.entity;

import com.company.entity.GroupsEntity;
import com.company.repos.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class GroupsController {

    @Autowired
    private GroupsRepository groupsRepository;

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String addGroup(@RequestBody GroupsEntity receivedGroup) {
        if (receivedGroup == null) {
            return "Null Data";
        }
        for (GroupsEntity findGroup : groupsRepository.findAll()) {
            if (findGroup.getName().equals(receivedGroup.getName())) {
                return "Group exist";
            }
        }
        GroupsEntity newGroup = new GroupsEntity();
        newGroup.setName(receivedGroup.getName());
        groupsRepository.save(newGroup);
        return "OK";
    }

    @RequestMapping(value = "/editGroup", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String editorGroup(@RequestBody GroupsEntity receivedGroup) {
        String result = validate(receivedGroup);
        if (result.equals("OK")) {
            GroupsEntity group = groupsRepository.findById(receivedGroup.getId()).get();
            group.setName(receivedGroup.getName());
            groupsRepository.flush();
        }
        return result;
    }

    public String validate(GroupsEntity receivedGroup) {
        if (receivedGroup == null) {
            return "Null Data";
        }
        Optional<GroupsEntity> findGroup = groupsRepository.findById(receivedGroup.getId());
        if (findGroup.isPresent()) {
            return "OK";
        } else {
            return "Group Not Found";
        }
    }
}
