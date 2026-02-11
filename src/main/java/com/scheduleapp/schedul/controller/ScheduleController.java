package com.scheduleapp.schedul.controller;

import com.scheduleapp.schedul.service.ScheduleService;
import com.scheduleapp.schedul.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "스케줄", description = "일정 관리 API")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 생성
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }

    // 단건조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetOneScheuleResponse> getOneschedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOneSchedule(scheduleId));
    }

    // 전체조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedules(
            @RequestParam(required = false) String writer
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedule(writer));
    }

    //수정
    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.ok(
                scheduleService.updateSchedule(scheduleId, request.getPassword(), request)
        );
    }

    //삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void>  deleteSchedule(@PathVariable Long scheduleId, @RequestBody DeleteScheduleRequest request) {
        scheduleService.deleteSchedule(scheduleId,request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
