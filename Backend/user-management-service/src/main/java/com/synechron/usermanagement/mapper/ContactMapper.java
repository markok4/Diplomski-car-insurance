package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.CityResponseDTO;
import com.synechron.usermanagement.dto.ContactCreateDTO;
import com.synechron.usermanagement.dto.ContactResponseDTO;
import com.synechron.usermanagement.model.City;
import com.synechron.usermanagement.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public Contact toCreateEntity(ContactCreateDTO dto){
        return Contact.builder()
                .email(dto.getEmail())
                .homePhone(dto.getHomePhone())
                .mobilePhone(dto.getMobilePhone())
                .isDeleted(dto.getIsDeleted())
                .build();
    }

    public Contact toResponseEntity(ContactResponseDTO dto){
        return Contact.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .homePhone(dto.getHomePhone())
                .mobilePhone(dto.getMobilePhone())
                .isDeleted(dto.getIsDeleted())
                .build();
    }

    public ContactResponseDTO toResponseDTO(Contact contact){
        return ContactResponseDTO.builder()
                .id(contact.getId())
                .email(contact.getEmail())
                .homePhone(contact.getHomePhone())
                .mobilePhone(contact.getMobilePhone())
                .isDeleted(contact.getIsDeleted())
                .build();
    }
}
