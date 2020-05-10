package com.cdu.edu.course.elective.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/11/9 0009 上午 10:47
 * @since jdk
 */
public interface ElectiveCourseStudentRepository extends JpaRepository<ElectiveCourseStudent, Integer> {

    /**
     * description: find professional course's classes by its id
     *
     * @param courseId a
     * @return
     */
    public List<ElectiveCourseStudent> findByCourseId(int courseId);

    public List<ElectiveCourseStudent> findByStudentId(String studentId);

}
