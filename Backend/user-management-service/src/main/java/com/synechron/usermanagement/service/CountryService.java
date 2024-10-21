package com.synechron.usermanagement.service;

import com.synechron.usermanagement.dto.CountryDTO;
import com.synechron.usermanagement.dto.CountryResponseDTO;
import com.synechron.usermanagement.mapper.CountryMapper;
import com.synechron.usermanagement.model.Country;
import com.synechron.usermanagement.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository repository;
    @Autowired
    private CountryMapper countryMapper;

    public Page<CountryResponseDTO> getAll(Pageable pageable) {
        Page<Country> countryPage = repository.findAll(pageable);
        return countryPage.map(country -> countryMapper.toResponseDTO(country));
    }

    public void delete(Long countryId) {
        Country country = repository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + countryId + " not found"));
        country.setIsDeleted(true);
        repository.save(country);
    }

    public void restore(Long countryId) {
        Country country = repository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + countryId + " not found"));
        country.setIsDeleted(false);
        repository.save(country);
    }

    public CountryResponseDTO create(CountryDTO countryDTO) {
        Country country = countryMapper.toEntity(countryDTO);
        country.setCreatedAt(LocalDateTime.now());
        Country savedCountry = repository.save(country);
        return countryMapper.toResponseDTO(savedCountry);
    }

    public CountryResponseDTO update(Long countryId, CountryDTO countryDTO) {
        Country country = repository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + countryId + " not found"));
        country.setName(countryDTO.getName());
        country.setAbbreviation(countryDTO.getAbbreviation());
        Country savedCountry = repository.save(country);
        return countryMapper.toResponseDTO(savedCountry);
    }


    public Optional<CountryResponseDTO> getById(Long id) {
        try {
            return repository.findById(id).map(country -> countryMapper.toResponseDTO(country));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while retrieving the country by ID: " + id);
        }
    }

}
