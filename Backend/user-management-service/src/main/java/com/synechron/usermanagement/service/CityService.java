package com.synechron.usermanagement.service;

import com.synechron.usermanagement.dto.CityResponseDTO;
import com.synechron.usermanagement.mapper.CityMapper;
import com.synechron.usermanagement.model.City;
import com.synechron.usermanagement.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;
    @Autowired
    private CityMapper mapper;

    public Page<CityResponseDTO> getByCountry(Pageable pageable, Long countryId){
        Page<City> cityPage = repository.findAllByCountryId(countryId, pageable);
        return cityPage.map(city->mapper.toResponseDTO(city));
    }
}
