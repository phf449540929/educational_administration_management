package com.cdu.edu.score;

import com.cdu.edu.course.AbstractCourse;
import com.cdu.edu.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/2/2 0002 下午 16:36
 * @since jdk 12.0.2
 */
@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public void setScore(List<Score> scoreList) {
        scoreRepository.saveAll(scoreList);
    }

    public List<Score> getScore(AbstractCourse course, List<Student> studentList,
                                Queue<String> rollScoreQueue, Queue<String> usualScoreQueue,
                                Queue<String> experimentalScoreQueue) {
        List<Score> scoreList = new ArrayList<>(studentList.size());
        int courseId = course.getCourseId();

        for (Student student : studentList) {
            String studentId = student.getStudentId();
            assert !rollScoreQueue.isEmpty();
            double rollScore = Double.parseDouble(rollScoreQueue.poll());
            assert !usualScoreQueue.isEmpty();
            double usualScore = Double.parseDouble(usualScoreQueue.poll());
            assert !experimentalScoreQueue.isEmpty();
            double experimentalScore = Double.parseDouble(experimentalScoreQueue.poll());
            double totalScore =
                    course.getRollRatio() * rollScore + course.getUsualRatio() * usualScore + course.getExperimentalRatio() * experimentalScore;
            scoreList.add(new Score(courseId, studentId, rollScore, usualScore, experimentalScore
                    , totalScore));
        }
        return scoreList;
    }

    public List<Score> getScore(AbstractCourse course, List<Student> studentList) {
        List<Score> scoreList = new ArrayList<>();
        for (Student student : studentList) {
            scoreList.add(scoreRepository.findByCourseIdAndStudentId(course.getCourseId(),
                    student.getStudentId()));
        }
        return scoreList;
    }

    public List<Score> getScore(Student student, List<AbstractCourse> courseList) {
        List<Score> scoreList = new ArrayList<>(courseList.size());
        for (AbstractCourse course : courseList) {
            scoreList.add(scoreRepository.findByCourseIdAndStudentId(course.getCourseId(),
                    student.getStudentId()));
        }
        return scoreList;
    }
}
