package com.synechron.usermanagement.service;

import com.synechron.usermanagement.dto.AddressResponseDTO;
import com.synechron.usermanagement.mapper.AddressMapper;
import com.synechron.usermanagement.model.Address;
import com.synechron.usermanagement.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;
    @Autowired
    private AddressMapper mapper;

    public Page<AddressResponseDTO> getByCity(Pageable pageable, Long cityId){
        Page<Address> addressPage = repository.findAllByCityId(cityId, pageable);
        return addressPage.map(address->mapper.toResponseDTO(address));
    }

    public AddressResponseDTO getById(Long id){
        try{
            Optional<Address> result = repository.findById(id);
            if (result.isPresent()) {
                return mapper.toResponseDTO(result.get());
            } else {
                throw new EntityNotFoundException("Entity does not exist");
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }
}
