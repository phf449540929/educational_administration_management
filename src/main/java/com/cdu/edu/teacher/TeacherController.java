package com.cdu.edu.teacher;

import com.cdu.edu.course.CourseDay;
import com.cdu.edu.course.CourseSemester;
import com.cdu.edu.course.CourseType;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.professional.ProfessionalCourseService;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClass;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClassService;
import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.course.elective.school.SchoolElectiveCourseService;
import com.cdu.edu.course.elective.school.student.SchoolElectiveCourseStudentService;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.publics.PublicCourseService;
import com.cdu.edu.course.publics.classes.PublicCourseClass;
import com.cdu.edu.course.publics.classes.PublicCourseClassService;
import com.cdu.edu.department.DepartmentService;
import com.cdu.edu.index.Identity;
import com.cdu.edu.index.LoginForm;
import com.cdu.edu.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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
            session.setAttribute("ASSISTANT", TeacherRank.ASSISTANT);
            session.setAttribute("LECTURER", TeacherRank.LECTURER);
            session.setAttribute("ASSOCIATE_PROFESSOR", TeacherRank.ASSOCIATE_PROFESSOR);
            session.setAttribute("PROFESSOR", TeacherRank.PROFESSOR);

            model.addAttribute("teacherDepartmentName", departmentService.getDepartmentName(teacher.getTeacherDepartment()));
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
     * description: save setProfessionalCourse the professional course, and find the department/course/achieve.html
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
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/professional/set.do")
    public String toProfessional(Model model, HttpSession session) {
        List<ProfessionalCourse> professionalCourseList = professionalCourseService.getProfessionalCourse();
        model.addAttribute("courseList", professionalCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(professionalCourseList.toArray(), CourseType.PROFESSIONAL_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(professionalCourseList.toArray(), CourseType.PROFESSIONAL_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(professionalCourseList.toArray(), CourseType.PROFESSIONAL_COURSE));

        Map<Integer, List<ProfessionalCourseClass>> courseClassMap =
                professionalCourseClassService.getCourseClass(professionalCourseList);
        session.setAttribute("courseClassMap", courseClassMap);
        session.setAttribute("courseClassStudentNumberMap",
                studentService.getCourseClassStudentNumber(professionalCourseList, courseClassMap, null, null, CourseType.PROFESSIONAL_COURSE));

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

        return "teacher/course/professional/select";
    }

    /**
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/professional/select.do")
    public String selectProfessional(int[] courseIdArray, Model model, HttpSession session) {
        session.setAttribute("courseIdArray", courseIdArray);
        model.addAttribute("courseList", professionalCourseService.getProfessionalCourse(courseIdArray));
        return "teacher/course/professional/ensure";
    }

    /**
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/professional/ensure.do")
    public String saveProfessional(HttpSession session) {
        professionalCourseService.setProfessionalCourse(
                (int[]) session.getAttribute("courseIdArray"), (Teacher) session.getAttribute("teacher"));
        return "teacher/course/achieve";
    }

    /**
     * description: get all the public course, and return to teacher/course/public/select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/public/set.do")
    public String toPublic(Model model, HttpSession session) {
        List<PublicCourse> publicCourseList = publicCourseService.getPublicCourse();
        model.addAttribute("courseList", publicCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(publicCourseList.toArray(), CourseType.PUBLIC_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(publicCourseList.toArray(), CourseType.PUBLIC_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(publicCourseList.toArray(), CourseType.PUBLIC_COURSE));

        Map<Integer, List<PublicCourseClass>> courseClassMap =
                publicCourseClassService.getCourseClass(publicCourseList);
        session.setAttribute("courseClassMap", courseClassMap);
        session.setAttribute("courseClassStudentNumberMap",
                studentService.getCourseClassStudentNumber(null, null, publicCourseList, courseClassMap, CourseType.PUBLIC_COURSE));

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

        return "teacher/course/public/select";
    }

    /**
     * description: get all the public course, and return to teacher/course/public/select.html
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
     * description: get all the public course, and return to teacher/course/public/select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/public/ensure.do")
    public String savePublic(HttpSession session) {
        publicCourseService.setPublicCourse(
                (int[]) session.getAttribute("courseIdArray"), (Teacher) session.getAttribute("teacher"));
        return "teacher/course/achieve";
    }

    /**
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param model the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/set.do")
    public String toSchool(Model model, HttpSession session) {
        List<SchoolElectiveCourse> schoolElectiveCourseList = schoolElectiveCourseService.getSchoolElectiveCourse();
        model.addAttribute("courseList", schoolElectiveCourseList);

        session.setAttribute("departmentNameMap",
                departmentService.getDepartmentName(schoolElectiveCourseList.toArray(), CourseType.SCHOOL_ELECTIVE_COURSE));

        session.setAttribute("teacherNameMap",
                teacherService.getTeacherName(schoolElectiveCourseList.toArray(), CourseType.SCHOOL_ELECTIVE_COURSE));
        session.setAttribute("teacherRankMap",
                teacherService.getTeacherRank(schoolElectiveCourseList.toArray(), CourseType.SCHOOL_ELECTIVE_COURSE));

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

        return "teacher/course/elective/school/select";
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
        return "teacher/course/elective/school/ensure";
    }

    /**
     * description: get all the professional course, and return to teacher/course/professional/select.html
     *
     * @param session the Model of the SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/ensure.do")
    public String saveSchool(HttpSession session) {
        schoolElectiveCourseService.setSchoolElectiveCourse(
                (int[]) session.getAttribute("courseIdArray"), (Teacher) session.getAttribute("teacher"));
        return "teacher/course/achieve";
    }
}
