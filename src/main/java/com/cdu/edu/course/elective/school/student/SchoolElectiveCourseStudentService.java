package com.cdu.edu.course.elective.school.student;

import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/11/9 0009 上午 10:44
 * @since jdk
 */
@Service
public class SchoolElectiveCourseStudentService {

    @Autowired
    private SchoolElectiveCourseStudentRepository schoolElectiveCourseStudentRepository;

    public void setCourseStudent(int[] courseIdArray, Student student) {
        for (int courseId : courseIdArray) {
            schoolElectiveCourseStudentRepository.save(new SchoolElectiveCourseStudent(courseId,
                    student.getStudentId()));
        }
    }

    public List<SchoolElectiveCourseStudent> getCourseStudent(SchoolElectiveCourse course){
        return schoolElectiveCourseStudentRepository.findByCourseId(course.getCourseId());
    }

    public Map<Integer, List<SchoolElectiveCourseStudent>> getCourseStudent(List<SchoolElectiveCourse> schoolElectiveCourseList) {
        Map<Integer, List<SchoolElectiveCourseStudent>> schoolElectiveCourseStudentMap =
                new HashMap<>(schoolElectiveCourseList.size());
        for (SchoolElectiveCourse schoolElectiveCourse : schoolElectiveCourseList) {
            schoolElectiveCourseStudentMap.put(schoolElectiveCourse.getCourseId(),
                    schoolElectiveCourseStudentRepository.findByCourseId(schoolElectiveCourse.getCourseId()));
        }
        return schoolElectiveCourseStudentMap;
    }

    public Map<Integer, Integer> getCourseStudentNumber(List<SchoolElectiveCourse> schoolElectiveCourseList) {
        Map<Integer, List<SchoolElectiveCourseStudent>> schoolElectiveCourseStudentMap =
                this.getCourseStudent(schoolElectiveCourseList);
        Map<Integer, Integer> schoolElectiveCourseStudentNumberMap =
                new HashMap<>(schoolElectiveCourseStudentMap.size());
        for (SchoolElectiveCourse course : schoolElectiveCourseList) {
            int courseId = course.getCourseId();
            schoolElectiveCourseStudentNumberMap.put(courseId,
                    schoolElectiveCourseStudentMap.get(courseId).size());
        }
        return schoolElectiveCourseStudentNumberMap;
    }


    public List<SchoolElectiveCourseStudent> getCourse(Student student) {
        return schoolElectiveCourseStudentRepository.findByStudentId(student.getStudentId());
    }
}
