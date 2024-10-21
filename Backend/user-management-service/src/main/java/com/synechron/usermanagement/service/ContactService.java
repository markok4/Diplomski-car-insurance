package com.synechron.usermanagement.service;

import com.synechron.usermanagement.dto.ContactCreateDTO;
import com.synechron.usermanagement.dto.ContactResponseDTO;
import com.synechron.usermanagement.mapper.AddressMapper;
import com.synechron.usermanagement.mapper.ContactMapper;
import com.synechron.usermanagement.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepository repository;
    @Autowired
    private ContactMapper mapper;

    public ContactResponseDTO create(ContactCreateDTO dto){
        try{
            if (repository.existsByEmail(dto.getEmail())) {
                throw new IllegalStateException("Email already in use.");
            }

            return mapper.toResponseDTO(repository.save(mapper.toCreateEntity(dto)));
        } catch(Error e){
            System.out.println(e);
            throw e;
        }
    }
}
