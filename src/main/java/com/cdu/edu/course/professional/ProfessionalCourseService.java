package com.cdu.edu.course.professional;

import com.cdu.edu.course.professional.classes.ProfessionalCourseClass;
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
public class ProfessionalCourseService {

    @Autowired
    private ProfessionalCourseRepository professionalCourseRepository;

    /**
     * description: setProfessionalCourse the professional course
     *
     * @param professionalCourse the professional course would be setProfessionalCourse
     */
    public ProfessionalCourse setProfessionalCourse(ProfessionalCourse professionalCourse) {
        return professionalCourseRepository.save(professionalCourse);
    }

    public void setProfessionalCourse(int[] courseIdArray, Teacher teacher) {
        for (int courseId : courseIdArray) {
            ProfessionalCourse professionalCourse = this.getProfessionalCourse(courseId);
            professionalCourse.setTeacherId(teacher.getTeacherId());
            this.setProfessionalCourse(professionalCourse);
        }
    }

    public List<ProfessionalCourse> getProfessionalCourse() {
        return professionalCourseRepository.findAll();
    }

    public ProfessionalCourse getProfessionalCourse(int courseId) {
        return professionalCourseRepository.findByCourseId(courseId);
    }

    public List<ProfessionalCourse> getProfessionalCourse(int[] courseIdArray) {
        List<Integer> courseIdList = new ArrayList<>();
        for (int courseId : courseIdArray) {
            courseIdList.add(courseId);
        }
        return professionalCourseRepository.findByCourseIdIn(courseIdList);
    }

    public List<ProfessionalCourse> getProfessionalCourse(List<ProfessionalCourseClass> professionalCourseClassList) {
        List<Integer> courseIdList = new ArrayList<>();
        for (ProfessionalCourseClass professionalCourseClass : professionalCourseClassList) {
            courseIdList.add(professionalCourseClass.getCourseId());
        }
        return professionalCourseRepository.findByCourseIdIn(courseIdList);
    }

    public List<ProfessionalCourse> getProfessionalCourse(Teacher teacher) {
        return professionalCourseRepository.findByTeacherId(teacher.getTeacherId());
    }

    /*public void setProfessionalCourse(AbstractCourse[][] courseArrayArray,
                                      ProfessionalCourse[][]
                                      professionalCourseArrayArray) {
        for(int i = 0; i < professionalCourseArrayArray.length; i++){
            for(int j = 0; j < professionalCourseArrayArray[i].length; j++){
                if(professionalCourseArrayArray[i][j] != null){
                    courseArrayArray[i][j] = professionalCourseArrayArray[i][j];
                }
            }
        }
    }*/


/*    public List<List<ProfessionalCourse>> getProfessionalCourse
(ProfessionalCourse[][] professionalCourseArrayArray){
        List<List<ProfessionalCourse>> professionalCourseListList = new ArrayList<>();
        for(ProfessionalCourse[] professionalCourseArray : professionalCourseArrayArray){
            List<ProfessionalCourse> professionalCourseList = new ArrayList<>(Arrays
            .asList(professionalCourseArray));
            professionalCourseListList.add(professionalCourseList);
        }
        return  professionalCourseListList;
    }*/

    /*public int[][] getProfessionalCourseLength(ProfessionalCourse[][]
    professionalCourseArrayArray) {
        int[][] professionalCourseLengthArrayArray = new int[13][8];
        for (int i = 1; i < professionalCourseArrayArray.length; i++) {
            for (int j = 1; j < professionalCourseArrayArray[i].length; j++) {
                if (professionalCourseArrayArray[i][j] != null) {
                    professionalCourseLengthArrayArray[i][j] =
                            Integer.parseInt(professionalCourseArrayArray[i][j]
                            .getCourseEndOrder().toString().split("_")[1]) - Integer
                            .parseInt(professionalCourseArrayArray[i][j]
                            .getCourseBeginOrder().toString().split("_")[1]) + 1;
                }
            }
        }
        return professionalCourseLengthArrayArray;
    }*/

    /*public void setProfessionalCourseLength(int[][] courseLengthArrayArray,
                                            int[][] professionalCourseLengthArrayArray) {
        for(int i = 0; i < professionalCourseLengthArrayArray.length; i++){
            for(int j = 0; j < professionalCourseLengthArrayArray[i].length; j++){
                if(professionalCourseLengthArrayArray[i][j] != 0){
                    courseLengthArrayArray[i][j] =
                    professionalCourseLengthArrayArray[i][j];
                }
            }
        }
    }*/

}

