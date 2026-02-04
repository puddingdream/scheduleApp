package com.scheduleapp.controller;

import com.scheduleapp.dto.*;
import com.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<GetScheduleResponse> getOneschedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOneSchedule(scheduleId));
    }

    // 전체조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedules(
            @RequestParam(required = false) String writer
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedule(writer));
    }

    //
    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.ok(
                scheduleService.updateSchedule(scheduleId, request.getPassword(), request)
        );
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void>  deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
