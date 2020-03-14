package com.cdu.edu.student;

import com.cdu.edu.SetSessionUtil;
import com.cdu.edu.course.*;
import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.course.elective.school.SchoolElectiveCourseService;
import com.cdu.edu.course.elective.school.student.SchoolElectiveCourseStudent;
import com.cdu.edu.course.elective.school.student.SchoolElectiveCourseStudentService;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.professional.ProfessionalCourseService;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClass;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClassService;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.publics.PublicCourseService;
import com.cdu.edu.course.publics.classes.PublicCourseClass;
import com.cdu.edu.course.publics.classes.PublicCourseClassService;
import com.cdu.edu.department.DepartmentService;
import com.cdu.edu.index.Identity;
import com.cdu.edu.index.LoginForm;
import com.cdu.edu.score.ScoreService;
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

    @Autowired
    ScoreService scoreService;

    @Autowired
    SetCourseUtil setCourseUtil;

    @Autowired
    SetCourseLengthUtil setCourseLengthUtil;

    @Autowired
    SetSessionUtil setSessionUtil;

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
            model.addAttribute("studentAcademyName",
                    departmentService.getDepartmentName(student.getStudentAcademy()));
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
     * description: save setProfessionalCourse the professional course, and
     * find the department/course/return.html
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

    /**
     * description: 学生查看自己的课表
     *
     * @param model   SpringMVC的Model
     * @param session JavaWeb的Session
     * @return java.lang.String 模版路径
     */
    @RequestMapping("course/look.do")
    public String look(Model model, HttpSession session) {
        AbstractCourse[][] courseArrayArray = new AbstractCourse[13][8];
        int[][] courseLengthArrayArray = new int[13][8];
        Student student = (Student) session.getAttribute("student");

        List<SchoolElectiveCourseStudent> schoolElectiveCourseStudentList =
                schoolElectiveCourseStudentService.getCourse(student);
        List<SchoolElectiveCourse> schoolElectiveCourseList =
                schoolElectiveCourseService.getSchoolElectiveCourse(schoolElectiveCourseStudentList);
        SchoolElectiveCourse[][] schoolElectiveCourseArrayArray =
                setCourseUtil.getSchoolElectiveCourse(schoolElectiveCourseList);
        setCourseUtil.setSchoolElectiveCourse(courseArrayArray, schoolElectiveCourseArrayArray);
        int[][] schoolElectiveCourseLengthArrayArray =
                setCourseLengthUtil.getSchoolElectiveCourseLength(schoolElectiveCourseArrayArray);
        setCourseLengthUtil.setSchoolElectiveCourseLength(courseLengthArrayArray,
                schoolElectiveCourseLengthArrayArray);

        List<ProfessionalCourseClass> professionalCourseClassList =
                professionalCourseClassService.getCourse(student);
        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse(professionalCourseClassList);
        ProfessionalCourse[][] professionalCourseArrayArray =
                setCourseUtil.getProfessionalCourse(professionalCourseList);
        setCourseUtil.setProfessionalCourse(courseArrayArray, professionalCourseArrayArray);
        int[][] professionalCourseLengthArrayArray =
                setCourseLengthUtil.getProfessionalCourseLength(professionalCourseArrayArray);
        setCourseLengthUtil.setProfessionalCourseLength(courseLengthArrayArray,
                professionalCourseLengthArrayArray);

        List<PublicCourseClass> publicCourseClassList = publicCourseClassService.getCourse(student);
        List<PublicCourse> publicCourseList =
                publicCourseService.getPublicCourse(publicCourseClassList);
        PublicCourse[][] publicCourseArrayArray = setCourseUtil.getPublicCourse(publicCourseList);
        setCourseUtil.setPublicCourse(courseArrayArray, publicCourseArrayArray);
        int[][] publicCourseLengthArrayArray =
                setCourseLengthUtil.getPublicCourseLength(publicCourseArrayArray);
        setCourseLengthUtil.setPublicCourseLength(courseLengthArrayArray,
                publicCourseLengthArrayArray);

        List<List<AbstractCourse>> courseListList = new ArrayList<>();
        for (AbstractCourse[] courseArray : courseArrayArray) {
            List<AbstractCourse> courseList = new ArrayList<>(Arrays.asList(courseArray));
            courseListList.add(courseList);
        }

        model.addAttribute("courseListList", courseListList);
        model.addAttribute("courseLengthArrayArray", courseLengthArrayArray);

        model.addAttribute("teacherNameMap",
                teacherService.getTeacherName(professionalCourseArrayArray,
                        schoolElectiveCourseArrayArray));

        setSessionUtil.setCourseDay(session);
        return "student/course/look";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/set.do")
    public String school(Model model, HttpSession session) {
        List<SchoolElectiveCourse> schoolElectiveCourseList =
                schoolElectiveCourseService.getSchoolElectiveCourse();
        model.addAttribute("courseList", schoolElectiveCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(schoolElectiveCourseList.toArray(),
                        CourseType.SCHOOL_ELECTIVE_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(schoolElectiveCourseList.toArray(),
                        CourseType.SCHOOL_ELECTIVE_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(schoolElectiveCourseList.toArray(),
                        CourseType.SCHOOL_ELECTIVE_COURSE));

        Map<Integer, List<SchoolElectiveCourseStudent>> courseStudentMap =
                schoolElectiveCourseStudentService.getCourseStudent(schoolElectiveCourseList);
        session.setAttribute("courseStudentMap", courseStudentMap);

        setSessionUtil.setTeacherRank(session);
        setSessionUtil.setCourseType(session);
        setSessionUtil.setCourseSemester(session);
        setSessionUtil.setCourseDay(session);
        return "student/course/elective/1_select";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/select.do")
    public String selectSchool(int[] courseIdArray, Model model, HttpSession session) {
        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList",
                schoolElectiveCourseService.getSchoolElectiveCourse(courseIdArray));
        return "student/course/elective/2_ensure";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/ensure.do")
    public String saveSchool(HttpSession session, Model model) {
        schoolElectiveCourseStudentService.setCourseStudent((int[]) session.getAttribute(
                "courseIdArray"), (Student) session.getAttribute("student"));
        model.addAttribute("information", "您已完成选修课选课");
        return "student/return";
    }

    @RequestMapping("score/select.do")
    public String toScore(HttpSession session) {
        setSessionUtil.setCourseSemester(session);
        return "student/score/1_select";
    }

    @RequestMapping("score/look.do")
    public String lookScore(HttpSession session, Model model, String year,
                            CourseSemester semester) {
        session.setAttribute("year", year);
        session.setAttribute("semester", semester);
        Student student = (Student) session.getAttribute("student");

        List<SchoolElectiveCourseStudent> schoolElectiveCourseStudentList =
                schoolElectiveCourseStudentService.getCourse(student);
        List<SchoolElectiveCourse> schoolElectiveCourseList =
                schoolElectiveCourseService.getSchoolElectiveCourse(schoolElectiveCourseStudentList);

        List<ProfessionalCourseClass> professionalCourseClassList =
                professionalCourseClassService.getCourse(student);
        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse(professionalCourseClassList);

        List<PublicCourseClass> publicCourseClassList = publicCourseClassService.getCourse(student);
        List<PublicCourse> publicCourseList =
                publicCourseService.getPublicCourse(publicCourseClassList);

        List<AbstractCourse> courseList =
                new ArrayList<>(schoolElectiveCourseList.size() + professionalCourseList.size() + publicCourseList.size());
        courseList.addAll(schoolElectiveCourseList);
        courseList.addAll(professionalCourseList);
        courseList.addAll(publicCourseList);
        courseList.removeIf(course -> !course.getCourseYear().equals(year) || course.getCourseSemester() != semester);

        model.addAttribute("courseList", courseList);
        model.addAttribute("scoreList", scoreService.getScore(student, courseList));
        return "student/score/2_look";
    }


}
