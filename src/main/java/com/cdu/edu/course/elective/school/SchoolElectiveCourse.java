package com.cdu.edu.course.elective.school;

import com.cdu.edu.course.AbstractCourse;

import javax.persistence.Entity;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/12 0012 下午 16:56
 * @since jdk
 */
@Entity
public class SchoolElectiveCourse extends AbstractCourse {

    private int courseSize;

    public int getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }
}
