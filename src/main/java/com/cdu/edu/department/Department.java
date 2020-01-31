package com.cdu.edu.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/15 0015 下午 13:33
 * @since jdk
 */
@Entity
public class Department {

    @Id
    @Column(nullable = false)
    private String departmentId;
    @Column(nullable = false)
    private String departmentPassword;
    @Column(nullable = false)
    private String departmentName;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentPassword() {
        return departmentPassword;
    }

    public void setDepartmentPassword(String departmentPassword) {
        this.departmentPassword = departmentPassword;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
