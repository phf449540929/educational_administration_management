package com.cdu.edu.course.professional;

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
public interface ProfessionalCourseRepository extends JpaRepository<ProfessionalCourse, Integer> {

    public ProfessionalCourse findByCourseId(int courseId);

    /**
     * description:
     *
     * @param courseIdList get the list of professional course by the array of course' id
     * @return java.util.List
     */
    public List<ProfessionalCourse> findByCourseIdIn(List<Integer> courseIdList);

    public List<ProfessionalCourse> findByTeacherId(String teacherId);

}
