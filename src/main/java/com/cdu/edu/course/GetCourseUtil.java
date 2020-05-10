package com.cdu.edu.course;

import com.cdu.edu.score.Score;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * description: 将课程List进行修改的工具类
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/3/15 0015 下午 17:44
 * @since jdk 12.0.2
 */
@Component
public class GetCourseUtil {

    /**
     * description: 从课程List去除还没有被教师录入成绩的课程
     *
     * @param courseList 课程List
     * @param scoreList  分数List
     * @return java.util.List<E> 经过修改之后的课程List
     */
    public List<AbstractCourse> getCourse(List<AbstractCourse> courseList, List<Score> scoreList){

        Iterator<AbstractCourse> iterator=courseList.iterator();
        while (iterator.hasNext()){
            AbstractCourse course = iterator.next();

            boolean flag = false;
            for(Score score : scoreList){
                if (score.getCourseId() == course.getCourseId()){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                iterator.remove();
            }
        }
        /*for(AbstractCourse course : courseList){
            boolean flag = false;
            for(Score score : scoreList){
                if (score.getCourseId() == course.getCourseId()){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                courseList.remove(course);
            }
        }*/
        return courseList;
    }

}
