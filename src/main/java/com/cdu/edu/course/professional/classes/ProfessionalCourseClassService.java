package com.cdu.edu.course.professional.classes;

import com.cdu.edu.course.professional.ProfessionalCourse;
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
public class ProfessionalCourseClassService {

    @Autowired
    private ProfessionalCourseClassRepository professionalCourseClassRepository;

    public void setCourseClass(int courseId, String[] classArray) {
        for (String studentClass : classArray) {
            professionalCourseClassRepository.save(new ProfessionalCourseClass(courseId,
                    studentClass));
        }
    }

    public List<ProfessionalCourseClass> getCourseClass(ProfessionalCourse professionalCourse) {
        return professionalCourseClassRepository.findByCourseId(professionalCourse.getCourseId());
    }

    public Map<Integer, List<ProfessionalCourseClass>> getCourseClass(List<ProfessionalCourse> professionalCourseList) {
        Map<Integer, List<ProfessionalCourseClass>> professionalCourseClassMap =
                new HashMap<>(professionalCourseList.size());
        for (ProfessionalCourse professionalCourse : professionalCourseList) {
            professionalCourseClassMap.put(professionalCourse.getCourseId(),
                    professionalCourseClassRepository.findByCourseId(professionalCourse.getCourseId()));
        }
        return professionalCourseClassMap;
    }

    public List<ProfessionalCourseClass> getCourse(Student student) {
        return professionalCourseClassRepository.findByStudentClass(student.getStudentClass());
    }


}
