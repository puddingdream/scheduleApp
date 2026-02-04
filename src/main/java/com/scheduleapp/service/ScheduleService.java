package com.scheduleapp.service;

import com.scheduleapp.dto.*;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.CommentRepository;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    //생성
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        validateCreateScheduleRequest(request);
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

    // 단건 조회
    @Transactional(readOnly = true)
    public GetOneScheuleResponse getOneSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄 입니다."));

        List<GetCommentsResponse> comments =
                commentRepository.findBySchedule_ScheduleId(scheduleId)
                .stream()
                .map(GetCommentsResponse::from)
                .toList();

        return GetOneScheuleResponse.from(schedule, comments);
    }

    // 전체조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAllSchedule(String writer) {
        if (writer == null || writer.isBlank()) {
            List<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
            return schedules.stream()
                    .map(GetScheduleResponse::from).toList();
        }
        List<Schedule> schedules = scheduleRepository.findByWriterOrderByModifiedAtDesc(writer);
        return schedules.stream()
                .map(GetScheduleResponse::from).toList();
    }


    // 수정
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, String password, UpdateScheduleRequest request) {
        validateUpdateScheduleRequest(request);
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄 입니다.")
        );
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("틀린 비밀번호 입니다.");
        }
        String title = request.getTitle() != null ? request.getTitle() : schedule.getTitle();
        String writer = request.getWriter() != null ? request.getWriter() : schedule.getWriter();

        schedule.update(title, writer);
        return UpdateScheduleResponse.from(schedule);
    }

    // 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId,DeleteScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄 입니다.")
        );
        if(!schedule.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("틀린 비밀번호 입니다.");
        }
        scheduleRepository.delete(schedule);
    }

    private void validateCreateScheduleRequest(CreateScheduleRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청이 비어있습니다.");
        }
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (request.getTitle().length() > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘길수 없습니다.");
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        if (request.getContent().length() > 200) {
            throw new IllegalArgumentException("내용 200자를 넘길수 없습니다.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (request.getWriter() == null || request.getWriter().isBlank()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }
    }

    private void validateUpdateScheduleRequest(UpdateScheduleRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청이 비어있습니다.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (request.getTitle() != null) {
            if (request.getTitle().isBlank()) {
                throw new IllegalArgumentException("제목은 필수입니다.");
            }
            if (request.getTitle().length() > 30) {
                throw new IllegalArgumentException("제목은 30자를 넘길수 없습니다.");
            }
        }
        if (request.getWriter() != null && request.getWriter().isBlank()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }
    }

}

