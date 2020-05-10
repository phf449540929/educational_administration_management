package com.cdu.edu.teacher;

import com.cdu.edu.course.CourseType;
import com.cdu.edu.course.elective.ElectiveCourse;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.sports.SportsElectiveCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: the service by operation for teachers
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 16:54
 * @since jdk11
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * description: login's service for teachers
     *
     * @param teacherId       the teacher's id
     * @param teacherPassword the teacher's password
     * @return com.cdu.edu.teacher.Teacher
     */
    public Teacher login(String teacherId, String teacherPassword) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId);
        return teacher == null || teacherPassword.equals(teacher.getTeacherPassword()) ? teacher
                : null;
    }

    /**
     * description: 用于部门设置教师
     *
     * @param teacher
     */
    public void insert(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void delete(Teacher teacher){
        teacherRepository.delete(teacher);
    }

    /**
     * description: 用于部门获取教师列表
     *
     * @return java.util.List<E> 数据库中的全部教师
     */
    public List<Teacher> getTeacher() {
        return teacherRepository.findAll();
    }

    /**
     *
     *
     * @param teacherId
     * @return
     */
    public Teacher getTeacher(String teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }

    public Map<String, String> getTeacherName(Object[] courseArray, CourseType courseType) {
        Map<String, String> teacherNameMap = new HashMap<>(courseArray.length);
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((ProfessionalCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherNameMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherName());
                    }
                }
                break;
            case PUBLIC_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((PublicCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherNameMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherName());
                    }
                }
                break;
            case ELECTIVE_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((ElectiveCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherNameMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherName());
                    }
                }
                break;
            case SPORTS_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((SportsElectiveCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherNameMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherName());
                    }
                }
                break;
            default:
                break;
        }
        return teacherNameMap;
    }

    public Map<String, String> getTeacherName(ElectiveCourse[][] electiveCoursesArrayArray,
                                              ProfessionalCourse[][] professionalCourseArrayArray
            , PublicCourse[][] publicCourseArrayArray) {
        Map<String, String> teacherNameMap = new HashMap<>(8 * 13);
        for (ElectiveCourse[] courses : electiveCoursesArrayArray) {
            for (ElectiveCourse course : courses) {
                String teacherId = course == null ? null : course.getTeacherId();
                if (teacherId != null) {
                    teacherNameMap.put(teacherId,
                            teacherRepository.findByTeacherId(teacherId).getTeacherName());
                }
            }
        }
        for (ProfessionalCourse[] courses : professionalCourseArrayArray) {
            for (ProfessionalCourse course : courses) {
                String teacherId = course == null ? null : course.getTeacherId();
                if (teacherId != null) {
                    teacherNameMap.put(teacherId,
                            teacherRepository.findByTeacherId(teacherId).getTeacherName());
                }
            }
        }
        for (PublicCourse[] courses : publicCourseArrayArray) {
            for (PublicCourse course : courses) {
                String teacherId = course == null ? null : course.getTeacherId();
                if (teacherId != null) {
                    teacherNameMap.put(teacherId,
                            teacherRepository.findByTeacherId(teacherId).getTeacherName());
                }
            }
        }
        return teacherNameMap;
    }

    public Map<String, TeacherRank> getTeacherRank(Object[] courseArray, CourseType courseType) {
        Map<String, TeacherRank> teacherRankMap = new HashMap<>(courseArray.length);
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((ProfessionalCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherRankMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherRank());
                    }
                }
                break;
            case PUBLIC_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((PublicCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherRankMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherRank());
                    }
                }
                break;
            case ELECTIVE_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((ElectiveCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherRankMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherRank());
                    }
                }
                break;
            case SPORTS_COURSE:
                for (Object course : courseArray) {
                    String teacherId = ((SportsElectiveCourse) course).getTeacherId();
                    if (teacherId != null) {
                        teacherRankMap.put(teacherId,
                                teacherRepository.findByTeacherId(teacherId).getTeacherRank());
                    }
                }
                break;
            default:
                break;
        }

        return teacherRankMap;
    }

}
