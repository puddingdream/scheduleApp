package com.scheduleapp.schedul.service;

import com.scheduleapp.comment.dto.GetCommentsResponse;
import com.scheduleapp.comment.repository.CommentRepository;
import com.scheduleapp.exception.NullScheduleException;
import com.scheduleapp.exception.PasswordMissException;
import com.scheduleapp.schedul.repository.ScheduleRepository;
import com.scheduleapp.schedul.dto.*;
import com.scheduleapp.schedul.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    //생성
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        validateCreateScheduleRequest(request); // 생성 유저입력검증
        Schedule schedule = scheduleRepository.save(Schedule.from(request));
        return CreateScheduleResponse.from(schedule);
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public GetOneScheuleResponse getOneSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NullScheduleException("없는 스케줄 입니다."));

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
        validateUpdateScheduleRequest(request); // 유저입력 검증
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NullScheduleException("없는 스케줄 입니다.")
        );
        if (!schedule.getPassword().equals(password)) {
            throw new PasswordMissException("틀린 비밀번호 입니다.");
        }

        String title = request.getTitle() != null ? request.getTitle() : schedule.getTitle();
        String writer = request.getWriter() != null ? request.getWriter() : schedule.getWriter();

        schedule.update(title, writer);
        return UpdateScheduleResponse.from(schedule);
    }

    // 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId, DeleteScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NullScheduleException("없는 스케줄 입니다.")
        );
        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new PasswordMissException("틀린 비밀번호 입니다.");
        }
        commentRepository.deleteBySchedule_ScheduleId(scheduleId);
        scheduleRepository.delete(schedule);
    }


    //페이지 조회
    @Transactional(readOnly = true)
    public PageResponse<GetPageScheduleResponse> getPageSchedule(Pageable pageable) {
        Pageable fixed = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("modifiedAt").descending()
        );
        Page<Schedule> result = scheduleRepository.findAllByOrderByModifiedAtDesc(fixed);
        List<Long> ids = result.getContent().stream()
                .map(Schedule::getScheduleId)
                .toList();

        Map<Long, Long> countMap = ids.isEmpty()
                ? Map.of()
                : commentRepository.countByScheduleIds(ids).stream()
                .collect(Collectors.toMap(
                        ScheduleCommentCount::getScheduleId,
                        ScheduleCommentCount::getCount
                ));

        List<GetPageScheduleResponse> content = result.getContent().stream()
                .map(schedule -> GetPageScheduleResponse.of(
                        schedule,
                        Math.toIntExact(countMap.getOrDefault(schedule.getScheduleId(), 0L))
                ))
                .toList();

        return new PageResponse<>(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }


    // 생성 유저입력검증
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

    // 수정 유저입력검증
    private void validateUpdateScheduleRequest(UpdateScheduleRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청이 비어있습니다.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (request.getTitle() != null && request.getTitle().length() > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘길수 없습니다.");
        }
    }


}

