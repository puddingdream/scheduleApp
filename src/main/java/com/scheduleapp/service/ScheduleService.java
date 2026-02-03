package com.scheduleapp.service;

import com.scheduleapp.dto.CreateScheduleRequest;
import com.scheduleapp.dto.CreateScheduleResponse;
import com.scheduleapp.dto.GetScheduleResponse;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = scheduleRepository.save(new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getWriter(),
                request.getPassword()));
        return new CreateScheduleResponse(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOneSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄 입니다.")
        );
        return new GetScheduleResponse(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();

        List<GetScheduleResponse> dtos = schedules.stream()
                .map(dto -> new GetScheduleResponse(
                        dto.getScheduleId(),
                        dto.getTitle(),
                        dto.getContent(),
                        dto.getWriter(),
                        dto.getCreatedAt(),
                        dto.getModifiedAt()
                )).toList();
        return dtos;
    }


}

