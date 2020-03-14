package com.cdu.edu.department;

import com.cdu.edu.course.*;
import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.course.elective.school.SchoolElectiveCourseService;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.professional.ProfessionalCourseService;
import com.cdu.edu.course.professional.classes.ProfessionalCourseClassService;
import com.cdu.edu.course.publics.PublicCourse;
import com.cdu.edu.course.publics.PublicCourseService;
import com.cdu.edu.course.publics.classes.PublicCourseClassService;
import com.cdu.edu.index.Identity;
import com.cdu.edu.index.LoginForm;
import com.cdu.edu.student.StudentService;
import com.cdu.edu.teacher.TeacherRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * description: the service by operation for departments
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/17 0017 上午 9:33
 * @since jdk
 */
@Controller
@RequestMapping("department")
public class DepartmentController {

    /**
     * student's service
     */
    @Autowired
    private StudentService studentService;

    /**
     * department's service
     */
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SchoolElectiveCourseService schoolElectiveCourseService;

    @Autowired
    private ProfessionalCourseService professionalCourseService;

    @Autowired
    private ProfessionalCourseClassService professionalCourseClassService;

    @Autowired
    private PublicCourseService publicCourseService;

    @Autowired
    private PublicCourseClassService publicCourseClassService;

    /**
     * description: the IndexController's login by department would come here
     *
     * @param username the department's username
     * @param password the department's password
     * @return java.lang.String
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
     * description: save setProfessionalCourse the professional course, and find the department/course/return.html
     *
     * @return java.lang.String
     */
    @RequestMapping("return.do")
    public String returnIndex() {
        return "department/index";
    }

