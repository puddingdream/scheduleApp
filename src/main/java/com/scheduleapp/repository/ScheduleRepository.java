package com.scheduleapp.repository;

import com.scheduleapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByModifiedAtDesc(); // 조회시 수정날자정렬

    List<Schedule> findByWriterOrderByModifiedAtDesc(String writer); // 작성자조회시 수정날자 정렬
}
