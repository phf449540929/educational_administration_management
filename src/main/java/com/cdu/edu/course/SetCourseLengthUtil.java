package com.cdu.edu.course;

import com.cdu.edu.course.elective.ElectiveCourse;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.publics.PublicCourse;
import org.springframework.stereotype.Component;

/**
 * description: 生成与课表相对应的课程上课时长二维数组的工具类
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/3/2 0002 下午 20:24
 * @since jdk 12.0.2
 */
@Component
public class SetCourseLengthUtil {

    /**
     * description: 生成选修课上课时长二维数组
     *
     * @param electiveCourseArrayArray 选修课二维数组
     * @return java.lang.Integer[][] 选修课上课时长二维数组
     */
    public int[][] getElectiveCourseLength(ElectiveCourse[][] electiveCourseArrayArray) {
        int[][] electiveCourseLengthArray = new int[13][8];
        for (int i = 1; i < electiveCourseArrayArray.length; i++) {
            for (int j = 1; j < electiveCourseArrayArray[i].length; j++) {
                if (electiveCourseArrayArray[i][j] != null) {
                    electiveCourseLengthArray[i][j] =
                            Integer.parseInt(electiveCourseArrayArray[i][j].getCourseEndOrder().toString().split("_")[1]) - Integer.parseInt(electiveCourseArrayArray[i][j].getCourseBeginOrder().toString().split("_")[1]) + 1;
                }
            }
        }
        return electiveCourseLengthArray;
    }

    /**
     * description: 将选修课上课时长二维数组填入综合三种课程的上课时长二维数组中
     *
     * @param courseLengthArrayArray         综合三种课程的上课时长二维数组
     * @param electiveCourseLengthArrayArray 选修课上课时长二维数组
     */
    public void setElectiveCourseLength(int[][] courseLengthArrayArray,
                                        int[][] electiveCourseLengthArrayArray) {
        for (int i = 0; i < electiveCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < electiveCourseLengthArrayArray[i].length; j++) {
                if (electiveCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] = electiveCourseLengthArrayArray[i][j];
                }
            }
        }
    }

    /**
     * description: 生成专业课上课时长二维数组
     *
     * @param professionalCourseArrayArray 专业课二维数组
     * @return java.lang.Integer[][] 专业课上课时长二维数组
     */
    public int[][] getProfessionalCourseLength(ProfessionalCourse[][] professionalCourseArrayArray) {
        int[][] professionalCourseLengthArrayArray = new int[13][8];
        for (int i = 1; i < professionalCourseArrayArray.length; i++) {
            for (int j = 1; j < professionalCourseArrayArray[i].length; j++) {
                if (professionalCourseArrayArray[i][j] != null) {
                    professionalCourseLengthArrayArray[i][j] =
                            Integer.parseInt(professionalCourseArrayArray[i][j].getCourseEndOrder().toString().split("_")[1]) - Integer.parseInt(professionalCourseArrayArray[i][j].getCourseBeginOrder().toString().split("_")[1]) + 1;
                }
            }
        }
        return professionalCourseLengthArrayArray;
    }

    /**
     * 将专业课上课时长二维数组填入综合三种课程的上课时长二维数组中
     *
     * @param courseLengthArrayArray             综合三种课程的上课时长二维数组
     * @param professionalCourseLengthArrayArray 专业课上课时长二维数组
     */
    public void setProfessionalCourseLength(int[][] courseLengthArrayArray,
                                            int[][] professionalCourseLengthArrayArray) {
        for (int i = 0; i < professionalCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < professionalCourseLengthArrayArray[i].length; j++) {
                if (professionalCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] = professionalCourseLengthArrayArray[i][j];
                }
            }
        }
    }

    /**
     * 将公共课上课时长二维数组填入综合三种课程的上课时长二维数组中
     *
     * @param publicCourseArrayArray 公共课二维数组
     * @return java.lang.Integer[][] 公共课上课时长二维数组
     */
    public int[][] getPublicCourseLength(PublicCourse[][] publicCourseArrayArray) {
        int[][] publicCourseLengthArrayArray = new int[13][8];
        for (int i = 1; i < publicCourseArrayArray.length; i++) {
            for (int j = 1; j < publicCourseArrayArray[i].length; j++) {
                if (publicCourseArrayArray[i][j] != null) {
                    publicCourseLengthArrayArray[i][j] =
                            Integer.parseInt(publicCourseArrayArray[i][j].getCourseEndOrder().toString().split("_")[1]) - Integer.parseInt(publicCourseArrayArray[i][j].getCourseBeginOrder().toString().split("_")[1]) + 1;
                }
            }
        }
        return publicCourseLengthArrayArray;
    }

    /**
     * 将公共课上课时长二维数组填入综合三种课程的上课时长二维数组中
     *
     * @param courseLengthArrayArray       综合三种课程的上课时长二维数组
     * @param publicCourseLengthArrayArray 公共课上课时长二维数组
     */
    public void setPublicCourseLength(int[][] courseLengthArrayArray,
                                      int[][] publicCourseLengthArrayArray) {
        for (int i = 0; i < publicCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < publicCourseLengthArrayArray[i].length; j++) {
                if (publicCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] = publicCourseLengthArrayArray[i][j];
                }
            }
        }
    }

}
