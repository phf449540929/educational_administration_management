package com.cdu.edu.student;

import com.cdu.edu.course.AbstractCourse;
import com.cdu.edu.course.CourseDay;
import com.cdu.edu.course.CourseSemester;
import com.cdu.edu.course.CourseType;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.professional.ProfessionalCourseService;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClass;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClassService;
import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.course.elective.school.SchoolElectiveCourseService;
import com.cdu.edu.course.elective.school.student.SchoolElectiveCourseStudent;
import com.cdu.edu.course.elective.school.student.SchoolElectiveCourseStudentService;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.publics.PublicCourseService;
import com.cdu.edu.course.publics.classes.PublicCourseClass;
import com.cdu.edu.course.publics.classes.PublicCourseClassService;
import com.cdu.edu.department.DepartmentService;
import com.cdu.edu.index.Identity;
import com.cdu.edu.index.LoginForm;
import com.cdu.edu.teacher.TeacherRank;
import com.cdu.edu.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * description: the controller by operation for students
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:05
 * @since jdk11
 */
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    SchoolElectiveCourseService schoolElectiveCourseService;

    @Autowired
    SchoolElectiveCourseStudentService schoolElectiveCourseStudentService;

    @Autowired
    ProfessionalCourseService professionalCourseService;

    @Autowired
    ProfessionalCourseClassService professionalCourseClassService;

    @Autowired
    PublicCourseService publicCourseService;

    @Autowired
    PublicCourseClassService publicCourseClassService;

    /**
     * description: the IndexController's login by student would come here
     *
     * @param username the student's username
     * @param password the student's password
     * @return java.lang.String
     */
    @RequestMapping("login.do")
    public String login(String username, String password, Model model, HttpSession session) {
        Student student = studentService.login(username, password);
        if (student != null) {
            session.setAttribute("student", student);
            model.addAttribute("studentAcademyName", departmentService.getDepartmentName(student.getStudentAcademy()));
            return "student/index";
        } else {
            LoginForm loginForm = new LoginForm();
            loginForm.setUsername(username);
            loginForm.setIdentity(Identity.STUDENT);
            loginForm.setMessage("您的用户名或密码错误");
            model.addAttribute("loginForm", loginForm);
            return "index";
        }
    }

    /**
     * description: save setProfessionalCourse the professional course, and find the department/course/achieve.html
     *
     * @return java.lang.String
     */
    @RequestMapping("return.do")
    public String returnIndex() {
        return "student/index";
    }

    @RequestMapping("insert.do")
    @ResponseBody
    public Student insert() {
        Student student = new Student();
        student.setStudentId("201610414423");
        student.setStudentPassword("123456");
        student.setStudentName("王寅");
        student.setStudentGender("男");
        student.setStudentAcademy("000000000001");
        student.setStudentGrade("2016");
        student.setStudentMajor("软件工程");
        student.setStudentClass("4");
        studentService.insert(student);

        return student;
    }

    @RequestMapping("course/look.do")
    public String look(Model model, HttpSession session) {
        AbstractCourse[][] courseArrayArray = new AbstractCourse[13][8];
        int[][] courseLengthArrayArray = new int[13][8];

        List<SchoolElectiveCourseStudent> schoolElectiveCourseList =
                schoolElectiveCourseStudentService.getCourse(((Student) session.getAttribute("student")));
        SchoolElectiveCourse[][] schoolElectiveCourseArrayArray =
                schoolElectiveCourseService.getSchoolElectiveCourse(schoolElectiveCourseList);
        int[][] schoolElectiveCourseLengthArrayArray =
                schoolElectiveCourseService.getSchoolElectiveCourseLength(schoolElectiveCourseArrayArray);
        schoolElectiveCourseService.setSchoolElectiveCourse(courseArrayArray, schoolElectiveCourseArrayArray);
        schoolElectiveCourseService.setSchoolElectiveCourseLength(
                courseLengthArrayArray, schoolElectiveCourseLengthArrayArray);

        List<ProfessionalCourseClass> professionalCourseClassList =
                professionalCourseClassService.getCourse(((Student) session.getAttribute("student")));
        ProfessionalCourse[][] professionalCourseArrayArray =
                professionalCourseService.getProfessionalCourse(professionalCourseClassList);
        int[][] professionalCourseLengthArrayArray =
                professionalCourseService.getProfessionalCourseLength(professionalCourseArrayArray);
        professionalCourseService.setProfessionalCourse(courseArrayArray, professionalCourseArrayArray);
        professionalCourseService.setProfessionalCourseLength(
                courseLengthArrayArray, professionalCourseLengthArrayArray);

        List<PublicCourseClass> publicCourseClassList =
                publicCourseClassService.getCourse(((Student) session.getAttribute("student")));
        PublicCourse[][] publicCourseArrayArray =
                publicCourseService.getPublicCourse(publicCourseClassList);
        int[][] publicCourseLengthArrayArray =
                publicCourseService.getPublicCourseLength(publicCourseArrayArray);
        publicCourseService.setPublicCourse(courseArrayArray, publicCourseArrayArray);
        publicCourseService.setPublicCourseLength(
                courseLengthArrayArray, publicCourseLengthArrayArray);

        List<List<AbstractCourse>> courseListList = new ArrayList<>();
        for (AbstractCourse[] courseArray : courseArrayArray) {
            List<AbstractCourse> courseList = new ArrayList<>(Arrays.asList(courseArray));
            courseListList.add(courseList);
        }

        model.addAttribute("courseListList", courseListList);
        model.addAttribute("courseLengthArrayArray", courseLengthArrayArray);

        model.addAttribute("teacherNameMap",
                teacherService.getTeacherName(professionalCourseArrayArray, schoolElectiveCourseArrayArray));

        session.setAttribute("DAY_1", CourseDay.DAY_1);
        session.setAttribute("DAY_2", CourseDay.DAY_2);
        session.setAttribute("DAY_3", CourseDay.DAY_3);
        session.setAttribute("DAY_4", CourseDay.DAY_4);
        session.setAttribute("DAY_5", CourseDay.DAY_5);
        session.setAttribute("DAY_6", CourseDay.DAY_6);
        session.setAttribute("DAY_7", CourseDay.DAY_7);
        return "student/course/look";
    }

    /**
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/set.do")
    public String school(Model model, HttpSession session) {
        List<SchoolElectiveCourse> schoolElectiveCourseList = schoolElectiveCourseService.getSchoolElectiveCourse();
        model.addAttribute("courseList", schoolElectiveCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(schoolElectiveCourseList.toArray(), CourseType.SCHOOL_ELECTIVE_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(schoolElectiveCourseList.toArray(), CourseType.SCHOOL_ELECTIVE_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(schoolElectiveCourseList.toArray(), CourseType.SCHOOL_ELECTIVE_COURSE));

        Map<Integer, List<SchoolElectiveCourseStudent>> courseStudentMap =
                schoolElectiveCourseStudentService.getCourseStudent(schoolElectiveCourseList);
        session.setAttribute("courseStudentMap", courseStudentMap);

        session.setAttribute("PROFESSIONAL_COURSE", CourseType.PROFESSIONAL_COURSE);
        session.setAttribute("PUBLIC_COURSE", CourseType.PUBLIC_COURSE);
        session.setAttribute("SCHOOL_ELECTIVE_COURSE", CourseType.SCHOOL_ELECTIVE_COURSE);
        session.setAttribute("SPORTS_ELECTIVE_COURSE", CourseType.SPORTS_ELECTIVE_COURSE);

        session.setAttribute("LAST_HALF_SEMESTER", CourseSemester.LAST_HALF_SEMESTER);
        session.setAttribute("NEXT_HALF_SEMESTER", CourseSemester.NEXT_HALF_SEMESTER);

        session.setAttribute("DAY_1", CourseDay.DAY_1);
        session.setAttribute("DAY_2", CourseDay.DAY_2);
        session.setAttribute("DAY_3", CourseDay.DAY_3);
        session.setAttribute("DAY_4", CourseDay.DAY_4);
        session.setAttribute("DAY_5", CourseDay.DAY_5);
        session.setAttribute("DAY_6", CourseDay.DAY_6);
        session.setAttribute("DAY_7", CourseDay.DAY_7);

        session.setAttribute("ASSISTANT", TeacherRank.ASSISTANT);
        session.setAttribute("LECTURER", TeacherRank.LECTURER);
        session.setAttribute("ASSOCIATE_PROFESSOR", TeacherRank.ASSOCIATE_PROFESSOR);
        session.setAttribute("PROFESSOR", TeacherRank.PROFESSOR);

        return "student/course/elective/school/select";
    }

    /**
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/select.do")
    public String selectSchool(int[] courseIdArray, Model model, HttpSession session) {
        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList", schoolElectiveCourseService.getSchoolElectiveCourse(courseIdArray));
        return "student/course/elective/school/ensure";
    }

    /**
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/ensure.do")
    public String saveSchool(HttpSession session) {
        schoolElectiveCourseStudentService.setCourseStudent(
                (int[]) session.getAttribute("courseIdArray"), (Student) session.getAttribute("student"));
        return "student/course/achieve";
    }
}
