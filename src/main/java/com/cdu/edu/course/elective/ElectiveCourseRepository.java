package com.cdu.edu.course.elective;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: 选修课的Dao层
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/11/2 0002 上午 11:39
 * @since jdk 10.0.1
 */
@Repository
public interface ElectiveCourseRepository extends JpaRepository<ElectiveCourse, Integer> {

    /**
     * description: 通过courseId寻找选修课
     *
     * @param courseId 选修课的courseId
     * @return com.cdu.edu.course.elective.ElectiveCourse 找到的选修课程
     */
    ElectiveCourse findByCourseIdIn(int courseId);

    /**
     * description: 通过courseId的List批量寻找选修课
     *
     * @param courseIdList 选修课courseId的List
     * @return java.util.List<E> 找到的选修课的List
     */
    List<ElectiveCourse> findByCourseIdIn(List<Integer> courseIdList);

    /**
     * description: 通过teacherId寻找选修课
     *
     * @param teacherId 教师的teacherId
     * @return java.util.List<E> 找到的选修课的List
     */
    List<ElectiveCourse> findByTeacherId(String teacherId);

}
