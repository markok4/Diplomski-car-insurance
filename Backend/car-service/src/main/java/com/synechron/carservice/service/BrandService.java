package com.synechron.carservice.service;

import com.synechron.carservice.dto.BrandDTO;
import com.synechron.carservice.exception.ResourceException;
import com.synechron.carservice.mapper.BrandDTOMapper;
import com.synechron.carservice.model.Brand;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;

import com.synechron.carservice.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandDTOMapper dtoMapper;

    public Page<BrandDTO> getAllBrands(Pageable pageable) {
        try{ Page<Brand> brandPage = brandRepository.findAll(pageable);
            List<BrandDTO> brandDTOList = dtoMapper.listToDTO(brandPage.getContent());
            return new PageImpl<>(brandDTOList, pageable, brandPage.getTotalElements());
        }
        catch(Exception e) {
        throw e;
        }
    }

    public void deleteBrandById(Long id){
        try{
            Brand brand = getBrandById(id);
            brand.setIsDeleted(true);
            brandRepository.save(brand);
        }
        catch(Exception e){
            throw e;
        }
    }

    public Brand getBrandById(Long id) {
        try {
            Brand brand = brandRepository.findById(id).orElseThrow(() -> new ResourceException("Brand not found with id : " + id));
            return  brand;
        } catch (Exception e)
        {
            throw e;
        }
    }

    public BrandDTO createBrand(BrandDTO brand){
        try{
            brand.setId(null);
            brand.setCreationDate(LocalDate.now());
            brand.setIsDeleted(false);
            return dtoMapper.toDTO(brandRepository.save(dtoMapper.toEntity(brand)));
        } catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }
    public void restore(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Brand with id " + id + " not found"));
        brand.setIsDeleted(false);
        brandRepository.save(brand);
    }
    public void deactivate(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Brand with id " + id + " not found"));
        brand.setIsDeleted(true);
        brandRepository.save(brand);
    }
    public BrandDTO updateBrand(Long id, BrandDTO brandDto){
    try{
        if (brandRepository.existsById(brandDto.getId())) {
            return dtoMapper.toDTO(brandRepository.save(dtoMapper.toEntity(brandDto)));
        } else {
            throw new EntityNotFoundException("Entity does not exist");
        }
    }catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }

    //VALIDATIONS
    public Boolean brandCreationValidation (Brand brand) {
        if (brand.getName().length() > 2 && brand.getLogoImage()!=null )
            return true;
        return false;
    }
}
