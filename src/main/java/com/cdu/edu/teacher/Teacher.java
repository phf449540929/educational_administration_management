package com.cdu.edu.teacher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * description: the entity of the teachers
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/12 0012 上午 10:11
 * @since jdk
 */
@Entity
public class Teacher {

    @Id
    @Column(nullable = false)
    private String teacherId;
    @Column(nullable = false)
    private String teacherPassword;
    @Column(nullable = false)
    private String teacherName;
    @Column(nullable = false)
    private String teacherGender;
    @Column(nullable = false)
    private String teacherDepartment;
    @Column(nullable = false)
    private TeacherRank teacherRank;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }


    public String getTeacherDepartment() {
        return teacherDepartment;
    }

    public void setTeacherDepartment(String teacherDepartment) {
        this.teacherDepartment = teacherDepartment;
    }

    public TeacherRank getTeacherRank() {
        return teacherRank;
    }

    public void setTeacherRank(TeacherRank teacherRank) {
        this.teacherRank = teacherRank;
    }
}
