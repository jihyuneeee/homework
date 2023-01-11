package com.homework.travel.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.homework.travel.domain.entity.City;
import com.homework.travel.service.CityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
public class CityApiController {

    @Autowired 
    CityService cityService;

    /**
     * 도시 등록 API
     */
    @PostMapping("/api/city")
    public CreateCityResponse saveCity(@RequestBody @Valid CreateCityRequest requset){

        City city = new City();
        city.setCityname(requset.getCityname());

        Long id = cityService.register(city);
        return new CreateCityResponse(id);

    }

    /**
     * 도시 수정 API
     */
    @PutMapping("/api/city/{id}")
    public UpdateCityResponse updateCity(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
        cityService.update(id, request.getCityname());
        City findCity = cityService.findOne(id);
        return new UpdateCityResponse(findCity.getId(), findCity.getCityname());
    }

    /**
     * 단일 도시 조회 API
     */
    @GetMapping("/api/city/{id}")
    public CityDto selectCity(@PathVariable("id") Long id){
        
        City city = cityService.findOne(id);
        CityDto dto = new CityDto(city.getCityname()); // Entity 직접 노출을 피하기 위해 

        return dto;

    }

    @Data
    @AllArgsConstructor
    static class CityDto {
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
        private String cityname;
    }

    @Data
    @AllArgsConstructor
    static class UpdateCityResponse{

        private Long id;

        @NotBlank(message = "cityname is a required value.")
        private String cityname;
    }
    
    @Data
    static class CreateCityRequest {
        @NotBlank(message = "cityname is a required value.")
        private String cityname;
    }

    @Data
    static class CreateCityResponse {
        private Long id;

        public CreateCityResponse(Long id) {
            this.id = id;
        }
    }
}
