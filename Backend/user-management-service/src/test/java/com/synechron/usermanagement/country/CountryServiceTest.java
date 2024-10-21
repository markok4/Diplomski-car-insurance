package com.synechron.usermanagement.country;

import com.synechron.usermanagement.dto.CountryDTO;
import com.synechron.usermanagement.dto.CountryResponseDTO;
import com.synechron.usermanagement.mapper.CountryMapper;
import com.synechron.usermanagement.model.Country;
import com.synechron.usermanagement.repository.CountryRepository;
import com.synechron.usermanagement.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CountryServiceTest {

    @Mock
    CountryMapper countryMapper;

    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryService countryService;

    @Test
    public void whenGetAll_thenReturnsPageOfCountryResponseDTO() {
        Pageable pageable = Pageable.unpaged();
        Country country = new Country();
        CountryResponseDTO dto = CountryResponseDTO.builder().build();
        Page<Country> countryPage = new PageImpl<>(Collections.singletonList(country));

        given(countryRepository.findAll(pageable)).willReturn(countryPage);
        given(countryMapper.toResponseDTO(any(Country.class))).willReturn(dto);

        Page<CountryResponseDTO> result = countryService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertSame(dto, result.getContent().get(0));
    }

    @Test
    public void whenDelete_thenSetsIsDeletedTrue() {
        Long countryId = 1L;
        Country country = new Country();
        given(countryRepository.findById(countryId)).willReturn(Optional.of(country));

        countryService.delete(countryId);

        assertTrue(country.getIsDeleted());
        verify(countryRepository).save(country);
    }

    @Test
    public void whenDeleteNonExistingCountry_thenThrowsEntityNotFoundException() {
        Long countryId = 1L;
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> countryService.delete(countryId),
                "Expected delete to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Country with id " + countryId + " not found"));
    }

    @Test
    public void whenRestore_thenSetsIsDeletedFalse() {
        Long countryId = 1L;
        Country country = new Country();
        country.setIsDeleted(true);
        given(countryRepository.findById(countryId)).willReturn(Optional.of(country));

        countryService.restore(countryId);

        assertFalse(country.getIsDeleted());
        verify(countryRepository).save(country);
    }

    @Test
    public void whenRestoreNonExistingCountry_thenThrowsEntityNotFoundException() {
        Long countryId = 1L;
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> countryService.restore(countryId),
                "Expected restore to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Country with id " + countryId + " not found"));
    }

    @Test
    public void whenCreate_thenReturnsCountryResponseDTO() {
        CountryDTO countryDTO = CountryDTO.builder().build();
        Country country = new Country();
        CountryResponseDTO responseDTO = CountryResponseDTO.builder().build();
        given(countryMapper.toEntity(any(CountryDTO.class))).willReturn(country);
        given(countryRepository.save(any(Country.class))).willReturn(country);
        given(countryMapper.toResponseDTO(any(Country.class))).willReturn(responseDTO);

        CountryResponseDTO result = countryService.create(countryDTO);

        assertNotNull(result);
        assertSame(responseDTO, result);
    }

    @Test
    public void whenUpdate_thenReturnsUpdatedCountryResponseDTO() {
        Long countryId = 1L;
        CountryDTO countryDTO = CountryDTO.builder().build();
        countryDTO.setName("New Name");
        Country country = new Country();
        CountryResponseDTO responseDTO = CountryResponseDTO.builder().build();
        given(countryRepository.findById(countryId)).willReturn(Optional.of(country));
        given(countryRepository.save(any(Country.class))).willReturn(country);
        given(countryMapper.toResponseDTO(any(Country.class))).willReturn(responseDTO);

        CountryResponseDTO result = countryService.update(countryId, countryDTO);

        assertNotNull(result);
        assertEquals("New Name", country.getName());
        assertSame(responseDTO, result);
    }

    @Test
    public void whenUpdateNonExistingCountry_thenThrowsEntityNotFoundException() {
        Long countryId = 1L;
        CountryDTO countryDTO = mock(CountryDTO.class);
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> countryService.update(countryId, countryDTO),
                "Expected update to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Country with id " + countryId + " not found"));
    }

    @Test
    public void whenGetById_thenReturnsCountryResponseDTO() {
        Long countryId = 1L;
        Country country = new Country();
        CountryResponseDTO responseDTO = CountryResponseDTO.builder().build();
        given(countryRepository.findById(countryId)).willReturn(Optional.of(country));
        given(countryMapper.toResponseDTO(any(Country.class))).willReturn(responseDTO);

        Optional<CountryResponseDTO> result = countryService.getById(countryId);

        assertTrue(result.isPresent());
        assertSame(responseDTO, result.get());
    }

    @Test
    public void whenGetByIdNonExistingCountry_thenThrowsEntityNotFoundException() {
        Long countryId = 1L;
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        Optional<CountryResponseDTO> result = countryService.getById(countryId);

        assertTrue(result.isEmpty());
    }
}
