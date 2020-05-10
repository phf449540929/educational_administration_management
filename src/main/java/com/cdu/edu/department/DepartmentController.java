package com.cdu.edu.department;

import com.cdu.edu.SetSessionUtil;
import com.cdu.edu.course.*;
import com.cdu.edu.course.elective.ElectiveCourse;
import com.cdu.edu.course.elective.ElectiveCourseService;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.professional.ProfessionalCourseService;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClassService;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.publics.PublicCourseService;
import com.cdu.edu.course.publics.classes.PublicCourseClassService;
import com.cdu.edu.index.Identity;
import com.cdu.edu.index.LoginForm;
import com.cdu.edu.student.Student;
import com.cdu.edu.student.StudentService;
import com.cdu.edu.teacher.Teacher;
import com.cdu.edu.teacher.TeacherRank;
import com.cdu.edu.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;

/**
 * description: 部门类的Controller层
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/17 0017 上午 9:33
 * @since jdk 10.0.1
 */
@Controller
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ElectiveCourseService electiveCourseService;

    @Autowired
    private ProfessionalCourseService professionalCourseService;

    @Autowired
    private ProfessionalCourseClassService professionalCourseClassService;

    @Autowired
    private PublicCourseService publicCourseService;

    @Autowired
    private PublicCourseClassService publicCourseClassService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SetSessionUtil setSessionUtil;

    /**
     * description: 部门登录
     *
     * @param username 部门的用户名
     * @param password 部门的密码
     * @return java.lang.String 模版路径
     */
    @RequestMapping("login.do")
    public String login(String username, String password, Model model, HttpSession session) {
        Department department = departmentService.login(username, password);
        if (department != null) {
            session.setAttribute("department", department);
            return "department/index";
        } else {
            LoginForm loginForm = new LoginForm();
            loginForm.setUsername(username);
            loginForm.setIdentity(Identity.DEPARTMENT);
            loginForm.setMessage("您的用户名或密码错误");
            model.addAttribute("loginForm", loginForm);
            return "index";
        }
    }

    /**
     * description: 返回部门个人主页
     *
     * @return java.lang.String 模版路径
     */
    @RequestMapping("return.do")
    public String returnIndex() {
        return "department/index";
    }

    /**
     * description: 保存部门实体
     *
     * @return com.cdu.edu.department.Department 被保存的部门实体
     */
    @RequestMapping("insert.do")
    @ResponseBody
    public Department insert() {
        Department department = new Department();
        department.setDepartmentId("000000000002");
        department.setDepartmentPassword("123456");
        department.setDepartmentName("马克思主义学院");
        departmentService.insert(department);
        return department;
    }

    /**
     * description: 进入设置教师页面
     *
     * @param session Session组件
     * @return java.lang.String 模版路径
     */
    @RequestMapping("teacher/insert/set.do")
    public String setTeacher(HttpSession session) {
        setSessionUtil.setTeacherRank(session);
        setSessionUtil.setGender(session);
        return "department/teacher/insert/1_essential";
    }

    /**
     * description: 设置教师的教职工号、密码、姓名、性别、职称
     *
     * @param teacherId       教师教职工号
     * @param teacherPassword 教师密码
     * @param teacherName     教师姓名
     * @param teacherGender   教师性别
     * @param teacherRank     教师职称
     * @param model           Model组件
     * @param session         Session组件
     * @param request         Request组件
     * @return java.lang.String 模版路径
     */
    @RequestMapping({"teacher/insert/essential.do", "teacher/update/essential.do"})
    public String setTeacherEssential(String teacherId, String teacherPassword,
                                      String teacherName, Gender teacherGender,
                                      TeacherRank teacherRank, Model model, HttpSession session,
                                      HttpServletRequest request) {
        if (teacherId == null || "".equals(teacherId)) {
            model.addAttribute("information", "您并未输入教师教职工号");
            return "error";
        } else if (teacherId.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的教师教职工号只能为数字");
            return "error";
        } else if (teacherPassword == null || "".equals(teacherPassword)) {
            model.addAttribute("information", "您并未输入教师密码");
            return "error";
        } else if (teacherGender == null) {
            model.addAttribute("information", "您并未选择教师性别");
            return "error";
        } else if (teacherRank == null) {
            model.addAttribute("information", "您并未选择教师职称");
            return "error";
        }
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        teacher = teacher == null ? new Teacher() : teacher;
        teacher.setTeacherId(teacherId);
        teacher.setTeacherPassword(teacherPassword);
        teacher.setTeacherName(teacherName);
        teacher.setTeacherGender(teacherGender == Gender.MAN ? "男" : "女");
        teacher.setTeacherRank(teacherRank);
        session.setAttribute("teacher", teacher);

        model.addAttribute("departmentList", departmentService.getDepartment());
        switch (request.getServletPath().split("/")[3]) {
            case "insert":
                return "department/teacher/insert/2_department";
            case "update":
                return "department/teacher/update/2_department";
            default:
                return "error";
        }
    }

    /**
     * description: 设置教师所属部门
     *
     * @param departmentId 部门Id
     * @param model        Model组件
     * @param session      Session组件
     * @param request      Request组件
     * @return java.lang.String 模版路径
     */
    @RequestMapping({"teacher/insert/department.do", "teacher/update/department.do"})
    public String setTeacherDepartment(String departmentId, Model model, HttpSession session,
                                       HttpServletRequest request) {
        if (departmentId == null || "".equals(departmentId)) {
            model.addAttribute("information", "您并未输入教师教职工号");
            return "error";
        }
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        teacher.setTeacherDepartment(departmentId);
        session.setAttribute("teacher", teacher);
        session.setAttribute("teacherDepartmentName",
                departmentService.getDepartmentName(departmentId));
        switch (request.getServletPath().split("/")[3]) {
            case "insert":
                return "department/teacher/insert/3_ensure";
            case "update":
                return "department/teacher/update/3_ensure";
            default:
                return "error";
        }
    }

    /**
     * description: 部门确认设置教师
     *
     * @param model   Model组件
     * @param session Session组件
     * @param request Request组件
     * @return java.lang.String 模版路径
     */
    @RequestMapping({"teacher/insert/ensure.do", "teacher/update/ensure.do"})
    public String saveTeacher(Model model, HttpSession session, HttpServletRequest request) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        teacherService.insert(teacher);
        switch (request.getServletPath().split("/")[3]) {
            case "insert":
                model.addAttribute("information", "您已完成该教师的设置");
                break;
            case "update":
                model.addAttribute("information", "您已完成该教师的修改");
                break;
            default:
                return "error";
        }
        return "department/return";
    }

    /**
     * description: 部门获取教师列表
     *
     * @param departmentId 部门Id
     * @param model        Model组件
     * @param session      Session组件
     * @return java.lang.String 模版路径
     */
    @RequestMapping("teacher/get.do")
    public String getTeacher(String departmentId, Model model, HttpSession session) {
        List<Teacher> teacherList = teacherService.getTeacher();
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("departmentNameList", departmentService.getDepartmentName(teacherList,
                null, Identity.TEACHER));
        setSessionUtil.setTeacherRank(session);
        return "department/teacher/select";
    }

    /**
     * description: 部门选择教师
     *
     * @param teacherId 教师教职工号
     * @param model     Model组件
     * @param session   Session组件
     * @param request   Request组件
     * @return java.lang.String 模版路径
     */
    @RequestMapping({"teacher/update/select.do", "teacher/delete/select.do"})
    public String selectTeacher(String teacherId, Model model, HttpSession session,
                                HttpServletRequest request) {
        if (teacherId == null || "".equals(teacherId)) {
            model.addAttribute("information", "您并未选择教师");
            return "error";
        }

        Teacher teacher = teacherService.getTeacher(teacherId);
        session.setAttribute("teacher", teacher);
        model.addAttribute("teacherDepartmentName",
                departmentService.getDepartmentName(teacher.getTeacherDepartment()));

        setSessionUtil.setTeacherRank(session);
        switch (request.getServletPath().split("/")[3]) {
            case "update":
                setSessionUtil.setGender(session);
                return "department/teacher/update/1_essential";
            case "delete":
                model.addAttribute("teacherDepartmentName",
                        departmentService.getDepartmentName(teacher.getTeacherDepartment()));
                return "department/teacher/delete/ensure";
            default:
                return "error";
        }
    }

    @RequestMapping("teacher/delete/ensure.do")
    public String deleteTeacher(Model model, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        teacherService.delete(teacher);
        model.addAttribute("information", "您已成功删除该教师");
        return "department/return";
    }

    @RequestMapping("student/insert/set.do")
    public String setStudent(HttpSession session) {
        setSessionUtil.setGender(session);
        return "department/student/insert/1_essential";
    }

    @RequestMapping({"student/insert/essential.do", "student/update/essential.do"})
    public String setStudentEssential(String studentId, String studentPassword,
                                      String studentName, Gender studentGender,
                                      String studentGrade, Model model, HttpSession session,
                                      HttpServletRequest request) {
        if (studentId == null || "".equals(studentId)) {
            model.addAttribute("information", "您并未输入学生学号");
            return "error";
        } else if (studentId.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的学生学号只能为数字");
            return "error";
        } else if (studentPassword == null || "".equals(studentPassword)) {
            model.addAttribute("information", "您并未输入学生密码");
            return "error";
        } else if (studentGender == null) {
            model.addAttribute("information", "您并未选择学生性别");
            return "error";
        } else if (studentGrade == null || "".equals(studentGrade)) {
            model.addAttribute("information", "您并未输入学生年级");
            return "error";
        } else if (studentGrade.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的学生年级只能为数字");
            return "error";
        }

        Student student = (Student) session.getAttribute("student");
        student = student == null ? new Student() : student;
        student.setStudentId(studentId);
        student.setStudentPassword(studentPassword);
        student.setStudentName(studentName);
        student.setStudentGender(studentGender == Gender.MAN ? "男" : "女");
        student.setStudentGrade(studentGrade);
        session.setAttribute("student", student);

        model.addAttribute("departmentList", departmentService.getDepartment());
        switch (request.getServletPath().split("/")[3]) {
            case "insert":
                return "department/student/insert/2_organization";
            case "update":
                return "department/student/update/2_organization";
            default:
                return "error";
        }
    }

    @RequestMapping({"student/insert/organization.do", "student/update/organization.do"})
    public String setStudentDepartment(String departmentId, String studentMajor,
                                       String studentClass, Model model, HttpSession session,
                                       HttpServletRequest request) {
        if (departmentId == null || "".equals(departmentId)) {
            model.addAttribute("information", "您并未选择学生所属学院");
            return "error";
        } else if (studentMajor == null || "".equals(studentMajor)) {
            model.addAttribute("information", "您并未输入学生专业");
            return "error";
        } else if (studentClass == null || "".equals(studentClass)) {
            model.addAttribute("information", "您并未输入学生班级");
            return "error";
        }

        Student student = (Student) session.getAttribute("student");
        student.setStudentAcademy(departmentId);
        student.setStudentMajor(studentMajor);
        student.setStudentClass(studentClass);
        session.setAttribute("student", student);
        session.setAttribute("studentAcademyName",
                departmentService.getDepartmentName(departmentId));
        switch (request.getServletPath().split("/")[3]) {
            case "insert":
                return "department/student/insert/3_ensure";
            case "update":
                return "department/student/update/3_ensure";
            default:
                return "error";
        }
    }

    @RequestMapping({"student/insert/ensure.do", "student/update/ensure.do"})
    public String saveStudent(Model model, HttpSession session, HttpServletRequest request) {
        Student student = (Student) session.getAttribute("student");
        studentService.insert(student);
        switch (request.getServletPath().split("/")[3]) {
            case "insert":
                model.addAttribute("information", "您已完成该学生的设置");
                break;
            case "update":
                model.addAttribute("information", "您已完成该学生的修改");
                break;
            default:
                return "error";
        }
        return "department/return";
    }

    @RequestMapping("student/get.do")
    public String getStudent(Model model, HttpSession session) {
        List<Student> studentList = studentService.getStudent();
        model.addAttribute("studentList", studentList);
        model.addAttribute("departmentNameList", departmentService.getDepartmentName(null,
                studentList, Identity.STUDENT));
        setSessionUtil.setTeacherRank(session);
        return "department/student/select";
    }

    @RequestMapping({"student/update/select.do", "student/delete/select.do"})
    public String selectStudent(String studentId, Model model, HttpSession session,
                                HttpServletRequest request) {
        if (studentId == null || "".equals(studentId)) {
            model.addAttribute("information", "您并未选择学生");
            return "error";
        }

        Student student = studentService.getStudent(studentId);
        session.setAttribute("student", student);

        switch (request.getServletPath().split("/")[3]) {
            case "update":
                setSessionUtil.setGender(session);
                return "department/student/update/1_essential";
            case "delete":
                model.addAttribute("studentAcademyName",
                        departmentService.getDepartmentName(student.getStudentAcademy()));
                return "department/student/delete/ensure";
            default:
                return "error";
        }
    }

    @RequestMapping("student/delete/ensure.do")
    public String deleteStudent(Model model, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        studentService.delete(student);
        model.addAttribute("information", "您已成功删除该学生");
        return "department/return";
    }

    /**
     * description: get all the classes, later find the department/course/professional/1_class.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/set.do")
    public String toProfessional(Model model) {
        model.addAttribute("classSet", studentService.getAllClass());
        return "department/course/professional/1_class";
    }

    /**
     * description: set professional's class, and find the department/course/professional/2_year
     * .html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/class.do")
    public String setProfessionalClass(String[] classArray, Model model, HttpSession session) {
        if (classArray == null || classArray.length < 1) {
            model.addAttribute("information", "您并未选择上课班级");
            return "error";
        }

        session.setAttribute("classArray", classArray);
        return "department/course/professional/2_year";
    }

    /**
     * description: set professional's year, and find the
     * department/course/professional/3_semester.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/year.do")
    public String setProfessionalYear(String year, Model model, HttpSession session) {
        int systemYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year == null || "".equals(year)) {
            model.addAttribute("information", "您并未输入上课年份");
            return "error";
        } else if (!year.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的上课年份只能为数字");
            return "error";
        } else if (Integer.parseInt(year) < systemYear) {
            model.addAttribute("information", "您输入的时间必须大于等于系统年份：" + systemYear);
            return "error";
        }

        session.setAttribute("year", year);
        setSessionUtil.setCourseSemester(session);
        return "department/course/professional/3_semester";
    }

    /**
     * description: set professional's semester, and find the
     * department/course/professional/4_name.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/semester.do")
    public String setProfessionalSemester(CourseSemester semester, Model model,
                                          HttpSession session) {
        if (semester == null) {
            model.addAttribute("information", "您并未选择开课学期");
            return "error";
        }

        session.setAttribute("semester", semester);
        return "department/course/professional/4_name";
    }

    /**
     * description: set professional's name, and find the department/course/professional/5_rank.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/name.do")
    public String setProfessionalName(String name, Model model, HttpSession session) {
        if (name == null || "".equals(name)) {
            model.addAttribute("information", "您并未输入课程名称");
            return "error";
        }

        session.setAttribute("name", name);
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/professional/6_week";
    }

    /**
     * description: set professional's week, and find the department/course/professional/7_day.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/week.do")
    public String setProfessionalWeek(CourseWeek beginWeek, CourseWeek endWeek, Model model,
                                      HttpSession session) {
        if (beginWeek == null) {
            model.addAttribute("information", "您并未选择课程开始周数");
            return "error";
        } else if (endWeek == null) {
            model.addAttribute("information", "您并未选择课程结束周数");
            return "error";
        }

        session.setAttribute("beginWeek", beginWeek);
        session.setAttribute("endWeek", endWeek);
        setSessionUtil.setCourseDay(session);
        return "department/course/professional/7_day";
    }

    /**
     * description: set professional's day, and find the department/course/professional/8_order.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/day.do")
    public String setProfessionalDay(CourseDay day, Model model, HttpSession session) {
        if (day == null) {
            model.addAttribute("information", "您并未选择课程上课日期");
            return "error";
        }

        session.setAttribute("day", day);
        model.addAttribute("orderArray", CourseOrder.values());
        return "department/course/professional/8_order";
    }

    /**
     * description: set professional's order, and find the
     * department/course/professional/9_classroom.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/order.do")
    public String setProfessionalOrder(CourseOrder beginOrder, CourseOrder endOrder, Model model,
                                       HttpSession session) {
        if (beginOrder == null) {
            model.addAttribute("information", "您并未选择课程开始时间");
            return "error";
        } else if (endOrder == null) {
            model.addAttribute("information", "您并未选择课程结束时间");
            return "error";
        }

        session.setAttribute("beginOrder", beginOrder);
        session.setAttribute("endOrder", endOrder);
        model.addAttribute("classrooms", CourseClassroom.values());
        return "department/course/professional/9_classroom";
    }

    /**
     * description: set professional's classroom, and find the
     * department/course/professional/10_ensure.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/classroom.do")
    public String setProfessionalClassroom(CourseClassroom classroom, Model model,
                                           HttpSession session) {
        if (classroom == null) {
            model.addAttribute("information", "您并未选择上课教室");
            return "error";
        }

        session.setAttribute("classroom", classroom);
        session.setAttribute("type", CourseType.PROFESSIONAL_COURSE);
        String[] classArray = ((String[]) session.getAttribute("classArray"));
        model.addAttribute("classNumber", classArray.length);
        model.addAttribute("studentNumber", studentService.getCourseClassStudentNumber(classArray));
        setSessionUtil.setCourseType(session);
        return "department/course/professional/10_ensure";
    }

    /**
     * description: save setProfessionalCourse the professional course, and find the
     * department/course/return.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/ensure.do")
    public String saveProfessional(Model model, HttpSession session) {
        ProfessionalCourse professionalCourse = new ProfessionalCourse();
        professionalCourse.setAcademyId(((Department) session.getAttribute("department")).getDepartmentId());
        professionalCourse.setCourseYear((String) session.getAttribute("year"));
        professionalCourse.setCourseSemester((CourseSemester) session.getAttribute("semester"));
        professionalCourse.setCourseType((CourseType) session.getAttribute("type"));
        professionalCourse.setCourseBeginWeek((CourseWeek) session.getAttribute("beginWeek"));
        professionalCourse.setCourseEndWeek((CourseWeek) session.getAttribute("endWeek"));
        professionalCourse.setCourseDay((CourseDay) session.getAttribute("day"));
        professionalCourse.setCourseBeginOrder((CourseOrder) session.getAttribute("beginOrder"));
        professionalCourse.setCourseEndOrder((CourseOrder) session.getAttribute("endOrder"));
        professionalCourse.setCourseName((String) session.getAttribute("name"));
        professionalCourse.setCourseClassroom((CourseClassroom) session.getAttribute("classroom"));
        professionalCourse.setTeacherNeedRank((TeacherRank) session.getAttribute("rank"));

        int courseId =
                professionalCourseService.setProfessionalCourse(professionalCourse).getCourseId();
        professionalCourseClassService.setCourseClass(courseId, (String[]) session.getAttribute(
                "classArray"));
        model.addAttribute("information", "您已完成该专业课的设置");
        return "department/return";
    }

    /**
     * description: get all the classes, later find the department/course/public/1_class.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/set.do")
    public String toPublic(Model model) {
        model.addAttribute("classSet", studentService.getAllClass());
        return "department/course/public/1_class";
    }

    /**
     * description: set public's class, and find the department/course/public/2_year.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/class.do")
    public String setPublicClass(String[] classArray, Model model, HttpSession session) {
        if (classArray == null || classArray.length < 1) {
            model.addAttribute("information", "您并未选择上课班级");
            return "error";
        }

        session.setAttribute("classArray", classArray);
        return "department/course/public/2_year";
    }

    /**
     * description: set public's year, and find the department/course/public/3_semester.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/year.do")
    public String setPublicYear(String year, Model model, HttpSession session) {
        int systemYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year == null || "".equals(year)) {
            model.addAttribute("information", "您并未输入上课年份");
            return "error";
        } else if (!year.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的上课年份只能为数字");
            return "error";
        } else if (Integer.parseInt(year) < systemYear) {
            model.addAttribute("information", "您输入的时间必须大于等于系统年份：" + systemYear);
            return "error";
        }

        session.setAttribute("year", year);
        setSessionUtil.setCourseSemester(session);
        return "department/course/public/3_semester";
    }

    /**
     * description: set public's semester, and find the department/course/public/4_name.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/semester.do")
    public String setPublicSemester(CourseSemester semester, Model model, HttpSession session) {
        if (semester == null) {
            model.addAttribute("information", "您并未选择开课学期");
            return "error";
        }

        session.setAttribute("semester", semester);
        return "department/course/public/4_name";
    }

    /**
     * description: set public's name, and find the department/course/public/5_rank.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/name.do")
    public String setPublicName(String name, Model model, HttpSession session) {
        if (name == null || "".equals(name)) {
            model.addAttribute("information", "您并未输入课程名称");
            return "error";
        }

        session.setAttribute("name", name);
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/public/6_week";
    }

    /**
     * description: set public's week, and find the department/course/public/7_day.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/week.do")
    public String setPublicWeek(CourseWeek beginWeek, CourseWeek endWeek, Model model,
                                HttpSession session) {
        if (beginWeek == null) {
            model.addAttribute("information", "您并未选择课程开始周数");
            return "error";
        } else if (endWeek == null) {
            model.addAttribute("information", "您并未选择课程结束周数");
            return "error";
        }

        session.setAttribute("beginWeek", beginWeek);
        session.setAttribute("endWeek", endWeek);
        setSessionUtil.setCourseDay(session);
        return "department/course/public/7_day";
    }

    /**
     * description: set public's day, and find the department/course/public/8_order.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/day.do")
    public String setPublicDay(CourseDay day, Model model, HttpSession session) {
        if (day == null) {
            model.addAttribute("information", "您并未选择课程上课日期");
            return "error";
        }

        session.setAttribute("day", day);
        model.addAttribute("orderArray", CourseOrder.values());
        return "department/course/public/8_order";
    }

    /**
     * description: set public's order, and find the department/course/public/9_classroom.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/order.do")
    public String setPublicOrder(CourseOrder beginOrder, CourseOrder endOrder, Model model,
                                 HttpSession session) {
        if (beginOrder == null) {
            model.addAttribute("information", "您并未选择课程开始时间");
            return "error";
        } else if (endOrder == null) {
            model.addAttribute("information", "您并未选择课程结束时间");
            return "error";
        }

        session.setAttribute("beginOrder", beginOrder);
        session.setAttribute("endOrder", endOrder);
        model.addAttribute("classrooms", CourseClassroom.values());
        return "department/course/public/9_classroom";
    }

    /**
     * description: set public's classroom, and find the department/course/public/10_ensure.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/classroom.do")
    public String setPublicClassroom(CourseClassroom classroom, Model model, HttpSession session) {
        if (classroom == null) {
            model.addAttribute("information", "您并未选择上课教室");
            return "error";
        }

        session.setAttribute("classroom", classroom);
        session.setAttribute("type", CourseType.PROFESSIONAL_COURSE);
        String[] classArray = ((String[]) session.getAttribute("classArray"));
        model.addAttribute("classNumber", classArray.length);
        model.addAttribute("studentNumber", studentService.getCourseClassStudentNumber(classArray));
        setSessionUtil.setCourseType(session);
        return "department/course/public/10_ensure";
    }

    /**
     * description: save the public course, and find the department/course/return.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/ensure.do")
    public String savePublic(Model model, HttpSession session) {
        PublicCourse publicCourse = new PublicCourse();
        publicCourse.setAcademyId(((Department) session.getAttribute("department")).getDepartmentId());
        publicCourse.setCourseYear((String) session.getAttribute("year"));
        publicCourse.setCourseSemester((CourseSemester) session.getAttribute("semester"));
        publicCourse.setCourseType((CourseType) session.getAttribute("type"));
        publicCourse.setCourseBeginWeek((CourseWeek) session.getAttribute("beginWeek"));
        publicCourse.setCourseEndWeek((CourseWeek) session.getAttribute("endWeek"));
        publicCourse.setCourseDay((CourseDay) session.getAttribute("day"));
        publicCourse.setCourseBeginOrder((CourseOrder) session.getAttribute("beginOrder"));
        publicCourse.setCourseEndOrder((CourseOrder) session.getAttribute("endOrder"));
        publicCourse.setCourseName((String) session.getAttribute("name"));
        publicCourse.setCourseClassroom((CourseClassroom) session.getAttribute("classroom"));
        publicCourse.setTeacherNeedRank((TeacherRank) session.getAttribute("rank"));

        int courseId = publicCourseService.setPublicCourse(publicCourse).getCourseId();
        publicCourseClassService.setCourseClass(courseId, (String[]) session.getAttribute(
                "classArray"));
        model.addAttribute("information", "您已完成该公共课课的设置");
        return "department/return";
    }

    /**
     * description: get all the classes, later find the department/course/professional/1_class.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/set.do")
    public String toSchool(Model model) {
        return "department/course/elective/1_size";
    }

    /**
     * description: get all the classes, later find the department/course/professional/1_class.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/size.do")
    public String setSchoolSize(String size, Model model, HttpSession session) {
        if (size == null || "".equals(size)) {
            model.addAttribute("information", "您并未输入课程的最大人数");
            return "error";
        } else if (!size.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的最大人数只能为数字");
            return "error";
        }

        session.setAttribute("size", Integer.parseInt(size));
        return "department/course/elective/2_year";
    }

    /**
     * description: set professional's year, and find the
     * department/course/professional/3_semester.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/year.do")
    public String setSchoolYear(String year, Model model, HttpSession session) {
        int systemYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year == null || "".equals(year)) {
            model.addAttribute("information", "您并未输入上课年份");
            return "error";
        } else if (!year.matches("[0-9]*")) {
            model.addAttribute("information", "您输入的上课年份只能为数字");
            return "error";
        } else if (Integer.parseInt(year) < systemYear) {
            model.addAttribute("information", "您输入的时间必须大于等于系统年份：" + systemYear);
            return "error";
        }

        session.setAttribute("year", year);
        setSessionUtil.setCourseSemester(session);
        return "department/course/elective/3_semester";
    }

    /**
     * description: set professional's semester, and find the
     * department/course/professional/4_name.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/semester.do")
    public String setSchoolSemester(CourseSemester semester, Model model, HttpSession session) {
        if (semester == null) {
            model.addAttribute("information", "您并未选择开课学期");
            return "error";
        }

        session.setAttribute("semester", semester);
        return "department/course/elective/4_name";
    }

    /**
     * description: set professional's name, and find the department/course/professional/5_rank.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/name.do")
    public String setSchoolName(String name, Model model, HttpSession session) {
        if (name == null || "".equals(name)) {
            model.addAttribute("information", "您并未输入课程名称");
            return "error";
        }

        session.setAttribute("name", name);
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/elective/6_week";
    }

    /**
     * description: set professional's week, and find the department/course/professional/7_day.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/week.do")
    public String setSchoolWeek(CourseWeek beginWeek, CourseWeek endWeek, Model model,
                                HttpSession session) {
        if (beginWeek == null) {
            model.addAttribute("information", "您并未选择课程开始周数");
            return "error";
        } else if (endWeek == null) {
            model.addAttribute("information", "您并未选择课程结束周数");
            return "error";
        }

        session.setAttribute("beginWeek", beginWeek);
        session.setAttribute("endWeek", endWeek);
        setSessionUtil.setCourseDay(session);
        return "department/course/elective/7_day";
    }

    /**
     * description: set professional's day, and find the department/course/professional/8_order.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/day.do")
    public String setSchoolDay(CourseDay day, Model model, HttpSession session) {
        if (day == null) {
            model.addAttribute("information", "您并未选择课程上课日期");
            return "error";
        }

        session.setAttribute("day", day);
        model.addAttribute("orderArray", CourseOrder.values());
        return "department/course/elective/8_order";
    }

    /**
     * description: set professional's order, and find the
     * department/course/professional/9_classroom.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/order.do")
    public String setSchoolOrder(CourseOrder beginOrder, CourseOrder endOrder, Model model,
                                 HttpSession session) {
        if (beginOrder == null) {
            model.addAttribute("information", "您并未选择课程开始时间");
            return "error";
        } else if (endOrder == null) {
            model.addAttribute("information", "您并未选择课程结束时间");
            return "error";
        }

        session.setAttribute("beginOrder", beginOrder);
        session.setAttribute("endOrder", endOrder);
        model.addAttribute("classrooms", CourseClassroom.values());
        return "department/course/elective/9_classroom";
    }

    /**
     * description: set professional's classroom, and find the
     * department/course/professional/10_ensure.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/classroom.do")
    public String setSchoolClassroom(CourseClassroom classroom, Model model, HttpSession session) {
        if (classroom == null) {
            model.addAttribute("information", "您并未选择上课教室");
            return "error";
        }

        session.setAttribute("classroom", classroom);
        session.setAttribute("type", CourseType.ELECTIVE_COURSE);
        setSessionUtil.setCourseType(session);
        return "department/course/elective/10_ensure";
    }

    /**
     * description: save setProfessionalCourse the professional course, and find the
     * department/course/return.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/ensure.do")
    public String saveSchool(Model model, HttpSession session) {
        ElectiveCourse electiveCourse = new ElectiveCourse();
        electiveCourse.setAcademyId(((Department) session.getAttribute("department")).getDepartmentId());
        electiveCourse.setCourseYear((String) session.getAttribute("year"));
        electiveCourse.setCourseSemester((CourseSemester) session.getAttribute("semester"));
        electiveCourse.setCourseType((CourseType) session.getAttribute("type"));
        electiveCourse.setCourseBeginWeek((CourseWeek) session.getAttribute("beginWeek"));
        electiveCourse.setCourseEndWeek((CourseWeek) session.getAttribute("endWeek"));
        electiveCourse.setCourseDay((CourseDay) session.getAttribute("day"));
        electiveCourse.setCourseBeginOrder((CourseOrder) session.getAttribute("beginOrder"));
        electiveCourse.setCourseEndOrder((CourseOrder) session.getAttribute("endOrder"));
        electiveCourse.setCourseName((String) session.getAttribute("name"));
        electiveCourse.setCourseClassroom((CourseClassroom) session.getAttribute("classroom"));
        electiveCourse.setTeacherNeedRank((TeacherRank) session.getAttribute("rank"));
        electiveCourse.setCourseSize((int) session.getAttribute("size"));

        electiveCourseService.setSchoolElectiveCourse(electiveCourse);
        model.addAttribute("information", "您已完成该选修课的设置");
        return "department/return";
    }

}
