package com.cdu.edu.department;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * description: 部门类的Dao层
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/17 0017 上午 9:38
 * @since jdk 10.0.1
 */
public interface DepartmentRepository extends JpaRepository<Department, String> {

    /**
     * description: 用于部门登录；用于部门确认设置教师；用于部门选择教师
     *
     * @param departmentId 部门Id
     * @return com.cdu.edu.department.Department 找到的部门实体
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
