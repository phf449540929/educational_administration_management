package com.cdu.edu.score;

import javax.persistence.*;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/2/2 0002 下午 17:07
 * @since jdk 12.0.2
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Score {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int scoreId;
    @Column(nullable = false)
    private int courseId;
    @Column(nullable = false)
    private String studentId;
    @Column(nullable = false)
    private double rollScore;
    @Column(nullable = false)
    private double usualScore;
    @Column(nullable = false)
    private double experimentalScore;
    @Column(nullable = false)
    private double totalScore;

    public Score() {
    }

    public Score(int courseId, String studentId, double rollScore, double usualScore,
                 double experimentalScore, double totalScore) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.rollScore = rollScore;
        this.usualScore = usualScore;
        this.experimentalScore = experimentalScore;
        this.totalScore = totalScore;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getRollScore() {
        return rollScore;
    }

    public void setRollScore(int rollScore) {
        this.rollScore = rollScore;
    }

    public double getUsualScore() {
        return usualScore;
    }

    public void setUsualScore(int usualScore) {
        this.usualScore = usualScore;
    }

    public double getExperimentalScore() {
        return experimentalScore;
    }

    public void setExperimentalScore(int experimentalScore) {
        this.experimentalScore = experimentalScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
