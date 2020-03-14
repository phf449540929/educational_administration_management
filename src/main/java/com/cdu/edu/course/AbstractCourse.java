package com.cdu.edu.course;

import com.cdu.edu.teacher.TeacherRank;

import javax.persistence.*;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/12 0012 上午 11:31
 * @since jdk
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractCourse {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int courseId;
    @Column(nullable = false)
    private String academyId;
    @Column(nullable = false)
    private String courseYear;
    @Column(nullable = false)
    private CourseSemester courseSemester;
    @Column(nullable = false)
    private CourseType courseType;
    @Column(nullable = false)
    private CourseWeek courseBeginWeek;
    @Column(nullable = false)
    private CourseWeek courseEndWeek;
    @Column(nullable = false)
    private CourseDay courseDay;
    @Column(nullable = false)
    private CourseOrder courseBeginOrder;
    @Column(nullable = false)
    private CourseOrder courseEndOrder;
    @Column
    private String courseName;
    @Column
    private CourseClassroom courseClassroom;
    @Column
    private String teacherId;
    @Column
    private TeacherRank teacherNeedRank;
    @Column
    private float rollRatio;
    @Column
    private float usualRatio;
    @Column
    private float experimentalRatio;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getAcademyId() {
        return academyId;
    }

    public void setAcademyId(String academyId) {
        this.academyId = academyId;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public CourseSemester getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(CourseSemester courseSemester) {
        this.courseSemester = courseSemester;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public CourseWeek getCourseBeginWeek() {
        return courseBeginWeek;
    }

    public void setCourseBeginWeek(CourseWeek courseBeginWeek) {
        this.courseBeginWeek = courseBeginWeek;
    }

    public CourseWeek getCourseEndWeek() {
        return courseEndWeek;
    }

    public void setCourseEndWeek(CourseWeek courseEndWeek) {
        this.courseEndWeek = courseEndWeek;
    }

    public CourseDay getCourseDay() {
        return courseDay;
    }

    public void setCourseDay(CourseDay courseDay) {
        this.courseDay = courseDay;
    }

    public CourseOrder getCourseBeginOrder() {
        return courseBeginOrder;
    }

    public void setCourseBeginOrder(CourseOrder courseBeginOrder) {
        this.courseBeginOrder = courseBeginOrder;
    }

    public CourseOrder getCourseEndOrder() {
        return courseEndOrder;
    }

    public void setCourseEndOrder(CourseOrder courseEndOrder) {
        this.courseEndOrder = courseEndOrder;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseClassroom getCourseClassroom() {
        return courseClassroom;
    }

    public void setCourseClassroom(CourseClassroom courseClassroom) {
        this.courseClassroom = courseClassroom;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherRank getTeacherNeedRank() {
        return teacherNeedRank;
    }

    public void setTeacherNeedRank(TeacherRank teacherNeedRank) {
        this.teacherNeedRank = teacherNeedRank;
    }

    public float getRollRatio() {
        return rollRatio;
    }

    public void setRollRatio(float rollRatio) {
        this.rollRatio = rollRatio;
    }

    public float getUsualRatio() {
        return usualRatio;
    }

    public void setUsualRatio(float usualRatio) {
        this.usualRatio = usualRatio;
    }

    public float getExperimentalRatio() {
        return experimentalRatio;
    }

    public void setExperimentalRatio(float experimentalRatio) {
        this.experimentalRatio = experimentalRatio;
    }
}
