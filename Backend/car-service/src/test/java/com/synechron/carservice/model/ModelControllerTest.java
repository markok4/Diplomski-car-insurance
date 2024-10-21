package com.synechron.carservice.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.synechron.carservice.config.ModelControllerTestContextConfiguration;
import com.synechron.carservice.controller.ModelController;
import com.synechron.carservice.dto.BrandDTO;
import com.synechron.carservice.dto.ModelCreateDTO;
import com.synechron.carservice.dto.ModelDTO;
import com.synechron.carservice.dto.ModelUpdateDTO;
import com.synechron.carservice.mapper.ModelDTOMapper;
import com.synechron.carservice.service.ModelService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ModelController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(ModelControllerTestContextConfiguration.class)
class ModelControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ModelService modelService;

    @Autowired
    ModelDTOMapper modelDTOMapper;

    @Test
    @WithAnonymousUser()
    void whenGetAllModels_thenReturnsPageOfModelDTO() throws Exception {
        List<Model> models = getModels();
        List<ModelDTO> modelDTOS = models.stream().map(model -> modelDTOMapper.toDTO(model)).toList();
        Page<ModelDTO> pageOfCountries = new PageImpl<>(modelDTOS);

        Mockito.when(modelService.getAll(Pageable.ofSize(10))).thenReturn(pageOfCountries);

        mockMvc.perform(get("/models")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("content").exists(),
                        jsonPath("content").isArray(),
                        jsonPath("content", hasSize(models.size())),
                        jsonPath("content[0].id").value(models.get(0).getId()),
                        jsonPath("content[1].id").value(models.get(1).getId()),
                        jsonPath("content[2].id").value(models.get(2).getId())
                );
    }

    List<Model> getModels() {
        return List.of(
                new Model(1L, "Model1", false, LocalDate.of(2012, Month.JANUARY, 12).atStartOfDay(), new ArrayList<>(), new Brand()),
                new Model(2L, "Model2", false, LocalDate.of(2012, Month.JANUARY, 12).atStartOfDay(), new ArrayList<>(), new Brand()),
                new Model(3L, "Model3", false,LocalDate.of(2012, Month.JANUARY, 12).atStartOfDay(), new ArrayList<>(), new Brand())
        );
    }

    @Test
    public void whenDeleteExistingModel_thenStatusOk() throws Exception {
        Long modelId = 1L;
        doNothing().when(modelService).delete(modelId);

        mockMvc.perform(delete("/models/{id}", modelId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully deleted")));
    }

    @Test
    public void whenDeleteNonExistentModel_thenStatusNotFound() throws Exception {
        Long modelId = 1L;
        doThrow(new EntityNotFoundException("Model not found")).when(modelService).delete(modelId);

        mockMvc.perform(delete("/models/{id}", modelId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Model not found")));

    }

    @Test
    public void whenRestoreExistingModel_thenStatusOk() throws Exception {
        Long modelId = 1L;
        doNothing().when(modelService).restore(modelId);

        mockMvc.perform(patch("/models/{id}", modelId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    public void whenRestoreNonExistentModel_thenStatusNotFound() throws Exception {
        Long modelId = 1L;
        doThrow(new EntityNotFoundException("")).when(modelService).restore(modelId);

        mockMvc.perform(patch("/models/{id}", modelId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void whenCreateModel_thenStatusCreated() throws Exception {
        ModelCreateDTO modelCreateDTO = ModelCreateDTO.builder()
                .name("Model1")
                .brandId(1L)
                .build();

        Brand brand = new Brand();
        brand.setId(1L);

        Model createdModel = Model.builder()
                .id(1L)
                .name("Model1")
                .isDeleted(false)
                .brand(brand)
                .build();

        Mockito.when(modelService.create(modelCreateDTO)).thenReturn(createdModel);

        mockMvc.perform(post("/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(modelCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdModel.getId()))
                .andExpect(jsonPath("$.name").value(createdModel.getName()))
                .andExpect(jsonPath("$.isDeleted").value(createdModel.getIsDeleted()))
                .andExpect(jsonPath("$.brand.id").value(createdModel.getBrand().getId()));
    }

    @Test
    public void whenUpdateExistingModel_thenStatusOk() throws Exception {
        long modelId = 1L;
        ModelUpdateDTO modelUpdateDTO = ModelUpdateDTO.builder()
                .name("Model1")
                .brandId(1L)
                .build();

        Model updatedModel = Model.builder()
                .id(1L)
                .name("Model1")
                .isDeleted(false)
                .brand(new Brand())
                .build();

        Mockito.when(modelService.update(modelId, modelUpdateDTO)).thenReturn(updatedModel);

        mockMvc.perform(put("/models/{id}", modelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(modelUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedModel.getId()))
                .andExpect(jsonPath("$.name").value(updatedModel.getName()))
                .andExpect(jsonPath("$.isDeleted").value(updatedModel.getIsDeleted()))
                .andExpect(jsonPath("$.brand.id").value(updatedModel.getBrand().getId()));
    }

    @Test
    public void whenGetExistingModelById_thenStatusOk() throws Exception {
        BrandDTO brand = new BrandDTO();
        brand.setId(1L);

        Long modelId = 1L;
        ModelDTO modelResponseDTO = ModelDTO.builder()
                .name("Model1")
                .isDeleted(false)
                .brand(brand)
                .build();

        Mockito.when(modelService.getById(modelId)).thenReturn(Optional.of(modelResponseDTO));

        mockMvc.perform(get("/models/{id}", modelId))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(modelResponseDTO)));
    }

    @Test
    public void whenGetNonExistentModelById_thenStatusNotFound() throws Exception {
        Long modelId = 1L;

        Mockito.when(modelService.getById(modelId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/models/{id}", modelId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Model not found")));
    }

    static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}