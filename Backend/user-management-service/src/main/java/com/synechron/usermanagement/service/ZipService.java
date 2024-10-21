package com.synechron.usermanagement.service;

import com.synechron.usermanagement.dto.ZipResponseDTO;
import com.synechron.usermanagement.mapper.ZipMapper;
import com.synechron.usermanagement.model.Zip;
import com.synechron.usermanagement.repository.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ZipService {
    @Autowired
    private ZipRepository repository;
    @Autowired
    private ZipMapper mapper;

    public Page<ZipResponseDTO> getByCity(Pageable pageable, Long cityid){
        Page<Zip> zipPage = repository.findAllByCityId(cityid, pageable);
        return zipPage.map(zip->mapper.toResponseDTO(zip));
    }
}
