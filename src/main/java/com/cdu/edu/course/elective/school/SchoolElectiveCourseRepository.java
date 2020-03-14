package com.cdu.edu.course.elective.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/11/2 0002 上午 11:39
 * @since jdk
 */
@Repository
public interface SchoolElectiveCourseRepository extends JpaRepository<SchoolElectiveCourse, Integer> {

    public SchoolElectiveCourse findByCourseId(int courseId);

    /**
     * description:
     *
     * @param courseIdList get the list of professional course by the array of course' id
     * @return java.util.List
     */
    public List<SchoolElectiveCourse> findByCourseId(List<Integer> courseIdList);

    public List<SchoolElectiveCourse> findByTeacherId(String teacherId);

}