    /**
     * description: the model setProfessionalCourse of a department
     *
     * @return com.cdu.edu.department.Department
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
     * description: set professional's class, and find the department/course/professional/2_year.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/class.do")
    public String setProfessionalClass(String[] classArray, Model model, HttpSession session) {
        session.setAttribute("classArray", classArray);
        model.addAttribute("year", Calendar.getInstance().get(Calendar.YEAR));
        return "department/course/professional/2_year";
    }

    /**
     * description: set professional's year, and find the department/course/professional/3_semester.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/year.do")
    public String setProfessionalYear(String year, HttpSession session) {
        session.setAttribute("year", year);
        session.setAttribute("LAST_HALF_SEMESTER", CourseSemester.LAST_HALF_SEMESTER);
        session.setAttribute("NEXT_HALF_SEMESTER", CourseSemester.NEXT_HALF_SEMESTER);
        return "department/course/professional/3_semester";
    }

    /**
     * description: set professional's semester, and find the department/course/professional/4_name.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/semester.do")
    public String setProfessionalSemester(CourseSemester semester, HttpSession session) {
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
        session.setAttribute("name", name);
//        session.setAttribute("ASSISTANT", TeacherRank.ASSISTANT);
//        session.setAttribute("LECTURER", TeacherRank.LECTURER);
//        session.setAttribute("ASSOCIATE_PROFESSOR", TeacherRank.ASSOCIATE_PROFESSOR);
//        session.setAttribute("PROFESSOR", TeacherRank.PROFESSOR);
//        return "department/course/professional/5_rank";
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/professional/6_week";
    }

    /**
     * description: set professional's rank, and find the department/course/professional/6_week.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/rank.do")
    public String setProfessionalRank(TeacherRank rank, Model model, HttpSession session) {
        session.setAttribute("rank", rank);
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/professional/6_week";
        // this method is overtime
    }

    /**
     * description: set professional's week, and find the department/course/professional/7_day.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/week.do")
    public String setProfessionalWeek(CourseWeek beginWeek, CourseWeek endWeek, Model model, HttpSession session) {
        session.setAttribute("beginWeek", beginWeek);
        session.setAttribute("endWeek", endWeek);
        session.setAttribute("DAY_1", CourseDay.DAY_1);
        session.setAttribute("DAY_2", CourseDay.DAY_2);
        session.setAttribute("DAY_3", CourseDay.DAY_3);
        session.setAttribute("DAY_4", CourseDay.DAY_4);
        session.setAttribute("DAY_5", CourseDay.DAY_5);
        session.setAttribute("DAY_6", CourseDay.DAY_6);
        session.setAttribute("DAY_7", CourseDay.DAY_7);
        return "department/course/professional/7_day";
    }

    /**
     * description: set professional's day, and find the department/course/professional/8_order.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/day.do")
    public String setProfessionalDay(CourseDay day, Model model, HttpSession session) {
        session.setAttribute("day", day);
        model.addAttribute("orderArray", CourseOrder.values());
        return "department/course/professional/8_order";
    }

    /**
     * description: set professional's order, and find the department/course/professional/9_classroom.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/order.do")
    public String setProfessionalOrder(CourseOrder beginOrder, CourseOrder endOrder, Model model, HttpSession session) {
        session.setAttribute("beginOrder", beginOrder);
        session.setAttribute("endOrder", endOrder);
        model.addAttribute("classrooms", CourseClassroom.values());
        return "department/course/professional/9_classroom";
    }

    /**
     * description: set professional's classroom, and find the department/course/professional/10_ensure.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/classroom.do")
    public String setProfessionalClassroom(CourseClassroom classroom, Model model, HttpSession session) {
        session.setAttribute("classroom", classroom);
        session.setAttribute("type", CourseType.PROFESSIONAL_COURSE);

        String[] classArray = ((String[]) session.getAttribute("classArray"));
        model.addAttribute("classNumber", classArray.length);
        model.addAttribute("studentNumber", studentService.getCourseClassStudentNumber(classArray));

        session.setAttribute("PROFESSIONAL_COURSE", CourseType.PROFESSIONAL_COURSE);
        session.setAttribute("PUBLIC_COURSE", CourseType.PUBLIC_COURSE);
        session.setAttribute("SCHOOL_ELECTIVE_COURSE", CourseType.SCHOOL_ELECTIVE_COURSE);
        session.setAttribute("SPORTS_ELECTIVE_COURSE", CourseType.SPORTS_ELECTIVE_COURSE);
        return "department/course/professional/10_ensure";
    }

    /**
     * description: save setProfessionalCourse the professional course, and find the department/course/return.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/professional/ensure.do")
    public String saveProfessional(HttpSession session) {
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

        int courseId = professionalCourseService.setProfessionalCourse(professionalCourse).getCourseId();
        professionalCourseClassService.setCourseClass(courseId, (String[]) session.getAttribute("classArray"));
        return "department/course/achieve";
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
        session.setAttribute("classArray", classArray);
        model.addAttribute("year", Calendar.getInstance().get(Calendar.YEAR));
        return "department/course/public/2_year";
    }

    /**
     * description: set public's year, and find the department/course/public/3_semester.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/year.do")
    public String setPublicYear(String year, HttpSession session) {
        session.setAttribute("year", year);
        session.setAttribute("LAST_HALF_SEMESTER", CourseSemester.LAST_HALF_SEMESTER);
        session.setAttribute("NEXT_HALF_SEMESTER", CourseSemester.NEXT_HALF_SEMESTER);
        return "department/course/public/3_semester";
    }

    /**
     * description: set public's semester, and find the department/course/public/4_name.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/semester.do")
    public String setPublicSemester(CourseSemester semester, HttpSession session) {
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
        session.setAttribute("name", name);
//        session.setAttribute("ASSISTANT", TeacherRank.ASSISTANT);
//        session.setAttribute("LECTURER", TeacherRank.LECTURER);
//        session.setAttribute("ASSOCIATE_PROFESSOR", TeacherRank.ASSOCIATE_PROFESSOR);
//        session.setAttribute("PROFESSOR", TeacherRank.PROFESSOR);
//        return "department/course/public/5_rank";
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/public/6_week";
    }

    /**
     * description: set public's rank, and find the department/course/public/6_week.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/rank.do")
    public String setPublicRank(TeacherRank rank, Model model, HttpSession session) {
        session.setAttribute("rank", rank);
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/public/6_week";
    }

    /**
     * description: set public's week, and find the department/course/public/7_day.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/week.do")
    public String setPublicWeek(CourseWeek beginWeek, CourseWeek endWeek, Model model, HttpSession session) {
        session.setAttribute("beginWeek", beginWeek);
        session.setAttribute("endWeek", endWeek);
        session.setAttribute("DAY_1", CourseDay.DAY_1);
        session.setAttribute("DAY_2", CourseDay.DAY_2);
        session.setAttribute("DAY_3", CourseDay.DAY_3);
        session.setAttribute("DAY_4", CourseDay.DAY_4);
        session.setAttribute("DAY_5", CourseDay.DAY_5);
        session.setAttribute("DAY_6", CourseDay.DAY_6);
        session.setAttribute("DAY_7", CourseDay.DAY_7);
        return "department/course/public/7_day";
    }

    /**
     * description: set public's day, and find the department/course/public/8_order.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/day.do")
    public String setPublicDay(CourseDay day, Model model, HttpSession session) {
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
    public String setPublicOrder(CourseOrder beginOrder, CourseOrder endOrder, Model model, HttpSession session) {
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
        session.setAttribute("classroom", classroom);
        session.setAttribute("type", CourseType.PUBLIC_COURSE);
        model.addAttribute("classNumber", ((String[]) session.getAttribute("classArray")).length);
        model.addAttribute("studentNumber",
                studentService.getCourseClassStudentNumber((String[]) session.getAttribute("classArray")));

        session.setAttribute("PROFESSIONAL_COURSE", CourseType.PROFESSIONAL_COURSE);
        session.setAttribute("PUBLIC_COURSE", CourseType.PUBLIC_COURSE);
        session.setAttribute("SCHOOL_ELECTIVE_COURSE", CourseType.SCHOOL_ELECTIVE_COURSE);
        session.setAttribute("SPORTS_ELECTIVE_COURSE", CourseType.SPORTS_ELECTIVE_COURSE);
        return "department/course/public/10_ensure";
    }

    /**
     * description: save the public course, and find the department/course/return.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/public/ensure.do")
    public String savePublic(HttpSession session) {
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
        publicCourseClassService.setCourseClass(courseId, (String[]) session.getAttribute("classArray"));
        return "department/course/achieve";
    }

    /**
     * description: get all the classes, later find the department/course/professional/1_class.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/set.do")
    public String toSchool(Model model) {
        return "department/course/elective/school/1_size";
    }

    /**
     * description: get all the classes, later find the department/course/professional/1_class.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/size.do")
    public String setSchoolSize(int size, Model model, HttpSession session) {
        session.setAttribute("size", size);
        model.addAttribute("year", Calendar.getInstance().get(Calendar.YEAR));
        return "department/course/elective/school/2_year";
    }

    /**
     * description: set professional's year, and find the department/course/professional/3_semester.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/year.do")
    public String setSchoolYear(String year, HttpSession session) {
        session.setAttribute("year", year);
        session.setAttribute("LAST_HALF_SEMESTER", CourseSemester.LAST_HALF_SEMESTER);
        session.setAttribute("NEXT_HALF_SEMESTER", CourseSemester.NEXT_HALF_SEMESTER);
        return "department/course/elective/school/3_semester";
    }

    /**
     * description: set professional's semester, and find the department/course/professional/4_name.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/semester.do")
    public String setSchoolSemester(CourseSemester semester, HttpSession session) {
        session.setAttribute("semester", semester);
        return "department/course/elective/school/4_name";
    }

    /**
     * description: set professional's name, and find the department/course/professional/5_rank.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/name.do")
    public String setSchoolName(String name, Model model, HttpSession session) {
        session.setAttribute("name", name);
//        session.setAttribute("ASSISTANT", TeacherRank.ASSISTANT);
//        session.setAttribute("LECTURER", TeacherRank.LECTURER);
//        session.setAttribute("ASSOCIATE_PROFESSOR", TeacherRank.ASSOCIATE_PROFESSOR);
//        session.setAttribute("PROFESSOR", TeacherRank.PROFESSOR);
//        return "department/course/elective/school/5_rank";
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/elective/school/6_week";
    }

    /**
     * description: set professional's rank, and find the department/course/professional/6_week.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/rank.do")
    public String setSchoolRank(TeacherRank rank, Model model, HttpSession session) {
        session.setAttribute("rank", rank);
        model.addAttribute("weeks", CourseWeek.values());
        return "department/course/elective/school/6_week";
    }

    /**
     * description: set professional's week, and find the department/course/professional/7_day.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/week.do")
    public String setSchoolWeek(CourseWeek beginWeek, CourseWeek endWeek, Model model, HttpSession session) {
        session.setAttribute("beginWeek", beginWeek);
        session.setAttribute("endWeek", endWeek);
        session.setAttribute("DAY_1", CourseDay.DAY_1);
        session.setAttribute("DAY_2", CourseDay.DAY_2);
        session.setAttribute("DAY_3", CourseDay.DAY_3);
        session.setAttribute("DAY_4", CourseDay.DAY_4);
        session.setAttribute("DAY_5", CourseDay.DAY_5);
        session.setAttribute("DAY_6", CourseDay.DAY_6);
        session.setAttribute("DAY_7", CourseDay.DAY_7);
        return "department/course/elective/school/7_day";
    }

    /**
     * description: set professional's day, and find the department/course/professional/8_order.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/day.do")
    public String setSchoolDay(CourseDay day, Model model, HttpSession session) {
        session.setAttribute("day", day);
        model.addAttribute("orderArray", CourseOrder.values());
        return "department/course/elective/school/8_order";
    }

    /**
     * description: set professional's order, and find the department/course/professional/9_classroom.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/order.do")
    public String setSchoolOrder(CourseOrder beginOrder, CourseOrder endOrder, Model model, HttpSession session) {
        session.setAttribute("beginOrder", beginOrder);
        session.setAttribute("endOrder", endOrder);
        model.addAttribute("classrooms", CourseClassroom.values());
        return "department/course/elective/school/9_classroom";
    }

    /**
     * description: set professional's classroom, and find the department/course/professional/10_ensure.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/classroom.do")
    public String setSchoolClassroom(CourseClassroom classroom, Model model, HttpSession session) {
        session.setAttribute("classroom", classroom);
        session.setAttribute("type", CourseType.SCHOOL_ELECTIVE_COURSE);

        session.setAttribute("PROFESSIONAL_COURSE", CourseType.PROFESSIONAL_COURSE);
        session.setAttribute("PUBLIC_COURSE", CourseType.PUBLIC_COURSE);
        session.setAttribute("SCHOOL_ELECTIVE_COURSE", CourseType.SCHOOL_ELECTIVE_COURSE);
        session.setAttribute("SPORTS_ELECTIVE_COURSE", CourseType.SPORTS_ELECTIVE_COURSE);
        return "department/course/elective/school/10_ensure";
    }

    /**
     * description: save setProfessionalCourse the professional course, and find the department/course/return.html
     *
     * @return java.lang.String
     */
    @RequestMapping("course/elective/school/ensure.do")
    public String saveSchool(HttpSession session) {
        SchoolElectiveCourse schoolElectiveCourse = new SchoolElectiveCourse();
        schoolElectiveCourse.setAcademyId(((Department) session.getAttribute("department")).getDepartmentId());
        schoolElectiveCourse.setCourseYear((String) session.getAttribute("year"));
        schoolElectiveCourse.setCourseSemester((CourseSemester) session.getAttribute("semester"));
        schoolElectiveCourse.setCourseType((CourseType) session.getAttribute("type"));
        schoolElectiveCourse.setCourseBeginWeek((CourseWeek) session.getAttribute("beginWeek"));
        schoolElectiveCourse.setCourseEndWeek((CourseWeek) session.getAttribute("endWeek"));
        schoolElectiveCourse.setCourseDay((CourseDay) session.getAttribute("day"));
        schoolElectiveCourse.setCourseBeginOrder((CourseOrder) session.getAttribute("beginOrder"));
        schoolElectiveCourse.setCourseEndOrder((CourseOrder) session.getAttribute("endOrder"));
        schoolElectiveCourse.setCourseName((String) session.getAttribute("name"));
        schoolElectiveCourse.setCourseClassroom((CourseClassroom) session.getAttribute("classroom"));
        schoolElectiveCourse.setTeacherNeedRank((TeacherRank) session.getAttribute("rank"));
        schoolElectiveCourse.setCourseSize((int) session.getAttribute("size"));

        int courseId = schoolElectiveCourseService.setSchoolElectiveCourse(schoolElectiveCourse).getCourseId();
        return "department/course/achieve";
    }

}
