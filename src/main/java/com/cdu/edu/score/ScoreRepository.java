package com.cdu.edu.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/2/2 0002 下午 16:58
 * @since jdk 12.0.2
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    public Score findByCourseIdAndStudentId(int courseId, String studentId);

}
