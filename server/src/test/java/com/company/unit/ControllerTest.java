package com.company.unit;


import com.company.Application;
import com.company.controllers.*;
import com.company.controllers.entity.GroupsController;
import com.company.controllers.entity.MarkController;
import com.company.controllers.entity.PeopleController;
import com.company.controllers.entity.SubjectController;
import com.company.entity.GroupsEntity;
import com.company.entity.MarksEntity;
import com.company.entity.PeopleEntity;
import com.company.entity.SubjectsEntity;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ControllerTest {

   // static final String mainUrl = "http://192.168.0.103:8080";
    static final String mainUrl = "http://192.168.43.89:8080";

    @Test
    public void MainControllerTest() throws IOException {
        HttpUriRequest request = new HttpGet(mainUrl);
        String auth = "admin:password";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        var httpResponse = HttpClientBuilder.create().build().execute(request);
        String answer = EntityUtils.toString(httpResponse.getEntity());
        assertEquals(answer, "Welcome!");
    }

    @Test
    public void ShowControllerTest() throws IOException {
        HttpUriRequest request = new HttpGet(mainUrl + "/show?table=group");
        String auth = "admin:password";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        var httpResponse = HttpClientBuilder.create().build().execute(request);
        int answer = httpResponse.getStatusLine().getStatusCode();
        System.out.println(answer);
        assertEquals(answer, 200);
    }

    @Autowired
    MainController mainController;
    @Autowired
    ShowController showController;
    @Autowired
    RemoveController removeController;
    @Autowired
    GroupsController groupsController;
    @Autowired
    MarkController markController;
    @Autowired
    PeopleController peopleController;
    @Autowired
    SubjectController subjectController;
    @Autowired
    TestController testController;

    @Autowired
    ErrorController errorController;

    @Test
    public void BasicErrorTest(){
        errorController.getErrorPath();
    }

    @Test
    public void ControllerBasicTest() {
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

        testController.addTestEntity();
        mainController.main();
        showController.shower("group");
        showController.shower("people");
        showController.shower("subject");
        showController.shower("mark");
        subjectController.addSubject(subjectsEntity);
        groupsController.addGroup(groupsEntity);
        peopleController.addPeople(studentEntity);
        peopleController.addPeople(teacherEntity);

        subjectController.validate(subjectsEntity);
        groupsController.validate(groupsEntity);
        peopleController.validate(studentEntity);
        peopleController.validate(teacherEntity);
        markController.validate(marksEntity);

        subjectController.editorSubject(subjectsEntity);
        groupsController.editorGroup(groupsEntity);
        peopleController.editorPeople(studentEntity);
        peopleController.editorPeople(teacherEntity);
        markController.editorMark(marksEntity);

        removeController.remover("people", groupsEntity.getId());
        //markController.add
    }
}
