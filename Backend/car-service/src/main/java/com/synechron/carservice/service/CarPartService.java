package com.synechron.carservice.service;

import com.synechron.carservice.dto.CarPartDTO;
import com.synechron.carservice.mapper.CarPartDTOMapper;
import com.synechron.carservice.model.CarPart;
import com.synechron.carservice.repository.CarPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarPartService {
    @Autowired
    private CarPartRepository carPartRepository;

    @Autowired
    private CarPartDTOMapper carPartDTOMapper;

    public Page<CarPartDTO> getAll(Pageable pageable) {
        Page<CarPart> carPartPage = carPartRepository.findAll(pageable);
        return carPartPage.map(model -> carPartDTOMapper.toDTO(model));
    }

    public CarPart getById(Long id){
        return carPartRepository.getReferenceById(id);
    }
}
