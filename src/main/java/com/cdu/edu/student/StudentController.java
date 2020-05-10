package com.cdu.edu.student;

import com.cdu.edu.SetSessionUtil;
import com.cdu.edu.course.*;
import com.cdu.edu.course.elective.ElectiveCourse;
import com.cdu.edu.course.elective.ElectiveCourseService;
import com.cdu.edu.course.elective.student.ElectiveCourseStudent;
import com.cdu.edu.course.elective.student.ElectiveCourseStudentService;
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
import com.cdu.edu.score.Score;
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
    ElectiveCourseService electiveCourseService;

    @Autowired
    ElectiveCourseStudentService electiveCourseStudentService;

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

    @Autowired
    GetCourseUtil getCourseUtil;

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
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/set.do")
    public String school(Model model, HttpSession session) {
        List<ElectiveCourse> electiveCourseList = electiveCourseService.getElectiveCourse();
        model.addAttribute("courseList", electiveCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(electiveCourseList.toArray(),
                        CourseType.ELECTIVE_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(electiveCourseList.toArray(),
                        CourseType.ELECTIVE_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(electiveCourseList.toArray(),
                        CourseType.ELECTIVE_COURSE));

        Map<Integer, List<ElectiveCourseStudent>> courseStudentMap =
                electiveCourseStudentService.getCourseStudent(electiveCourseList);
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
        if (courseIdArray == null || courseIdArray.length == 0) {
            model.addAttribute("information", "您并未选择课程");
            return "error";
        }
        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList", electiveCourseService.getElectiveCourse(courseIdArray));
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
        electiveCourseStudentService.setCourseStudent((int[]) session.getAttribute("courseIdArray"
        ), (Student) session.getAttribute("student"));
        model.addAttribute("information", "您已完成选修课选课");
        return "student/return";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/timetable/get.do")
    public String toTimetable(HttpSession session, Model model) {
        setSessionUtil.setCourseSemester(session);
        return "student/course/timetable/1_select";
    }

    /**
     * description: 学生查看自己的课表
     *
     * @param model   SpringMVC的Model
     * @param session JavaWeb的Session
     * @return java.lang.String 模版路径
     */
    @RequestMapping("course/timetable/select.do")
    public String lookTimetable(String year, CourseSemester semester, Model model,
                                HttpSession session) {
        if (year == null || "".equals(year)) {
            model.addAttribute("information", "您并未输入查看课表的年份");
            return "error";
        } else if (!year.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的查看课表的年份只能为数字");
            return "error";
        } else if (semester == null) {
            model.addAttribute("information", "您并未选择查看课表的学期");
            return "error";
        }

        AbstractCourse[][] courseArrayArray = new AbstractCourse[13][8];
        int[][] courseLengthArrayArray = new int[13][8];
        Student student = (Student) session.getAttribute("student");

        List<ElectiveCourseStudent> electiveCourseStudentList =
                electiveCourseStudentService.getCourse(student);
        List<ElectiveCourse> electiveCourseList =
                electiveCourseService.getElectiveCourse(electiveCourseStudentList);
        electiveCourseList.removeIf(course -> !course.getCourseYear().equals(year) || course.getCourseSemester() != semester);
        ElectiveCourse[][] electiveCourseArrayArray =
                setCourseUtil.getElectiveCourse(electiveCourseList);
        setCourseUtil.setElectiveCourse(courseArrayArray, electiveCourseArrayArray);
        int[][] schoolElectiveCourseLengthArrayArray =
                setCourseLengthUtil.getElectiveCourseLength(electiveCourseArrayArray);
        setCourseLengthUtil.setElectiveCourseLength(courseLengthArrayArray,
                schoolElectiveCourseLengthArrayArray);

        List<ProfessionalCourseClass> professionalCourseClassList =
                professionalCourseClassService.getCourse(student);
        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse(professionalCourseClassList);
        professionalCourseList.removeIf(course -> !course.getCourseYear().equals(year) || course.getCourseSemester() != semester);
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
        publicCourseList.removeIf(course -> !course.getCourseYear().equals(year) || course.getCourseSemester() != semester);
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
                teacherService.getTeacherName(electiveCourseArrayArray,
                        professionalCourseArrayArray, publicCourseArrayArray));
        setSessionUtil.setCourseDay(session);
        return "student/course/timetable/2_look";
    }

    @RequestMapping("score/show/get.do")
    public String toScore(HttpSession session) {
        setSessionUtil.setCourseSemester(session);
        return "student/score/show/1_select";
    }

    @RequestMapping("score/show/select.do")
    public String lookScore(String year, CourseSemester semester, Model model,
                            HttpSession session) {
        if (year == null || "".equals(year)) {
            model.addAttribute("information", "您并未输入查看成绩的年份");
            return "error";
        } else if (!year.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的查看成绩的年份只能为数字");
            return "error";
        } else if (semester == null) {
            model.addAttribute("information", "您并未选择查看成绩的学期");
            return "error";
        }

        session.setAttribute("year", year);
        session.setAttribute("semester", semester);
        Student student = (Student) session.getAttribute("student");

        List<ElectiveCourseStudent> electiveCourseStudentList =
                electiveCourseStudentService.getCourse(student);
        List<ElectiveCourse> electiveCourseList =
                electiveCourseService.getElectiveCourse(electiveCourseStudentList);

        List<ProfessionalCourseClass> professionalCourseClassList =
                professionalCourseClassService.getCourse(student);
        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse(professionalCourseClassList);

        List<PublicCourseClass> publicCourseClassList = publicCourseClassService.getCourse(student);
        List<PublicCourse> publicCourseList =
                publicCourseService.getPublicCourse(publicCourseClassList);

        List<AbstractCourse> courseList =
                new ArrayList<>(electiveCourseList.size() + professionalCourseList.size() + publicCourseList.size());
        courseList.addAll(electiveCourseList);
        courseList.addAll(professionalCourseList);
        courseList.addAll(publicCourseList);
        courseList.removeIf(course -> !course.getCourseYear().equals(year) || course.getCourseSemester() != semester);

        List<Score> scoreList = scoreService.getScore(student, courseList);
        courseList = getCourseUtil.getCourse(courseList, scoreList);

        model.addAttribute("courseList", courseList);
        model.addAttribute("scoreList", scoreList);
        return "student/score/show/2_look";
    }


}
