package com.cdu.edu.course;

import com.cdu.edu.course.elective.ElectiveCourse;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.publics.PublicCourse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description: 生成课表二维数组的工具类
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/3/2 0002 下午 20:25
 * @since jdk 12.0.2
 */
@Component
public class SetCourseUtil {

    /**
     * description: 生成选修课二维数组
     *
     * @param electiveCourseList 选修课List
     * @return com.cdu.edu.course.elective.ElectiveCourse[][] 选修课二维数组
     */
    public ElectiveCourse[][] getElectiveCourse(List<ElectiveCourse> electiveCourseList) {
        ElectiveCourse[][] electiveCourseArrayArray = new ElectiveCourse[13][8];
        for (ElectiveCourse electiveCourse : electiveCourseList) {
            int beginOrder =
                    Integer.parseInt(electiveCourse.getCourseBeginOrder().toString().split("_")[1]);
            int endOrder = Integer.parseInt(electiveCourse.getCourseEndOrder().toString().split(
                    "_")[1]);
            int day = Integer.parseInt(electiveCourse.getCourseDay().toString().split("_")[1]);
            for (int i = beginOrder; i <= endOrder; i++) {
                electiveCourseArrayArray[i][day] = electiveCourse;
            }
        }
        return electiveCourseArrayArray;
    }

    /**
     * description: 将选修课二维数组填入综合三种课程的二维数组中
     *
     * @param courseArrayArray         综合三种课程的二维数组
     * @param electiveCourseArrayArray 选修课二维数组
     */
    public void setElectiveCourse(AbstractCourse[][] courseArrayArray,
                                  ElectiveCourse[][] electiveCourseArrayArray) {
        for (int i = 0; i < electiveCourseArrayArray.length; i++) {
            for (int j = 0; j < electiveCourseArrayArray[i].length; j++) {
                if (electiveCourseArrayArray[i][j] != null) {
                    courseArrayArray[i][j] = electiveCourseArrayArray[i][j];
                }
            }
        }
    }

    /**
     * description: 生成专业课二维数组
     *
     * @param professionalCourseList 专业课List
     * @return com.cdu.edu.course.professional[][] 专业课二维数组
     */
    public ProfessionalCourse[][] getProfessionalCourse(List<ProfessionalCourse> professionalCourseList) {
        ProfessionalCourse[][] professionalCourseArrayArray = new ProfessionalCourse[13][8];
        for (ProfessionalCourse professionalCourse : professionalCourseList) {
            int beginOrder =
                    Integer.parseInt(professionalCourse.getCourseBeginOrder().toString().split("_"
                    )[1]);
            int endOrder =
                    Integer.parseInt(professionalCourse.getCourseEndOrder().toString().split("_")[1]);
            int day = Integer.parseInt(professionalCourse.getCourseDay().toString().split("_")[1]);
            for (int i = beginOrder; i <= endOrder; i++) {
                professionalCourseArrayArray[i][day] = professionalCourse;
            }
        }
        return professionalCourseArrayArray;
    }

    /**
     * description: 将专业课二维数组填入综合了三种课程的二维数组中
     *
     * @param courseArrayArray             综合三种课程的二维数组
     * @param professionalCourseArrayArray 选修课二维数组
     */
    public void setProfessionalCourse(AbstractCourse[][] courseArrayArray,
                                      ProfessionalCourse[][] professionalCourseArrayArray) {
        for (int i = 0; i < professionalCourseArrayArray.length; i++) {
            for (int j = 0; j < professionalCourseArrayArray[i].length; j++) {
                if (professionalCourseArrayArray[i][j] != null) {
                    courseArrayArray[i][j] = professionalCourseArrayArray[i][j];
                }
            }
        }
    }

    /**
     * description: 生成公共课二维数组
     *
     * @param publicCourseList 公共课List
     * @return com.cdu.edu.course.publics.PublicCourse[][] 公共课二维数组
     */
    public PublicCourse[][] getPublicCourse(List<PublicCourse> publicCourseList) {
        PublicCourse[][] publicCourseArrayArray = new PublicCourse[13][8];
        for (PublicCourse publicCourse : publicCourseList) {
            int beginOrder =
                    Integer.parseInt(publicCourse.getCourseBeginOrder().toString().split("_")[1]);
            int endOrder =
                    Integer.parseInt(publicCourse.getCourseEndOrder().toString().split("_")[1]);
            int day = Integer.parseInt(publicCourse.getCourseDay().toString().split("_")[1]);
            for (int i = beginOrder; i <= endOrder; i++) {
                publicCourseArrayArray[i][day] = publicCourse;
            }
        }
        return publicCourseArrayArray;
    }

    /**
     * description: 将公共课二维数组填入综合了三种课程的二维数组中
     *
     * @param courseArrayArray       综合三种课程的二维数组
     * @param publicCourseArrayArray 公共课课二维数组
     */
    public void setPublicCourse(AbstractCourse[][] courseArrayArray,
                                PublicCourse[][] publicCourseArrayArray) {
        for (int i = 0; i < publicCourseArrayArray.length; i++) {
            for (int j = 0; j < publicCourseArrayArray[i].length; j++) {
                if (publicCourseArrayArray[i][j] != null) {
                    courseArrayArray[i][j] = publicCourseArrayArray[i][j];
                }
            }
        }
    }

}
