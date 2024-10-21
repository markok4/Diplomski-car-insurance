package com.synechron.carservice.service;

import com.synechron.carservice.dto.ModelCreateDTO;
import com.synechron.carservice.dto.ModelDTO;
import com.synechron.carservice.dto.ModelUpdateDTO;
import com.synechron.carservice.mapper.ModelDTOMapper;
import com.synechron.carservice.model.Brand;
import com.synechron.carservice.model.Model;
import com.synechron.carservice.repository.ModelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ModelDTOMapper modelDTOMapper;

    public Model update(Long modelId, ModelUpdateDTO modelUpdateDTO) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Model with id " + modelId + " not found"));

        model.setName(modelUpdateDTO.getName());

        if (modelUpdateDTO.getBrandId() != null) {
            Brand brand = brandService.getBrandById(modelUpdateDTO.getBrandId());
            model.setBrand(brand);
        }

        return modelRepository.save(model);
    }


    public Model create(ModelCreateDTO modelCreateDTO) {
        Model model = modelDTOMapper.fromCreateDTO(modelCreateDTO);
        model.setCreatedAt(LocalDateTime.now());
        if (modelCreateDTO.getBrandId() != null) {
            Brand brand = brandService.getBrandById(modelCreateDTO.getBrandId());
            model.setBrand(brand);
        }

        return modelRepository.save(model);
    }

    public void delete(Long modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Model with id " + modelId + " not found"));
        model.setIsDeleted(true);
        modelRepository.save(model);
    }

    public void restore(Long modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Model with id " + modelId + " not found"));
        model.setIsDeleted(false);
        modelRepository.save(model);
    }

    public Optional<ModelDTO> getById(Long id) {
        try {
            return modelRepository.findById(id)
                    .map(modelDTOMapper::toDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while retrieving the model by ID: " + id);
        }
    }

    public Page<ModelDTO> getAll(Pageable pageable) {
        Page<Model> modelPage = modelRepository.findAll(pageable);
        return modelPage.map(model -> modelDTOMapper.toDTO(model));
    }

    public List<ModelDTO> getAllByBrand(Long brandId) {
        List<Model> models = modelRepository.findAllByBrandId(brandId);
        return models.stream().map(model -> modelDTOMapper.toDTO(model)).toList();
    }

    public Model getReferenceById(Long id) {
        return modelRepository.getReferenceById(id);
    }
}
