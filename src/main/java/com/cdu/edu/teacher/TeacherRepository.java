package com.cdu.edu.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * description: the repository by operation for teachers
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:10
 * @since jdk11
 */
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    /**
     * description: find the student by student's id
     *
     * @param teacherId teacher's id
     * @return com.cdu.edu.teacher.Teacher
     */
    Teacher findByTeacherId(String teacherId);

}
