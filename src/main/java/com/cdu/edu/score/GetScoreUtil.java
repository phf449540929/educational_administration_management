package com.cdu.edu.score;

import com.cdu.edu.course.AbstractCourse;
import com.cdu.edu.student.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/3/15 0015 下午 23:55
 * @since jdk 12.0.2
 */
@Component
public class GetScoreUtil {

    public List<Score> getScore(AbstractCourse course, List<Student> studentList,
                                Queue<Double> rollScoreQueue, Queue<Double> usualScoreQueue,
                                Queue<Double> experimentalScoreQueue) {
        List<Score> scoreList = new ArrayList<>(studentList.size());
        int courseId = course.getCourseId();

        for (Student student : studentList) {
            String studentId = student.getStudentId();

            assert !rollScoreQueue.isEmpty();
            double rollScore = rollScoreQueue.poll();

            assert !usualScoreQueue.isEmpty();
            double usualScore = usualScoreQueue.poll();

            assert !experimentalScoreQueue.isEmpty();
            double experimentalScore = experimentalScoreQueue.poll();

            double totalScore =
                    course.getRollRatio() * rollScore + course.getUsualRatio() * usualScore + course.getExperimentalRatio() * experimentalScore;
            scoreList.add(new Score(courseId, studentId, rollScore, usualScore, experimentalScore
                    , totalScore));
        }
        return scoreList;
    }

}
