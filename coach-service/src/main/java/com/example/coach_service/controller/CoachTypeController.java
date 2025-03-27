package com.example.coach_service.controller;

import com.example.coach_service.dto.request.CoachTypeRequest;
import com.example.coach_service.service.CoachTypeService;
import com.example.coach_service.utils.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CoachTypeController {

    @Autowired
    private CoachTypeService coachTypeService;

    @GetMapping("/coach-type")
    public ResponseEntity<?> getAllCoachType(){
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachTypeService.getAllCoachType());
    }

    @GetMapping("/coach-type/{id}")
    public ResponseEntity<?> getCoachTypeById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachTypeService.getCoachTypeById(id));
    }

    @GetMapping("/coach-type/name/{name}")
    public ResponseEntity<?> getCoachTypeByName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachTypeService.getCoachTypeByName(name));
    }

    @GetMapping("/coach-type/type/{type}")
    public ResponseEntity<?> getAllCoachTypeByType(@PathVariable String type){
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachTypeService.getAllCoachTypeByType(Type.valueOf(type)));
    }

    @PostMapping("/coach-type")
    public ResponseEntity<?> addCoachType(@RequestBody CoachTypeRequest coachTypeRequest){
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachTypeService.addCoachType(coachTypeRequest));
    }
}
