package com.homework.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.homework.travel.domain.entity.CityInfo;
import com.homework.travel.domain.response.ResponseInfo;
import com.homework.travel.service.CityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    CityService cityService;

    @PostMapping(value = "/city")
    public ResponseEntity<ResponseInfo> cityRegister(@RequestBody @Validated CityInfo cityInfo,
            BindingResult bindingResult) {

        System.out.println("!!!!!!!!");

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError objectError : errorList) {

                ResponseInfo response = ResponseInfo.builder()
                        .status("fail")
                        .message(objectError.getDefaultMessage())
                        .build();

                return ResponseEntity.internalServerError().body(response);

            }
        }

        return ResponseEntity.ok().body(cityService.cityRegister(cityInfo));

    }

    @PutMapping(value = { "/city/{id}", "/city" })
    public ResponseEntity<ResponseInfo> cityChange(@RequestBody @Validated CityInfo cityInfo,
            @PathVariable(name = "id") Optional<Long> cityId, BindingResult bindingResult) {

        if (!cityId.isPresent()) {
            ResponseInfo response = ResponseInfo.builder()
                    .status("fail")
                    .message("id not exist.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError objectError : errorList) {

                ResponseInfo response = ResponseInfo.builder()
                        .status("fail")
                        .message(objectError.getDefaultMessage())
                        .build();

                return ResponseEntity.internalServerError().body(response);

            }
        }

        return ResponseEntity.ok().body(cityService.cityChange(cityId.get(), cityInfo));
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<ResponseInfo> getCity(@PathVariable(name = "id") Optional<Long> cityId) {

        System.out.println("!!!!?????@@@");
        if (!cityId.isPresent()) {
            System.out.println("?????@@@");
            ResponseInfo response = ResponseInfo.builder()
                    .status("fail")
                    .message("id not exist.")
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok().body(cityService.getCity(cityId.get()));
    }

}
