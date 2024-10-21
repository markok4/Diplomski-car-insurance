package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.ZipResponseDTO;
import com.synechron.usermanagement.model.Zip;
import org.springframework.stereotype.Component;

@Component
public class ZipMapper {
    public ZipResponseDTO toResponseDTO(Zip zip){
        return ZipResponseDTO.builder()
                .id(zip.getId())
                .zipNumber(zip.getZipNumber())
                .isDeleted(zip.getIsDeleted())
                .build();
    }
}
