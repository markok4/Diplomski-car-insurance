package com.synechron.paymentservice.mapper;
import java.util.List;

public interface IMapper <Entity, DTO>{
    Entity toEntity(DTO dto);
    DTO toDTO(Entity entity);
    List<DTO> listToDTO(List<Entity> entities);
}