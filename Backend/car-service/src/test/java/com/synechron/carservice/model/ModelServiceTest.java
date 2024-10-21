package com.synechron.carservice.model;


import com.synechron.carservice.dto.ModelCreateDTO;
import com.synechron.carservice.dto.ModelDTO;
import com.synechron.carservice.dto.ModelUpdateDTO;
import com.synechron.carservice.mapper.CarDTOMapper;
import com.synechron.carservice.mapper.ModelDTOMapper;
import com.synechron.carservice.repository.CarRepository;
import com.synechron.carservice.repository.ModelRepository;
import com.synechron.carservice.service.CarService;
import com.synechron.carservice.service.ModelService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ModelServiceTest {

    @Mock
    ModelDTOMapper modelDTOMapper;

    @Mock
    ModelRepository modelRepository;

    @InjectMocks
    ModelService modelService;

    @Test
    public void whenGetAll_thenReturnsPageOfModelDTO() {
        Pageable pageable = Pageable.unpaged();
        Model model = new Model();
        ModelDTO dto = ModelDTO.builder().build();
        Page<Model> modelPage = new PageImpl<>(Collections.singletonList(model));

        given(modelRepository.findAll(pageable)).willReturn(modelPage);
        given(modelDTOMapper.toDTO(any(Model.class))).willReturn(dto);

        Page<ModelDTO> result = modelService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertSame(dto, result.getContent().get(0));
    }

    @Test
    public void whenDelete_thenSetsIsDeletedTrue() {
        Long modelId = 1L;
        Model model = new Model();
        given(modelRepository.findById(modelId)).willReturn(Optional.of(model));

        modelService.delete(modelId);

        assertTrue(model.getIsDeleted());
        verify(modelRepository).save(model);
    }

    @Test
    public void whenDeleteNonExistingModel_thenThrowsEntityNotFoundException() {
        Long modelId = 1L;
        when(modelRepository.findById(modelId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> modelService.delete(modelId),
                "Expected delete to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Model with id " + modelId + " not found"));
    }

    @Test
    public void whenRestore_thenSetsIsDeletedFalse() {
        Long modelId = 1L;
        Model model = new Model();
        model.setIsDeleted(true);
        given(modelRepository.findById(modelId)).willReturn(Optional.of(model));

        modelService.restore(modelId);

        assertFalse(model.getIsDeleted());
        verify(modelRepository).save(model);
    }

    @Test
    public void whenRestoreNonExistingModel_thenThrowsEntityNotFoundException() {
        Long modelId = 1L;
        when(modelRepository.findById(modelId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> modelService.restore(modelId),
                "Expected restore to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Model with id " + modelId + " not found"));
    }

    @Test
    public void whenCreate_thenReturnsModel() {
        ModelCreateDTO modelCreateDTO = ModelCreateDTO.builder().build();
        Model model = new Model();
        given(modelDTOMapper.fromCreateDTO(any(ModelCreateDTO.class))).willReturn(model);
        given(modelRepository.save(any(Model.class))).willReturn(model);

        Model result = modelService.create(modelCreateDTO);

        assertNotNull(result);
        assertSame(model, result);
    }

    @Test
    public void whenUpdate_thenReturnsUpdatedModel() {
        Long modelId = 1L;
        ModelUpdateDTO modelDTO = ModelUpdateDTO.builder().build();
        modelDTO.setName("New Name");
        Model model = new Model();
        given(modelRepository.findById(modelId)).willReturn(Optional.of(model));
        given(modelRepository.save(any(Model.class))).willReturn(model);

        Model result = modelService.update(modelId, modelDTO);

        assertNotNull(result);
        assertEquals("New Name", model.getName());
        assertSame(model, result);
    }

    @Test
    public void whenUpdateNonExistingModel_thenThrowsEntityNotFoundException() {
        Long modelId = 1L;
        ModelUpdateDTO modelDTO = mock(ModelUpdateDTO.class);
        when(modelRepository.findById(modelId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> modelService.update(modelId, modelDTO),
                "Expected update to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Model with id " + modelId + " not found"));
    }

    @Test
    public void whenGetByIdNonExistingModel_thenThrowsEntityNotFoundException() {
        Long modelId = 1L;
        when(modelRepository.findById(modelId)).thenReturn(Optional.empty());

        Optional<ModelDTO> result = modelService.getById(modelId);

        assertTrue(result.isEmpty());
    }
}
