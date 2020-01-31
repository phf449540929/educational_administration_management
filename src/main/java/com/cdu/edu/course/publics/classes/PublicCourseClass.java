package com.cdu.edu.course.publics.classes;

import javax.persistence.*;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/12 0012 下午 18:24
 * @since jdk
 */
@Entity
public class PublicCourseClass {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int courseId;
    @Column(nullable = false)
    private String studentClass;

    public PublicCourseClass() {
    }

    public PublicCourseClass(int courseId, String studentClass) {
        this.courseId = courseId;
        this.studentClass = studentClass;
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

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

}
