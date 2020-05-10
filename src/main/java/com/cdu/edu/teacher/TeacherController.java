package com.cdu.edu.teacher;

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
import com.cdu.edu.score.GetScoreUtil;
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
    private ElectiveCourseService electiveCourseService;

    @Autowired
    private ElectiveCourseStudentService electiveCourseStudentService;

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

    @Autowired
    private GetScoreUtil getScoreUtil;


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

        return "teacher/course/professional/1_select";
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
        if (courseIdArray == null || courseIdArray.length == 0) {
            model.addAttribute("information", "您并未选择课程");
            return "error";
        }

        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList",
                professionalCourseService.getProfessionalCourse(courseIdArray));
        return "teacher/course/professional/2_ensure";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/professional/ensure.do")
    public String saveProfessional(Model model, HttpSession session) {
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

        return "teacher/course/public/1_select";
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
        return "teacher/course/public/2_ensure";
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
    @RequestMapping("course/elective/set.do")
    public String toSchool(Model model, HttpSession session) {
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

        setSessionUtil.setCourseType(session);
        setSessionUtil.setCourseSemester(session);
        setSessionUtil.setCourseDay(session);
        setSessionUtil.setTeacherRank(session);

        return "teacher/course/elective/1_select";
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
        model.addAttribute("courseList",
                electiveCourseService.getElectiveCourse(courseIdArray));
        return "teacher/course/elective/2_ensure";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/ensure.do")
    public String saveSchool(Model model, HttpSession session) {
        electiveCourseService.setSchoolElectiveCourse((int[]) session.getAttribute("courseIdArray"
        ), (Teacher) session.getAttribute("teacher"));
        model.addAttribute("information", "您已完成该课程的选择");
        return "teacher/return";
    }

    /**
     * description: get all the professional course, and return to
     * teacher/course/professional/1_select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/timetable/get.do")
    public String toTimetable(Model model, HttpSession session) {
        setSessionUtil.setCourseSemester(session);
        return "teacher/course/timetable/1_select";
    }

    /**
     * description: 教师查看自己的课表
     *
     * @param model   SpringMVC的Model
     * @param session JavaWeb的Session
     * @return 模版路径
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
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        List<ElectiveCourse> electiveCourseList =
                electiveCourseService.getElectiveCourse(teacher);
        electiveCourseList.removeIf(course -> !course.getCourseYear().equals(year) || course.getCourseSemester() != semester);
        ElectiveCourse[][] electiveCourseArrayArray =
                setCourseUtil.getElectiveCourse(electiveCourseList);
        setCourseUtil.setElectiveCourse(courseArrayArray, electiveCourseArrayArray);
        int[][] schoolElectiveCourseLengthArrayArray =
                setCourseLengthUtil.getElectiveCourseLength(electiveCourseArrayArray);
        setCourseLengthUtil.setElectiveCourseLength(courseLengthArrayArray,
                schoolElectiveCourseLengthArrayArray);

        List<ProfessionalCourse> professionalCourseList =
                professionalCourseService.getProfessionalCourse(teacher);
        professionalCourseList.removeIf(course -> !course.getCourseYear().equals(year) || course.getCourseSemester() != semester);
        ProfessionalCourse[][] professionalCourseArrayArray =
                setCourseUtil.getProfessionalCourse(professionalCourseList);
        setCourseUtil.setProfessionalCourse(courseArrayArray, professionalCourseArrayArray);
        int[][] professionalCourseLengthArrayArray =
                setCourseLengthUtil.getProfessionalCourseLength(professionalCourseArrayArray);
        setCourseLengthUtil.setProfessionalCourseLength(courseLengthArrayArray,
                professionalCourseLengthArrayArray);

        List<PublicCourse> publicCourseList = publicCourseService.getPublicCourse(teacher);
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
        setSessionUtil.setCourseDay(session);
        return "teacher/course/timetable/2_look";
    }

    @RequestMapping({"score/enter/get.do", "score/show/get.do"})
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

        List<ElectiveCourse> electiveCourseList =
                electiveCourseService.getElectiveCourse(teacher);
        Map<Integer, Integer> electiveCourseStudentNumberMap =
                electiveCourseStudentService.getCourseStudentNumber(electiveCourseList);

        model.addAttribute("professionalCourseList", professionalCourseList);
        model.addAttribute("professionalCourseClassStudentNumberMap",
                professionalCourseClassStudentNumberMap);

        model.addAttribute("publicCourseList", publicCourseList);
        model.addAttribute("publicCourseClassStudentNumberMap", publicCourseClassStudentNumberMap);

        model.addAttribute("electiveCourseList", electiveCourseList);
        model.addAttribute("electiveCourseStudentNumberMap",
                electiveCourseStudentNumberMap);

        session.setAttribute("professionalCourseDepartmentNameMap",
                departmentService.getDepartmentName(professionalCourseList.toArray(),
                        CourseType.PROFESSIONAL_COURSE));
        session.setAttribute("publicCourseDepartmentNameMap",
                departmentService.getDepartmentName(publicCourseList.toArray(),
                        CourseType.PUBLIC_COURSE));
        session.setAttribute("schoolElectiveCourseDepartmentNameMap",
                departmentService.getDepartmentName(electiveCourseList.toArray(),
                        CourseType.ELECTIVE_COURSE));

        setSessionUtil.setCourseType(session);
        setSessionUtil.setCourseSemester(session);
        setSessionUtil.setTeacherRank(session);

        session.setAttribute("action",
                "/teacher/score/enter/get.do".equals(request.getServletPath()) ? "enter" : "look");

        return "teacher/score/1_select";
    }

    @RequestMapping({"score/enter/select.do", "score/show/select.do"})
    public String selectCourse(Integer courseId, Model model, HttpSession session) {
        if (courseId == null) {
            model.addAttribute("information", "您并未选择课程");
            return "error";
        }

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
        ElectiveCourse electiveCourse = electiveCourseService.getElectiveCourse(courseId);
        if (electiveCourse != null) {
            session.setAttribute("courseType", CourseType.ELECTIVE_COURSE);
            session.setAttribute("course", electiveCourse);
        }

        return "enter".equals(session.getAttribute("action").toString()) ?
                "teacher/score/enter" + "/2_ratio" : lookScore(session);
    }

    @RequestMapping("score/enter/ratio.do")
    public String setRatio(String rollRatio, String usualRatio, String experimentalRatio,
                           Model model, HttpSession session) {
        if (rollRatio == null || "".equals(rollRatio)) {
            model.addAttribute("information", "您并未输入卷面成绩");
            return "error";
        } else if (!rollRatio.matches("^[0-9]+(.[0-9]+)?$")) {
            model.addAttribute("information", "您只能在卷面成绩比例输入小数");
            return "error";
        } else if (Float.parseFloat(rollRatio) <= 0 && Float.parseFloat(rollRatio) >= 1) {
            model.addAttribute("information", "您输入的卷面成绩比例只能在0与1之间");
            return "error";
        } else if (usualRatio == null || "".equals(usualRatio)) {
            model.addAttribute("information", "您并未输入平时成绩");
            return "error";
        } else if (!usualRatio.matches("^[0-9]+(.[0-9]+)?$")) {
            model.addAttribute("information", "您只能在平时成绩比例输入小数");
            return "error";
        } else if (Float.parseFloat(usualRatio) < 0 || Float.parseFloat(usualRatio) > 1) {
            model.addAttribute("information", "您输入的平时成绩比例只能在0与1之间");
            return "error";
        } else if (experimentalRatio == null || "".equals(experimentalRatio)) {
            model.addAttribute("information", "您并未输入实验成绩比例");
            return "error";
        } else if (!experimentalRatio.matches("^[0-9]+(.[0-9]+)?$")) {
            model.addAttribute("information", "您只能在实验成绩比例输入小数");
            return "error";
        } else if (Float.parseFloat(experimentalRatio) <= 0 && Float.parseFloat(experimentalRatio) >= 1) {
            model.addAttribute("information", "您输入的实验成绩比例只能在0与1之间");
            return "error";
        }

        AbstractCourse course = null;
        CourseType courseType = (CourseType) session.getAttribute("courseType");
        switch (courseType) {
            case PROFESSIONAL_COURSE:
                course = (ProfessionalCourse) session.getAttribute("course");
                course.setRollRatio(Float.parseFloat(rollRatio));
                course.setUsualRatio(Float.parseFloat(usualRatio));
                course.setExperimentalRatio(Float.parseFloat(experimentalRatio));
                professionalCourseService.setProfessionalCourse((ProfessionalCourse) course);
                List<ProfessionalCourseClass> professionalCourseClassList =
                        professionalCourseClassService.getCourseClass((ProfessionalCourse) course);
                session.setAttribute("studentList",
                        studentService.getCourseClassStudent(professionalCourseClassList, null,
                                courseType));
                break;
            case PUBLIC_COURSE:
                course = (PublicCourse) session.getAttribute("course");
                course.setRollRatio(Float.parseFloat(rollRatio));
                course.setUsualRatio(Float.parseFloat(usualRatio));
                course.setExperimentalRatio(Float.parseFloat(experimentalRatio));
                publicCourseService.setPublicCourse((PublicCourse) course);
                List<PublicCourseClass> publicCourseClassList =
                        publicCourseClassService.getCourseClass((PublicCourse) course);
                session.setAttribute("studentList", studentService.getCourseClassStudent(null,
                        publicCourseClassList, courseType));
                break;
            case ELECTIVE_COURSE:
                course = (ElectiveCourse) session.getAttribute("course");
                course.setRollRatio(Float.parseFloat(rollRatio));
                course.setUsualRatio(Float.parseFloat(usualRatio));
                course.setExperimentalRatio(Float.parseFloat(experimentalRatio));
                electiveCourseService.setSchoolElectiveCourse((ElectiveCourse) course);
                List<ElectiveCourseStudent> electiveCourseStudentList =
                        electiveCourseStudentService.getCourseStudent((ElectiveCourse) course);
                session.setAttribute("studentList",
                        studentService.getCourseStudent(electiveCourseStudentList));
                break;
            default:
                break;
        }
        return "teacher/score/enter/3_input";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("score/enter/input.do")
    public String setScore(Model model, HttpSession session, HttpServletRequest request) {
        List<Student> studentList = (List<Student>) session.getAttribute("studentList");
        Queue<Double> rollScoreQueue = new LinkedList<>();
        Queue<Double> usualScoreQueue = new LinkedList<>();
        Queue<Double> experimentalScoreQueue = new LinkedList<>();

        for (Student student : studentList) {
            String rollScore = request.getParameter(student.getStudentId() + "RollScore");
            if (rollScore == null || "".equals(rollScore)) {
                model.addAttribute("information", "您并未为每个学生输入卷面成绩");
                return "error";
            } else if (!rollScore.matches("^[0-9]+(.[0-9]+)?$")) {
                model.addAttribute("information", "您输入的卷面成绩必须为小数");
                return "error";
            } else if (Double.parseDouble(rollScore) < 0 || Double.parseDouble(rollScore) > 100) {
                model.addAttribute("information", "您输入的卷面成绩必须在0与100之间");
                return "error";
            }
            String usualScore = request.getParameter(student.getStudentId() + "UsualScore");
            if (usualScore == null || "".equals(usualScore)) {
                model.addAttribute("information", "您并未为每个学生输入平时成绩");
                return "error";
            } else if (!usualScore.matches("^[0-9]+(.[0-9]+)?$")) {
                model.addAttribute("information", "您输入的平时成绩必须为小数");
                return "error";
            } else if (Double.parseDouble(usualScore) < 0 || Double.parseDouble(usualScore) > 100) {
                model.addAttribute("information", "您输入的平时成绩必须在0与100之间");
                return "error";
            }
            String experimentalScore = request.getParameter(student.getStudentId() +
                    "ExperimentalScore");
            if (experimentalScore == null || "".equals(experimentalScore)) {
                model.addAttribute("information", "您并未为每个学生输入实验成绩");
                return "error";
            } else if (!experimentalScore.matches("^[0-9]+(.[0-9]+)?$")) {
                model.addAttribute("information", "您输入的实验成绩必须为小数");
                return "error";
            } else if (Double.parseDouble(experimentalScore) < 0 || Double.parseDouble(experimentalScore) > 100) {
                model.addAttribute("information", "您输入的实验成绩必须在0与100之间");
                return "error";
            }
            rollScoreQueue.offer(Double.parseDouble(rollScore));
            usualScoreQueue.offer(Double.parseDouble(usualScore));
            experimentalScoreQueue.offer(Double.parseDouble(experimentalScore));
        }

        List<Score> scoreList = getScoreUtil.getScore((AbstractCourse) session.getAttribute(
                "course"), studentList, rollScoreQueue, usualScoreQueue, experimentalScoreQueue);
        session.setAttribute("scoreList", scoreList);
        return "teacher/score/enter/4_ensure";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("score/enter/ensure.do")
    public String saveScore(Model model, HttpSession session) {
        scoreService.setScore((List<Score>) session.getAttribute("scoreList"));
        model.addAttribute("information", "您已完成成绩录入");
        return "teacher/return";
    }

    @RequestMapping("score/show/look.do")
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
            case ELECTIVE_COURSE:
                course = (ElectiveCourse) session.getAttribute("course");
                List<ElectiveCourseStudent> electiveCourseStudentList =
                        electiveCourseStudentService.getCourseStudent((ElectiveCourse) course);
                studentList = studentService.getCourseStudent(electiveCourseStudentList);
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
        return "teacher/score/show/2_look";
    }

}
