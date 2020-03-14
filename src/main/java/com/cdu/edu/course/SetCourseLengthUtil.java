package com.cdu.edu.course;

import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.publics.PublicCourse;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/3/2 0002 下午 20:24
 * @since jdk 12.0.2
 */
@Component
public class SetCourseLengthUtil {

    public int[][] getSchoolElectiveCourseLength(SchoolElectiveCourse[][] schoolElectiveCourseArrayArray) {
        int[][] schoolElectiveCourseLengthArray = new int[13][8];
        for (int i = 1; i < schoolElectiveCourseArrayArray.length; i++) {
            for (int j = 1; j < schoolElectiveCourseArrayArray[i].length; j++) {
                if (schoolElectiveCourseArrayArray[i][j] != null) {
                    schoolElectiveCourseLengthArray[i][j] =
                            Integer.parseInt(schoolElectiveCourseArrayArray[i][j].getCourseEndOrder().toString().split("_")[1]) - Integer.parseInt(schoolElectiveCourseArrayArray[i][j].getCourseBeginOrder().toString().split("_")[1]) + 1;
                }
            }
        }
        return schoolElectiveCourseLengthArray;
    }

    public void setSchoolElectiveCourseLength(int[][] courseLengthArrayArray,
                                                     int[][] schoolElectiveCourseLengthArrayArray) {
        for (int i = 0; i < schoolElectiveCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < schoolElectiveCourseLengthArrayArray[i].length; j++) {
                if (schoolElectiveCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] =
                            schoolElectiveCourseLengthArrayArray[i][j];
                }
            }
        }
    }

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

    public void setProfessionalCourseLength(int[][] courseLengthArrayArray,
                                                   int[][] professionalCourseLengthArrayArray) {
        for (int i = 0; i < professionalCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < professionalCourseLengthArrayArray[i].length; j++) {
                if (professionalCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] =
                            professionalCourseLengthArrayArray[i][j];
                }
            }
        }
    }

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

    public void setPublicCourseLength(int[][] courseLengthArrayArray,
                                             int[][] publicCourseLengthArrayArray) {
        for (int i = 0; i < publicCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < publicCourseLengthArrayArray[i].length; j++) {
                if (publicCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] =
                            publicCourseLengthArrayArray[i][j];
                }
            }
        }
    }
}
