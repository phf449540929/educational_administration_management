package com.cdu.edu.course.elective.student;

import javax.persistence.*;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/12 0012 下午 18:10
 * @since jdk
 */
@Entity
public class ElectiveCourseStudent {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int courseId;
    @Column(nullable = false)
    private String studentId;

    public ElectiveCourseStudent() {
    }

    public ElectiveCourseStudent(int courseId, String studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
