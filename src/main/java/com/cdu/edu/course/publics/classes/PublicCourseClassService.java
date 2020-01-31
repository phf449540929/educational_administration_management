package com.cdu.edu.course.publics.classes;

import com.cdu.edu.course.publics.PublicCourse;
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
public class PublicCourseClassService {

    @Autowired
    private PublicCourseClassRepository publicCourseClassRepository;

    public void setCourseClass(int courseId, String[] classArray) {
        for (String studentClass : classArray) {
            publicCourseClassRepository.save(new PublicCourseClass(courseId, studentClass));
        }
    }

    public Map<Integer, List<PublicCourseClass>> getCourseClass(List<PublicCourse> publicCourseList) {
        Map<Integer, List<PublicCourseClass>> publicCourseClassMap = new HashMap<>(publicCourseList.size());
        for (PublicCourse publicCourse : publicCourseList) {
            publicCourseClassMap.put(publicCourse.getCourseId(),
                    publicCourseClassRepository.findByCourseId(publicCourse.getCourseId()));
        }
        return publicCourseClassMap;
    }

    public List<PublicCourseClass> getCourse(Student student){
        return publicCourseClassRepository.findByStudentClass(student.getStudentClass());
    }


}
