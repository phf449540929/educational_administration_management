package com.cdu.edu.department;

import com.cdu.edu.course.CourseType;
import com.cdu.edu.course.elective.ElectiveCourse;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.sports.SportsElectiveCourse;
import com.cdu.edu.index.Identity;
import com.cdu.edu.student.Student;
import com.cdu.edu.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 部门类的Service层
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/17 0017 上午 9:36
 * @since jdk 10.0.1
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * description: 用于部门登录
     *
     * @param departmentId       部门Id
     * @param departmentPassword 部门密码
     * @return com.cdu.edu.department.Department 根据Id与密码寻找到的部门实体
     */
    public Department login(String departmentId, String departmentPassword) {
        Department department = departmentRepository.findByDepartmentId(departmentId);
        return department == null || departmentPassword.equals(department.getDepartmentPassword()) ? department : null;
    }

    /**
     * description: 用于保存部门实体
     *
     * @param department 需要保存的部门实体
     */
    public void insert(Department department) {
        departmentRepository.save(department);
    }

    /**
     * description: 用于部门设置教师所属部门
     *
     * @return java.util.List<E> 在数据库中的所有部门
     */
    public List<Department> getDepartment() {
        return departmentRepository.findAll();
    }

    /**
     * description: 用于部门确认设置教师
     *
     * @param departmentId 部门Id
     * @return java.lang.String 模版路径
     */
    public String getDepartmentName(String departmentId) {
        return departmentRepository.findByDepartmentId(departmentId).getDepartmentName();
    }

    public Map<String, String> getDepartmentName(Object[] courseArray, CourseType courseType) {
        Map<String, String> departmentNameMap = new HashMap<>(courseArray.length);
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                for (Object course : courseArray) {
                    ProfessionalCourse professionalCourse = (ProfessionalCourse) course;
                    departmentNameMap.put(professionalCourse.getAcademyId(),
                            departmentRepository.findByDepartmentId(professionalCourse.getAcademyId()).getDepartmentName());
                }
                break;
            case PUBLIC_COURSE:
                for (Object course : courseArray) {
                    PublicCourse publicCourse = (PublicCourse) course;
                    departmentNameMap.put(publicCourse.getAcademyId(),
                            departmentRepository.findByDepartmentId(publicCourse.getAcademyId()).getDepartmentName());
                }
                break;
            case ELECTIVE_COURSE:
                for (Object course : courseArray) {
                    ElectiveCourse electiveCourse = (ElectiveCourse) course;
                    departmentNameMap.put(electiveCourse.getAcademyId(),
                            departmentRepository.findByDepartmentId(electiveCourse.getAcademyId()).getDepartmentName());
                }
                break;
            case SPORTS_COURSE:
                for (Object course : courseArray) {
                    SportsElectiveCourse sportsElectiveCourse = (SportsElectiveCourse) course;
                    departmentNameMap.put(sportsElectiveCourse.getAcademyId(),
                            departmentRepository.findByDepartmentId(sportsElectiveCourse.getAcademyId()).getDepartmentName());
                }
                break;
            default:
                break;
        }
        return departmentNameMap;
    }

    /**
     * description: 用于部门选择教师
     *
     * @param teacherList 教师List
     * @param studentList 学生List
     * @param identity    身份，教师List和学生List只能有一个不为空，identity用于标明放入的是哪一个List
     * @return
     */
    public List<String> getDepartmentName(List<Teacher> teacherList, List<Student> studentList,
                                          Identity identity) {
        List<String> departmentNameList = new ArrayList<>((teacherList == null ? 0 :
                teacherList.size()) + (studentList == null ? 0 : studentList.size()));
        switch (identity) {
            case TEACHER:
                assert teacherList != null;
                for (Teacher teacher : teacherList) {
                    departmentNameList.add(departmentRepository.findByDepartmentId(teacher.getTeacherDepartment()).getDepartmentName());
                }
                break;
            case STUDENT:
                assert studentList != null;
                for (Student student : studentList) {
                    departmentNameList.add(departmentRepository.findByDepartmentId(student.getStudentAcademy()).getDepartmentName());
                }
                break;
            default:
                break;
        }
        return departmentNameList;
    }

}
