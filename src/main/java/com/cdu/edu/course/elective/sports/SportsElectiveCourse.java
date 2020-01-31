package com.cdu.edu.course.elective.sports;

import com.cdu.edu.course.AbstractCourse;

import javax.persistence.*;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/12 0012 下午 16:57
 * @since jdk
 */
@Entity
public class SportsElectiveCourse extends AbstractCourse {

    @Column
    private String studentAcademy;
    @Column
    private String studentGrade;
    @Column
    private String studentMajor;
    @Column
    private String studentClass;
    @Column
    private String studentId;

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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
