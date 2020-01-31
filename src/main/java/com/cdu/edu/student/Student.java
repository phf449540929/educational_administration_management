package com.cdu.edu.student;

import javax.persistence.*;

/**
 * description: the entity of a student
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:00
 * @since jdk11
 */
@Entity
public class Student {

    @Id
    @Column(nullable = false)
    private String studentId;
    @Column(nullable = false)
    private String studentPassword;
    @Column(nullable = false)
    private String studentName;
    @Column(nullable = false)
    private String studentGender;
    @Column(nullable = false)
    private String studentAcademy;
    @Column(nullable = false)
    private String studentGrade;
    @Column(nullable = false)
    private String studentMajor;
    @Column(nullable = false)
    private String studentClass;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentAcademy() {
        return studentAcademy;
    }

    public void setStudentAcademy(String studentAcademy) {
        this.studentAcademy = studentAcademy;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
