package com.company.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "marks", schema = "dbo", catalog = "Javacourse")
public class MarksEntity implements IEntity {
    private int id;
    private int studentId;
    private int subjectId;
    private int teacherId;
    private int value;

    private SubjectsEntity subjectsEntity;
    private PeopleEntity peopleEntityStudent;
    private PeopleEntity teacherEntityStudent;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value", nullable = false)
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarksEntity that = (MarksEntity) o;
        return id == that.id && studentId == that.studentId && subjectId == that.subjectId && teacherId == that.teacherId && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, subjectId, teacherId, value);
    }

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    public SubjectsEntity getSubjectId() {
        return subjectsEntity;
    }

    public void setSubjectId(SubjectsEntity subjectsEntity) {
        this.subjectsEntity = subjectsEntity;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    public PeopleEntity getStudentId() {
        return peopleEntityStudent;
    }

    public void setStudentId(PeopleEntity peopleEntityStudent) {
        this.peopleEntityStudent = peopleEntityStudent;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    public PeopleEntity getTeacherId() {
        return teacherEntityStudent;
    }

    public void setTeacherId(PeopleEntity teacherEntityStudent) {
        this.teacherEntityStudent = teacherEntityStudent;
    }
}
