package com.homework.travel.api;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.homework.travel.domain.entity.Travel;
import com.homework.travel.service.TravelService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class TravelApiController {
    
    @Autowired
    TravelService travelService;

    /**
     * 여행 등록 API
     */
    @PostMapping("/api/travel")
    public CreateTravelResponse saveCity(@RequestBody @Valid CreateTravelRequest requset){

        Long id = travelService.plan(requset.getCityId(), requset.getUserId(), requset.getStartDate(), requset.getEndDate());
        return new CreateTravelResponse(id);

    }

    @Data
    static class CreateTravelRequest {
        
        private Long cityId;

        private Long userId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endDate;
    }

    @Data
    static class CreateTravelResponse {
       
        private Long id;

        public CreateTravelResponse(Long id) {
            this.id = id;
        }
    }
}

