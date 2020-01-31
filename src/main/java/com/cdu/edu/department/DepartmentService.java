package com.cdu.edu.department;

import com.cdu.edu.course.CourseType;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.course.elective.sports.SportsElectiveCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * description: the service by operation for departments
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/17 0017 上午 9:36
 * @since jdk11
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * description: login's service for departments
     *
     * @param departmentId       the department's id
     * @param departmentPassword the department's password
     * @return com.cdu.edu.department.Department
     */
    public Department login(String departmentId, String departmentPassword) {
        Department department = departmentRepository.findByDepartmentId(departmentId);
        return department == null || departmentPassword.equals(department.getDepartmentPassword()) ? department : null;
    }

    public void insert(Department department) {
        departmentRepository.save(department);
    }

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
            case SCHOOL_ELECTIVE_COURSE:
                for (Object course : courseArray) {
                    SchoolElectiveCourse schoolElectiveCourse = (SchoolElectiveCourse) course;
                    departmentNameMap.put(schoolElectiveCourse.getAcademyId(),
                            departmentRepository.findByDepartmentId(schoolElectiveCourse.getAcademyId()).getDepartmentName());
                }
                break;
            case SPORTS_ELECTIVE_COURSE:
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

}
