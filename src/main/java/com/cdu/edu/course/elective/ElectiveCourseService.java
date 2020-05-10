package com.cdu.edu.course.elective;

import com.cdu.edu.course.elective.student.ElectiveCourseStudent;
import com.cdu.edu.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 选修课的Service层
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/11/2 0002 上午 11:53
 * @since jdk 10.0.1
 */
@Service
public class ElectiveCourseService {

    @Autowired
    private ElectiveCourseRepository electiveCourseRepository;

    /**
     * description: 增加选修课
     *
     * @param electiveCourse 需要被增加的选修课
     */
    public void setSchoolElectiveCourse(ElectiveCourse electiveCourse) {
        electiveCourseRepository.save(electiveCourse);
    }

    /**
     * description: 为教师选择的选修课添加教师信息
     *
     * @param courseIdArray 教师选择的选修课的courseId数组
     * @param teacher       教师
     */
    public void setSchoolElectiveCourse(int[] courseIdArray, Teacher teacher) {
        for (int courseId : courseIdArray) {
            ElectiveCourse electiveCourse = this.getElectiveCourse(courseId);
            electiveCourse.setTeacherId(teacher.getTeacherId());
            this.setSchoolElectiveCourse(electiveCourse);
        }
    }

    /**
     * description: 获取所有选修课
     *
     * @return java.util.List<E> 所有的选修课
     */
    public List<ElectiveCourse> getElectiveCourse() {
        return electiveCourseRepository.findAll();
    }

    /**
     * description: 通过courseId寻找选修课
     *
     * @param courseId 选修课的courseId
     * @return com.cdu.edu.course.elective.ElectiveCourse 寻找到的选修课
     */
    public ElectiveCourse getElectiveCourse(int courseId) {
        return electiveCourseRepository.findByCourseIdIn(courseId);
    }

    /**
     * description: 通过courseId数组寻找选修课
     *
     * @param courseIdArray courseId数组
     * @return java.util.List<E> 寻找到的选修课的List
     */
    public List<ElectiveCourse> getElectiveCourse(int[] courseIdArray) {
        List<Integer> courseIdList = new ArrayList<>();
        for (int courseId : courseIdArray) {
            courseIdList.add(courseId);
        }
        return electiveCourseRepository.findByCourseIdIn(courseIdList);
    }

    /**
     * get
     *
     * @param electiveCourseStudentList
     * @return
     */
    public List<ElectiveCourse> getElectiveCourse(List<ElectiveCourseStudent> electiveCourseStudentList) {
        List<Integer> courseIdList = new ArrayList<>();
        for (ElectiveCourseStudent electiveCourseStudent : electiveCourseStudentList) {
            courseIdList.add(electiveCourseStudent.getCourseId());
        }
        return electiveCourseRepository.findByCourseIdIn(courseIdList);
    }

    public List<ElectiveCourse> getElectiveCourse(Teacher teacher) {
        return electiveCourseRepository.findByTeacherId(teacher.getTeacherId());
    }

    /*public void setSchoolElectiveCourse(AbstractCourse[][] courseArrayArray,
                                        SchoolElectiveCourse[][]
                                        schoolElectiveCourseArrayArray) {
        for (int i = 0; i < schoolElectiveCourseArrayArray.length; i++) {
            for (int j = 0; j < schoolElectiveCourseArrayArray[i].length; j++) {
                if (schoolElectiveCourseArrayArray[i][j] != null) {
                    courseArrayArray[i][j] = schoolElectiveCourseArrayArray[i][j];
                }
            }
        }
    }*/

/*    public List<List<SchoolElectiveCourse>> getSchoolElectiveCourse
(SchoolElectiveCourse[][] schoolElectiveCourseArrayArray){
        List<List<SchoolElectiveCourse>> schoolElectiveCourseListList = new ArrayList<>();
        for(SchoolElectiveCourse[] schoolElectiveCourseArray :
        schoolElectiveCourseArrayArray){
            List<SchoolElectiveCourse> schoolElectiveCourseList = new ArrayList<>
            (Arrays.asList(schoolElectiveCourseArray));
            schoolElectiveCourseListList.add(schoolElectiveCourseList);
        }
        return  schoolElectiveCourseListList;
    }*/

    /*public int[][] getSchoolElectiveCourseLength(SchoolElectiveCourse[][]
    schoolElectiveCourseArrayArray) {
        int[][] schoolElectiveCourseLengthArray = new int[13][8];
        for (int i = 1; i < schoolElectiveCourseArrayArray.length; i++) {
            for (int j = 1; j < schoolElectiveCourseArrayArray[i].length; j++) {
                if (schoolElectiveCourseArrayArray[i][j] != null) {
                    schoolElectiveCourseLengthArray[i][j] =
                            Integer.parseInt(schoolElectiveCourseArrayArray[i][j]
                            .getCourseEndOrder().toString().split("_")[1]) - Integer
                            .parseInt(schoolElectiveCourseArrayArray[i][j]
                            .getCourseBeginOrder().toString().split("_")[1]) + 1;
                }
            }
        }
        return schoolElectiveCourseLengthArray;
    }*/

    /*public void setSchoolElectiveCourseLength(int[][] courseLengthArrayArray,
                                              int[][]
                                              schoolElectiveCourseLengthArrayArray) {
        for (int i = 0; i < schoolElectiveCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < schoolElectiveCourseLengthArrayArray[i].length; j++) {
                if (schoolElectiveCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] =
                    schoolElectiveCourseLengthArrayArray[i][j];
                }
            }
        }
    }*/
}

