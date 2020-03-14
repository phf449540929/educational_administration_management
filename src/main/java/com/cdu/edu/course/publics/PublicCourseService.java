package com.cdu.edu.course.publics;

import com.cdu.edu.course.publics.classes.PublicCourseClass;
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
public class PublicCourseService {

    @Autowired
    private PublicCourseRepository publicCourseRepository;

    /**
     * description: setPublicCourse the professional course
     *
     * @param publicCourse the professional course would be setPublicCourse
     */
    public PublicCourse setPublicCourse(PublicCourse publicCourse) {
        return publicCourseRepository.save(publicCourse);
    }

    public void setPublicCourse(int[] courseIdArray, Teacher teacher) {
        for (int courseId : courseIdArray) {
            PublicCourse publicCourse = this.getPublicCourse(courseId);
            publicCourse.setTeacherId(teacher.getTeacherId());
            this.setPublicCourse(publicCourse);
        }
    }

    public List<PublicCourse> getPublicCourse() {
        return publicCourseRepository.findAll();
    }

    public PublicCourse getPublicCourse(int courseId) {
        return publicCourseRepository.findByCourseId(courseId);
    }

    public List<PublicCourse> getPublicCourse(int[] courseIdArray) {
        List<Integer> courseIdList = new ArrayList<>();
        for (int courseId : courseIdArray) {
            courseIdList.add(courseId);
        }
        return publicCourseRepository.findByCourseId(courseIdList);
    }

    public List<PublicCourse> getPublicCourse(List<PublicCourseClass> publicCourseClassList) {
        List<Integer> courseIdList = new ArrayList<>();
        for (PublicCourseClass publicCourseClass : publicCourseClassList) {
            courseIdList.add(publicCourseClass.getCourseId());
        }
        return publicCourseRepository.findByCourseId(courseIdList);
    }

    public List<PublicCourse> getPublicCourse(Teacher teacher) {
        return publicCourseRepository.findByTeacherId(teacher.getTeacherId());
    }

    /*public void setPublicCourse(AbstractCourse[][] courseArrayArray, PublicCourse[][]
     publicCourseArrayArray) {
        for (int i = 0; i < publicCourseArrayArray.length; i++) {
            for (int j = 0; j < publicCourseArrayArray[i].length; j++) {
                if (publicCourseArrayArray[i][j] != null) {
                    courseArrayArray[i][j] = publicCourseArrayArray[i][j];
                }
            }
        }
    }*/


/*    public List<List<PublicCourse>> getPublicCourse(PublicCourse[][]
PublicCourseArrayArray){
        List<List<PublicCourse>> PublicCourseListList = new ArrayList<>();
        for(PublicCourse[] PublicCourseArray : PublicCourseArrayArray){
            List<PublicCourse> PublicCourseList = new ArrayList<>(Arrays.asList
            (PublicCourseArray));
            PublicCourseListList.add(PublicCourseList);
        }
        return  PublicCourseListList;
    }*/

    /*public int[][] getPublicCourseLength(PublicCourse[][] publicCourseArrayArray) {
        int[][] publicCourseLengthArrayArray = new int[13][8];
        for (int i = 1; i < publicCourseArrayArray.length; i++) {
            for (int j = 1; j < publicCourseArrayArray[i].length; j++) {
                if (publicCourseArrayArray[i][j] != null) {
                    publicCourseLengthArrayArray[i][j] =
                            Integer.parseInt(publicCourseArrayArray[i][j]
                            .getCourseEndOrder().toString().split("_")[1]) - Integer
                            .parseInt(publicCourseArrayArray[i][j].getCourseBeginOrder
                            ().toString().split("_")[1]) + 1;
                }
            }
        }
        return publicCourseLengthArrayArray;
    }*/

    /*public void setPublicCourseLength(int[][] courseLengthArrayArray,
                                      int[][] publicCourseLengthArrayArray) {
        for (int i = 0; i < publicCourseLengthArrayArray.length; i++) {
            for (int j = 0; j < publicCourseLengthArrayArray[i].length; j++) {
                if (publicCourseLengthArrayArray[i][j] != 0) {
                    courseLengthArrayArray[i][j] = publicCourseLengthArrayArray[i][j];
                }
            }
        }
    }*/

}

