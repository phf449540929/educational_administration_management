package com.cdu.edu.course.elective.school;

import com.cdu.edu.course.AbstractCourse;
import com.cdu.edu.course.elective.school.student.SchoolElectiveCourseStudent;
import com.cdu.edu.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/11/2 0002 上午 11:53
 * @since jdk
 */
@Service
public class SchoolElectiveCourseService {

    @Autowired
    private SchoolElectiveCourseRepository schoolElectiveCourseRepository;

    /**
     * description: setSchoolElectiveCourse the professional course
     *
     * @param schoolElectiveCourse the professional course would be setSchoolElectiveCourse
     */
    public SchoolElectiveCourse setSchoolElectiveCourse(SchoolElectiveCourse schoolElectiveCourse) {
        return schoolElectiveCourseRepository.save(schoolElectiveCourse);
    }

    public void setSchoolElectiveCourse(int[] courseIdArray, Teacher teacher) {
        for (int courseId : courseIdArray) {
            SchoolElectiveCourse schoolElectiveCourse = this.getSchoolElectiveCourse(courseId);
            schoolElectiveCourse.setTeacherId(teacher.getTeacherId());
            this.setSchoolElectiveCourse(schoolElectiveCourse);
        }
    }

    public List<SchoolElectiveCourse> getSchoolElectiveCourse() {
        return schoolElectiveCourseRepository.findAll();
    }

    public SchoolElectiveCourse getSchoolElectiveCourse(int courseId) {
        return schoolElectiveCourseRepository.findByCourseId(courseId);
    }

    public List<SchoolElectiveCourse> getSchoolElectiveCourse(int[] courseIdArray) {
        List<Integer> courseIdList = new ArrayList<>();
        for (int courseId : courseIdArray) {
            courseIdList.add(courseId);
        }
        return schoolElectiveCourseRepository.findByCourseId(courseIdList);
    }

    public SchoolElectiveCourse[][] getSchoolElectiveCourse(List<SchoolElectiveCourseStudent> schoolElectiveCourseStudentList) {
        List<Integer> courseIdList = new ArrayList<>();
        for (SchoolElectiveCourseStudent schoolElectiveCourseStudent : schoolElectiveCourseStudentList) {
            courseIdList.add(schoolElectiveCourseStudent.getCourseId());
        }
        List<SchoolElectiveCourse> schoolElectiveCourseList = schoolElectiveCourseRepository.findByCourseId(courseIdList);

        SchoolElectiveCourse[][] schoolElectiveCourseArrayArray = new SchoolElectiveCourse[13][8];
        for (SchoolElectiveCourse schoolElectiveCourse : schoolElectiveCourseList) {
            int beginOrder = Integer.parseInt(schoolElectiveCourse.getCourseBeginOrder().toString().split("_")[1]);
            int endOrder = Integer.parseInt(schoolElectiveCourse.getCourseEndOrder().toString().split("_")[1]);
            int day = Integer.parseInt(schoolElectiveCourse.getCourseDay().toString().split("_")[1]);
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

/*    public List<List<SchoolElectiveCourse>> getSchoolElectiveCourse(SchoolElectiveCourse[][] schoolElectiveCourseArrayArray){
        List<List<SchoolElectiveCourse>> schoolElectiveCourseListList = new ArrayList<>();
        for(SchoolElectiveCourse[] schoolElectiveCourseArray : schoolElectiveCourseArrayArray){
            List<SchoolElectiveCourse> schoolElectiveCourseList = new ArrayList<>(Arrays.asList(schoolElectiveCourseArray));
            schoolElectiveCourseListList.add(schoolElectiveCourseList);
        }
        return  schoolElectiveCourseListList;
    }*/

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
                    courseLengthArrayArray[i][j] = schoolElectiveCourseLengthArrayArray[i][j];
                }
            }
        }
    }
}

