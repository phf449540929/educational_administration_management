package com.cdu.edu;

import com.cdu.edu.course.CourseDay;
import com.cdu.edu.course.CourseSemester;
import com.cdu.edu.course.CourseType;
import com.cdu.edu.teacher.TeacherRank;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/2/12 0012 下午 14:45
 * @since jdk 12.0.2
 */
@Component
public class SetSessionUtil {

    public void setCourseType(HttpSession session) {
        session.setAttribute("PROFESSIONAL_COURSE", CourseType.PROFESSIONAL_COURSE);
        session.setAttribute("PUBLIC_COURSE", CourseType.PUBLIC_COURSE);
        session.setAttribute("SCHOOL_ELECTIVE_COURSE", CourseType.SCHOOL_ELECTIVE_COURSE);
        session.setAttribute("SPORTS_ELECTIVE_COURSE", CourseType.SPORTS_ELECTIVE_COURSE);
    }

    public void setCourseSemester(HttpSession session) {
        session.setAttribute("LAST_HALF_SEMESTER", CourseSemester.LAST_HALF_SEMESTER);
        session.setAttribute("NEXT_HALF_SEMESTER", CourseSemester.NEXT_HALF_SEMESTER);
    }

    public void setCourseDay(HttpSession session) {
        session.setAttribute("DAY_1", CourseDay.DAY_1);
        session.setAttribute("DAY_2", CourseDay.DAY_2);
        session.setAttribute("DAY_3", CourseDay.DAY_3);
        session.setAttribute("DAY_4", CourseDay.DAY_4);
        session.setAttribute("DAY_5", CourseDay.DAY_5);
        session.setAttribute("DAY_6", CourseDay.DAY_6);
        session.setAttribute("DAY_7", CourseDay.DAY_7);
    }

    public void setTeacherRank(HttpSession session) {
        session.setAttribute("ASSISTANT", TeacherRank.ASSISTANT);
        session.setAttribute("LECTURER", TeacherRank.LECTURER);
        session.setAttribute("ASSOCIATE_PROFESSOR", TeacherRank.ASSOCIATE_PROFESSOR);
        session.setAttribute("PROFESSOR", TeacherRank.PROFESSOR);
    }
}
