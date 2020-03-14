package com.cdu.edu.course;

import com.cdu.edu.course.elective.school.SchoolElectiveCourse;
import com.cdu.edu.course.professional.ProfessionalCourse;
import com.cdu.edu.course.publics.PublicCourse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/3/2 0002 下午 20:25
 * @since jdk 12.0.2
 */
@Component
public class SetCourseUtil {

    public SchoolElectiveCourse[][] getSchoolElectiveCourse(List<SchoolElectiveCourse> schoolElectiveCourseList) {
        SchoolElectiveCourse[][] schoolElectiveCourseArrayArray = new SchoolElectiveCourse[13][8];
        for (SchoolElectiveCourse schoolElectiveCourse : schoolElectiveCourseList) {
            int beginOrder =
                    Integer.parseInt(schoolElectiveCourse.getCourseBeginOrder().toString().split(
                            "_")[1]);
            int endOrder =
                    Integer.parseInt(schoolElectiveCourse.getCourseEndOrder().toString().split("_"
                    )[1]);
            int day =
                    Integer.parseInt(schoolElectiveCourse.getCourseDay().toString().split("_")[1]);
            for (int i = beginOrder; i <= endOrder; i++) {
                schoolElectiveCourseArrayArray[i][day] = schoolElectiveCourse;
            }
        }
        return schoolElectiveCourseArrayArray;
    }

    public void setSchoolElectiveCourse(AbstractCourse[][] courseArrayArray,
                                               SchoolElectiveCourse[][] schoolElectiveCourseArrayArray) {
        for (int i = 0; i < schoolElectiveCourseArrayArray.length; i++) {
            for (int j = 0; j < schoolElectiveCourseArrayArray[i].length; j++) {
                if (schoolElectiveCourseArrayArray[i][j] != null) {
                    courseArrayArray[i][j] = schoolElectiveCourseArrayArray[i][j];
                }
            }
        }
    }

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
