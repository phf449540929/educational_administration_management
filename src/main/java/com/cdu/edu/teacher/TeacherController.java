package com.cdu.edu.teacher;

import com.cdu.edu.SetSessionUtil;
import com.cdu.edu.course.AbstractCourse;
import com.cdu.edu.course.CourseType;
import com.cdu.edu.course.SetCourseLengthUtil;
import com.cdu.edu.course.SetCourseUtil;
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
import com.cdu.edu.score.Score;
import com.cdu.edu.score.ScoreService;
import com.cdu.edu.student.Student;
import com.cdu.edu.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * description: the controller by operation for teachers
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:05
 * @since jdk11
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SchoolElectiveCourseService schoolElectiveCourseService;

    @Autowired
    private SchoolElectiveCourseStudentService schoolElectiveCourseStudentService;

    @Autowired
    private ProfessionalCourseService professionalCourseService;

    @Autowired
    private ProfessionalCourseClassService professionalCourseClassService;

    @Autowired
    private PublicCourseService publicCourseService;

    @Autowired
    private PublicCourseClassService publicCourseClassService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SetCourseUtil setCourseUtil;

    @Autowired
    private SetCourseLengthUtil setCourseLengthUtil;

    @Autowired
    private SetSessionUtil setSessionUtil;


    /**
     * description: the IndexController's login by teacher would come here
     *
     * @param username the teacher's username
     * @param password the teacher's password
     * @return java.lang.String
     */
    @RequestMapping("login.do")
    public String login(String username, String password, Model model, HttpSession session) {
        Teacher teacher = teacherService.login(username, password);
        if (teacher != null) {
            session.setAttribute("teacher", teacher);
            setSessionUtil.setTeacherRank(session);
            session.setAttribute("teacherDepartmentName",
                    departmentService.getDepartmentName(teacher.getTeacherDepartment()));
            return "teacher/index";
        } else {
            LoginForm loginForm = new LoginForm();
            loginForm.setUsername(username);
            loginForm.setIdentity(Identity.TEACHER);
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
        return "teacher/index";
    }

    @RequestMapping("insert.do")
    @ResponseBody
    public Teacher insert() {
        Teacher teacher = new Teacher();
        teacher.setTeacherId("201610414423");
        teacher.setTeacherPassword("123456");
        teacher.setTeacherName("王寅");
        teacher.setTeacherGender("男");
        teacher.setTeacherDepartment("000000000002");
        teacher.setTeacherRank(TeacherRank.ASSISTANT);
        teacherService.insert(teacher);
        return teacher;
    }

    /**
     * description: 教师查看自己的课表
     *
     * @param model   SpringMVC的Model
     * @param session JavaWeb的Session
     * @return 模版路径
     */
    @RequestMapping("course/look.do")
    public String look(Model model, HttpSession session) {
        AbstractCourse[][] courseArrayArray = new AbstractCourse[13][8];
        int[][] courseLengthArrayArray = new int[13][8];
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        List<SchoolElectiveCourse> schoolElectiveCourseList =
                schoolElectiveCourseService.getSchoolElectiveCourse(teacher);
        SchoolElectiveCourse[][] schoolElectiveCourseArrayArray =
                setCourseUtil.getSchoolElectiveCourse(schoolElectiveCourseList);
        setCourseUtil.setSchoolElectiveCourse(courseArrayArray, schoolElectiveCourseArrayArray);
        int[][] schoolElectiveCourseLengthArrayArray =
                setCourseLengthUtil.getSchoolElectiveCourseLength(schoolElectiveCourseArrayArray);
        setCourseLengthUtil.setSchoolElectiveCourseLength(courseLengthArrayArray,
                schoolElectiveCourseLengthArrayArray);

        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse(teacher);
        ProfessionalCourse[][] professionalCourseArrayArray =
                setCourseUtil.getProfessionalCourse(professionalCourseList);
        setCourseUtil.setProfessionalCourse(courseArrayArray, professionalCourseArrayArray);
        int[][] professionalCourseLengthArrayArray =
                setCourseLengthUtil.getProfessionalCourseLength(professionalCourseArrayArray);
        setCourseLengthUtil.setProfessionalCourseLength(courseLengthArrayArray,
                professionalCourseLengthArrayArray);

        List<PublicCourse> publicCourseList = publicCourseService.getPublicCourse(teacher);
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

        setSessionUtil.setCourseDay(session);

        return "teacher/course/look";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/professional/set.do")
    public String toProfessional(Model model, HttpSession session) {
        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse();
        model.addAttribute("courseList", professionalCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(professionalCourseList.toArray(),
                        CourseType.PROFESSIONAL_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(professionalCourseList.toArray(),
                        CourseType.PROFESSIONAL_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(professionalCourseList.toArray(),
                        CourseType.PROFESSIONAL_COURSE));

        Map<Integer, List<ProfessionalCourseClass>> courseClassMap =
                professionalCourseClassService.getCourseClass(professionalCourseList);
        session.setAttribute("courseClassMap", courseClassMap);
        session.setAttribute("courseClassStudentNumberMap",
                studentService.getCourseClassStudentNumber(professionalCourseList, courseClassMap
                        , null, null, CourseType.PROFESSIONAL_COURSE));

        setSessionUtil.setCourseType(session);
        setSessionUtil.setCourseSemester(session);
        setSessionUtil.setCourseDay(session);
        setSessionUtil.setTeacherRank(session);

        return "teacher/course/professional/select";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/professional/select.do")
    public String selectProfessional(int[] courseIdArray, Model model, HttpSession session) {
        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList",
                professionalCourseService.getProfessionalCourse(courseIdArray));
        return "teacher/course/professional/ensure";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/professional/ensure.do")
    public String saveProfessional(HttpSession session, Model model) {
        professionalCourseService.setProfessionalCourse((int[]) session.getAttribute(
                "courseIdArray"), (Teacher) session.getAttribute("teacher"));
        model.addAttribute("information", "您已完成该课程的选择");
        return "teacher/return";
    }

    /**
     * description: get all the public course, and return to
     * teacher/course/public/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/public/set.do")
    public String toPublic(Model model, HttpSession session) {
        List<PublicCourse> publicCourseList = publicCourseService.getPublicCourse();
        model.addAttribute("courseList", publicCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(publicCourseList.toArray(),
                        CourseType.PUBLIC_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(publicCourseList.toArray(),
                        CourseType.PUBLIC_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(publicCourseList.toArray(),
                        CourseType.PUBLIC_COURSE));

        Map<Integer, List<PublicCourseClass>> courseClassMap =
                publicCourseClassService.getCourseClass(publicCourseList);
        session.setAttribute("courseClassMap", courseClassMap);
        session.setAttribute("courseClassStudentNumberMap",
                studentService.getCourseClassStudentNumber(null, null, publicCourseList,
                        courseClassMap, CourseType.PUBLIC_COURSE));

        setSessionUtil.setCourseType(session);
        setSessionUtil.setCourseSemester(session);
        setSessionUtil.setCourseDay(session);
        setSessionUtil.setTeacherRank(session);

        return "teacher/course/public/select";
    }

    /**
     * description: get all the public course, and return to
     * teacher/course/public/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/public/select.do")
    public String selectPublic(int[] courseIdArray, Model model, HttpSession session) {
        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList", publicCourseService.getPublicCourse(courseIdArray));
        return "teacher/course/public/ensure";
    }

    /**
     * description: get all the public course, and return to
     * teacher/course/public/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/public/ensure.do")
    public String savePublic(HttpSession session, Model model) {
        publicCourseService.setPublicCourse((int[]) session.getAttribute("courseIdArray"),
                (Teacher) session.getAttribute("teacher"));
        model.addAttribute("information", "您已完成该课程的选择");
        return "teacher/return";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/set.do")
    public String toSchool(Model model, HttpSession session) {
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

        setSessionUtil.setCourseType(session);
        setSessionUtil.setCourseSemester(session);
        setSessionUtil.setCourseDay(session);
        setSessionUtil.setTeacherRank(session);

        return "teacher/course/elective/school/select";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/select.do")
    public String selectSchool(int[] courseIdArray, Model model, HttpSession session) {
        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList",
                schoolElectiveCourseService.getSchoolElectiveCourse(courseIdArray));
        return "teacher/course/elective/school/ensure";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/ensure.do")
    public String saveSchool(HttpSession session, Model model) {
        schoolElectiveCourseService.setSchoolElectiveCourse((int[]) session.getAttribute(
                "courseIdArray"), (Teacher) session.getAttribute("teacher"));
        model.addAttribute("information", "您已完成该课程的选择");
        return "teacher/return";
    }

    @RequestMapping({"score/enter/get.do", "score/look/get.do"})
    public String toCourse(HttpSession session, Model model, HttpServletRequest request) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse(teacher);
        Map<Integer, List<ProfessionalCourseClass>> professionalCourseClassMap =
                professionalCourseClassService.getCourseClass(professionalCourseList);
        Map<Integer, Integer> professionalCourseClassStudentNumberMap =
                studentService.getCourseClassStudentNumber(professionalCourseList,
                        professionalCourseClassMap, null, null, CourseType.PROFESSIONAL_COURSE);

        List<PublicCourse> publicCourseList = publicCourseService.getPublicCourse(teacher);
        Map<Integer, List<PublicCourseClass>> publicCourseClassMap =
                publicCourseClassService.getCourseClass(publicCourseList);
        Map<Integer, Integer> publicCourseClassStudentNumberMap =
                studentService.getCourseClassStudentNumber(null, null, publicCourseList,
                        publicCourseClassMap, CourseType.PUBLIC_COURSE);

        List<SchoolElectiveCourse> schoolElectiveCourseList =
                schoolElectiveCourseService.getSchoolElectiveCourse(teacher);
        Map<Integer, Integer> schoolElectiveCourseStudentNumberMap =
                schoolElectiveCourseStudentService.getCourseStudentNumber(schoolElectiveCourseList);

        model.addAttribute("professionalCourseList", professionalCourseList);
        model.addAttribute("professionalCourseClassStudentNumberMap",
                professionalCourseClassStudentNumberMap);

        model.addAttribute("publicCourseList", publicCourseList);
        model.addAttribute("publicCourseClassStudentNumberMap", publicCourseClassStudentNumberMap);

        model.addAttribute("schoolElectiveCourseList", schoolElectiveCourseList);
        model.addAttribute("schoolElectiveCourseStudentNumberMap",
                schoolElectiveCourseStudentNumberMap);

        session.setAttribute("professionalCourseDepartmentNameMap",
                departmentService.getDepartmentName(professionalCourseList.toArray(),
                        CourseType.PROFESSIONAL_COURSE));
        session.setAttribute("publicCourseDepartmentNameMap",
                departmentService.getDepartmentName(publicCourseList.toArray(),
                        CourseType.PUBLIC_COURSE));
        session.setAttribute("schoolElectiveCourseDepartmentNameMap",
                departmentService.getDepartmentName(schoolElectiveCourseList.toArray(),
                        CourseType.SCHOOL_ELECTIVE_COURSE));

        setSessionUtil.setCourseType(session);
        setSessionUtil.setCourseSemester(session);
        setSessionUtil.setTeacherRank(session);

        session.setAttribute("action",
                "/teacher/score/enter/get.do".equals(request.getServletPath()) ? "enter" : "look");

        return "teacher/score/1_select";
    }

    @RequestMapping({"score/enter/select.do", "score/look/select.do"})
    public String selectCourse(int courseId, HttpSession session, HttpServletRequest request) {
        ProfessionalCourse professionalCourse =
                professionalCourseService.getProfessionalCourse(courseId);
        if (professionalCourse != null) {
            session.setAttribute("courseType", CourseType.PROFESSIONAL_COURSE);
            session.setAttribute("course", professionalCourse);
        }
        PublicCourse publicCourse = publicCourseService.getPublicCourse(courseId);
        if (publicCourse != null) {
            session.setAttribute("courseType", CourseType.PUBLIC_COURSE);
            session.setAttribute("course", publicCourse);
        }
        SchoolElectiveCourse schoolElectiveCourse =
                schoolElectiveCourseService.getSchoolElectiveCourse(courseId);
        if (schoolElectiveCourse != null) {
            session.setAttribute("courseType", CourseType.SCHOOL_ELECTIVE_COURSE);
            session.setAttribute("course", schoolElectiveCourse);
        }

        switch (session.getAttribute("action").toString()) {
            case "enter":
                return "teacher/score/enter/2_ratio";
            case "look":
                return lookScore(session);
            default:
                return request.getServletPath();
        }
    }

    @RequestMapping("score/enter/ratio.do")
    public String setRatio(float rollRatio, float usualRatio, float experimentalRatio,
                           HttpSession session) {
        AbstractCourse course = null;
        CourseType courseType = (CourseType) session.getAttribute("courseType");
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                course = (ProfessionalCourse) session.getAttribute("course");
                course.setRollRatio(rollRatio);
                course.setUsualRatio(usualRatio);
                course.setExperimentalRatio(experimentalRatio);
                professionalCourseService.setProfessionalCourse((ProfessionalCourse) course);
                List<ProfessionalCourseClass> professionalCourseClassList =
                        professionalCourseClassService.getCourseClass((ProfessionalCourse) course);
                session.setAttribute("studentList",
                        studentService.getCourseClassStudent(professionalCourseClassList, null,
                                courseType));
                break;
            case PUBLIC_COURSE:
                course = (PublicCourse) session.getAttribute("course");
                course.setRollRatio(rollRatio);
                course.setUsualRatio(usualRatio);
                course.setExperimentalRatio(experimentalRatio);
                publicCourseService.setPublicCourse((PublicCourse) course);
                List<PublicCourseClass> publicCourseClassList =
                        publicCourseClassService.getCourseClass((PublicCourse) course);
                session.setAttribute("studentList", studentService.getCourseClassStudent(null,
                        publicCourseClassList, courseType));
                break;
            case SCHOOL_ELECTIVE_COURSE:
                course = (SchoolElectiveCourse) session.getAttribute("course");
                course.setRollRatio(rollRatio);
                course.setUsualRatio(usualRatio);
                course.setExperimentalRatio(experimentalRatio);
                schoolElectiveCourseService.setSchoolElectiveCourse((SchoolElectiveCourse) course);
                List<SchoolElectiveCourseStudent> schoolElectiveCourseStudentList =
                        schoolElectiveCourseStudentService.getCourseStudent((SchoolElectiveCourse) course);
                session.setAttribute("studentList",
                        studentService.getCourseStudent(schoolElectiveCourseStudentList));
                break;
            default:
                break;
        }
        return "teacher/score/enter/3_input";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("score/enter/input.do")
    public String setScore(HttpServletRequest request, HttpSession session) {
        List<Student> studentList = (List<Student>) session.getAttribute("studentList");
        Queue<String> rollScoreQueue = new LinkedList<>();
        Queue<String> usualScoreQueue = new LinkedList<>();
        Queue<String> experimentalScoreQueue = new LinkedList<>();
        for (Student student : studentList) {
            rollScoreQueue.offer(request.getParameter(student.getStudentId() + "RollScore"));
            usualScoreQueue.offer(request.getParameter(student.getStudentId() + "UsualScore"));
            experimentalScoreQueue.offer(request.getParameter(student.getStudentId() +
                    "ExperimentalScore"));
        }
        List<Score> scoreList = scoreService.getScore((AbstractCourse) session.getAttribute(
                "course"), studentList, rollScoreQueue, usualScoreQueue, experimentalScoreQueue);
        session.setAttribute("scoreList", scoreList);
        return "teacher/score/enter/4_ensure";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("score/enter/ensure.do")
    public String saveScore(HttpSession session, Model model) {
        scoreService.setScore((List<Score>) session.getAttribute("scoreList"));
        model.addAttribute("information", "您已完成成绩录入");
        return "teacher/return";
    }

    @RequestMapping("score/look/look.do")
    public String lookScore(HttpSession session) {
        AbstractCourse course = null;
        List<Student> studentList = null;
        CourseType courseType = (CourseType) session.getAttribute("courseType");
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                course = (ProfessionalCourse) session.getAttribute("course");
                List<ProfessionalCourseClass> professionalCourseClassList =
                        professionalCourseClassService.getCourseClass((ProfessionalCourse) course);
                studentList = studentService.getCourseClassStudent(professionalCourseClassList,
                        null, courseType);
                break;
            case PUBLIC_COURSE:
                course = (PublicCourse) session.getAttribute("course");
                List<PublicCourseClass> publicCourseClassList =
                        publicCourseClassService.getCourseClass((PublicCourse) course);
                studentList = studentService.getCourseClassStudent(null, publicCourseClassList,
                        courseType);
                break;
            case SCHOOL_ELECTIVE_COURSE:
                course = (SchoolElectiveCourse) session.getAttribute("course");
                List<SchoolElectiveCourseStudent> schoolElectiveCourseStudentList =
                        schoolElectiveCourseStudentService.getCourseStudent((SchoolElectiveCourse) course);
                studentList = studentService.getCourseStudent(schoolElectiveCourseStudentList);
                break;
            default:
                break;
        }
        session.setAttribute("studentList", studentList);
        assert studentList != null;
        session.setAttribute("scoreList", scoreService.getScore(course, studentList));
        for (Score score : scoreService.getScore(course, studentList)) {
            System.out.println(score.getScoreId());
        }
        return "teacher/score/look/2_look";
    }

}
