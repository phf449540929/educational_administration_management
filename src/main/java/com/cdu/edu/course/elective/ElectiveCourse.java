package com.cdu.edu.course.elective;

import com.cdu.edu.course.AbstractCourse;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * description: 选修课程类
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/12 0012 下午 16:56
 * @since jdk 10.0.1
 */
@Entity
public class ElectiveCourse extends AbstractCourse {

    @Column
    private int courseSize;

    public int getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }
}
