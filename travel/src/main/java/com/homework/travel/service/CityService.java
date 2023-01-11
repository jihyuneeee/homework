package com.homework.travel.service;

import com.homework.travel.domain.entity.CityInfo;
import com.homework.travel.domain.response.ResponseInfo;

public interface CityService {

    public ResponseInfo cityRegister(CityInfo cityInfo);

    public ResponseInfo cityChange(Long id, CityInfo cityInfo);

    public ResponseInfo getCity(Long id);

}
