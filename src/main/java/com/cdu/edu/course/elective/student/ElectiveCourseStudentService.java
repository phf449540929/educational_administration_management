package com.cdu.edu.course.elective.student;

import com.cdu.edu.course.elective.ElectiveCourse;
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
public class ElectiveCourseStudentService {

    @Autowired
    private ElectiveCourseStudentRepository electiveCourseStudentRepository;

    public void setCourseStudent(int[] courseIdArray, Student student) {
        for (int courseId : courseIdArray) {
            electiveCourseStudentRepository.save(new ElectiveCourseStudent(courseId,
                    student.getStudentId()));
        }
    }

    public List<ElectiveCourseStudent> getCourseStudent(ElectiveCourse course){
        return electiveCourseStudentRepository.findByCourseId(course.getCourseId());
    }

    public Map<Integer, List<ElectiveCourseStudent>> getCourseStudent(List<ElectiveCourse> electiveCourseList) {
        Map<Integer, List<ElectiveCourseStudent>> schoolElectiveCourseStudentMap =
                new HashMap<>(electiveCourseList.size());
        for (ElectiveCourse electiveCourse : electiveCourseList) {
            schoolElectiveCourseStudentMap.put(electiveCourse.getCourseId(),
                    electiveCourseStudentRepository.findByCourseId(electiveCourse.getCourseId()));
        }
        return schoolElectiveCourseStudentMap;
    }

    public Map<Integer, Integer> getCourseStudentNumber(List<ElectiveCourse> electiveCourseList) {
        Map<Integer, List<ElectiveCourseStudent>> schoolElectiveCourseStudentMap =
                this.getCourseStudent(electiveCourseList);
        Map<Integer, Integer> schoolElectiveCourseStudentNumberMap =
                new HashMap<>(schoolElectiveCourseStudentMap.size());
        for (ElectiveCourse course : electiveCourseList) {
            int courseId = course.getCourseId();
            schoolElectiveCourseStudentNumberMap.put(courseId,
                    schoolElectiveCourseStudentMap.get(courseId).size());
        }
        return schoolElectiveCourseStudentNumberMap;
    }


    public List<ElectiveCourseStudent> getCourse(Student student) {
        return electiveCourseStudentRepository.findByStudentId(student.getStudentId());
    }
}
