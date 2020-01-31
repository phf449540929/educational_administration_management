package com.cdu.edu.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * description: the repository by operation for students
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:10
 * @since jdk11
 */
public interface StudentRepository extends JpaRepository<Student, String> {

    /**
     * description: find the student by student's id
     *
     * @param studentId student's id
     * @return com.cdu.edu.student.Student
     */
    Student findByStudentId(String studentId);

    /**
     * description: find the student by student's id
     *
     * @param studentId student's id
     * @return com.cdu.edu.student.Student
     */
    List<Student> findByStudentClass(String studentClass);

    /**
     * description: get all the student order by student's class
     *
     * @return java.util.List
     */
    List<Student> findAllByOrderByStudentClass();
}
