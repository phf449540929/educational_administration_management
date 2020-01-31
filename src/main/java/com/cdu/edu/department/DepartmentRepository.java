package com.cdu.edu.department;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * description: description: the repository by operation for departments
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/17 0017 上午 9:38
 * @since jdk11
 */
public interface DepartmentRepository extends JpaRepository<Department, String> {

    /**
     * description: find the department by department's id
     *
     * @param departmentId department's id
     * @return com.cdu.edu.department.Department
     */
    Department findByDepartmentId(String departmentId);

    /**
     * description: find the departments by departments' id
     *
     * @param departmentIdList the list by departments' id
     * @return java.util.List
     */
    List<Department> findByDepartmentId(List<String> departmentIdList);

}
