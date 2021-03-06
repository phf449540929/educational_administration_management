package com.cdu.edu.student;

import com.cdu.edu.course.CourseType;
import com.cdu.edu.course.elective.student.ElectiveCourseStudent;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClass;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.publics.classes.PublicCourseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * description: the service by operation for students
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 16:54
 * @since jdk11
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SortUtil sortUtil;

    /**
     * description: login's service for students
     *
     * @param studentId       the student's id
     * @param studentPassword the student's password
     * @return com.cdu.edu.student.Student
     */
    public Student login(String studentId, String studentPassword) {
        Student student = studentRepository.findByStudentId(studentId);
        return student == null || studentPassword.equals(student.getStudentPassword()) ? student
                : null;
    }

    public void insert(Student student) {
        studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public List<Student> getStudent() {
        return studentRepository.findAll();
    }

    public Student getStudent(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public String[] getAllClass() {
        List<Student> students = studentRepository.findAllByOrderByStudentClass();
        Set<String> studentClasses = new HashSet<>();
        for (Student student : students) {
            studentClasses.add(student.getStudentClass());
        }
        return sortUtil.quickSort(studentClasses);
    }

    public List<Student> getCourseStudent(List<ElectiveCourseStudent> electiveCourseStudentList) {
        List<String> studentIdList = new ArrayList<>(electiveCourseStudentList.size());
        for (ElectiveCourseStudent electiveCourseStudent : electiveCourseStudentList) {
            studentIdList.add(electiveCourseStudent.getStudentId());
        }
        return studentRepository.findByStudentId(studentIdList);
    }

    public List<Student> getCourseClassStudent(List<ProfessionalCourseClass> professionalCourseClassList, List<PublicCourseClass> publicCourseClassList, CourseType courseType) {
        List<String> classList = new ArrayList<>((professionalCourseClassList == null ? 0 :
                professionalCourseClassList.size()) + (publicCourseClassList == null ? 0 :
                publicCourseClassList.size()));
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                assert professionalCourseClassList != null;
                for (ProfessionalCourseClass courseClass : professionalCourseClassList) {
                    classList.add(courseClass.getStudentClass());
                }
                break;
            case PUBLIC_COURSE:
                assert publicCourseClassList != null;
                for (PublicCourseClass courseClass : publicCourseClassList) {
                    classList.add(courseClass.getStudentClass());
                }
                break;
            default:
                break;
        }
        List<Student> studentList = new ArrayList<>();
        for (String studentClass : classList) {
            studentList.addAll(studentRepository.findByStudentClass(studentClass));
        }
        return studentList;
    }

    public int getCourseClassStudentNumber(String[] classArray) {
        int student = 0;
        for (String classes : classArray) {
            student += studentRepository.findByStudentClass(classes).size();
        }
        return student;
    }

    public Map<Integer, Integer> getCourseClassStudentNumber(List<ProfessionalCourse> professionalCourseList, Map<Integer, List<ProfessionalCourseClass>> professionalCourseClassMap, List<PublicCourse> publicCourseList, Map<Integer, List<PublicCourseClass>> publicCourseClassMap, CourseType courseType) {
        Map<Integer, Integer> courseClassStudentNumberMap =
                new HashMap<>((professionalCourseList != null ? professionalCourseList.size() : 0) + (publicCourseList != null ? publicCourseList.size() : 0));
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                assert professionalCourseList != null;
                for (ProfessionalCourse professionalCourse : professionalCourseList) {
                    int student = 0;
                    for (ProfessionalCourseClass professionalCourseClass :
                            professionalCourseClassMap.get(professionalCourse.getCourseId())) {
                        student += studentRepository.findByStudentClass(professionalCourseClass.getStudentClass()).size();
                    }
                    courseClassStudentNumberMap.put(professionalCourse.getCourseId(), student);
                }
                break;
            case PUBLIC_COURSE:
                assert publicCourseList != null;
                for (PublicCourse publicCourse : publicCourseList) {
                    int student = 0;
                    for (PublicCourseClass publicCourseClass :
                            publicCourseClassMap.get(publicCourse.getCourseId())) {
                        student += studentRepository.findByStudentClass(publicCourseClass.getStudentClass()).size();
                    }
                    courseClassStudentNumberMap.put(publicCourse.getCourseId(), student);
                }
                break;
            default:
                break;
        }
        return courseClassStudentNumberMap;
    }


}
