package com.synechron.usermanagement.country;

import com.synechron.usermanagement.config.TestDatabaseConfig;
import com.synechron.usermanagement.model.Country;
import com.synechron.usermanagement.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CountryRepositoryTest {
    @Autowired
    CountryRepository countryRepository;

    @Test
    public void whenFindAll_thenReturnAllCountries() {
        List<Country> countries = countryRepository.findAll();

        assertEquals(21, countries.size(), "Should return all countries");
    }

    @Test
    public void whenFindById_thenReturnCorrectCountry() {
        Optional<Country> foundCountry = countryRepository.findById(1L);

        assertTrue(foundCountry.isPresent(), "Country should be found");
        assertEquals("Serbia", foundCountry.get().getName(), "Country should have the correct name");
    }

    @Test
    public void whenFindById_thenReturnEmptyForNonExistentCountry() {
        Optional<Country> foundCountry = countryRepository.findById(99L);

        assertFalse(foundCountry.isPresent(), "Country should not be found");
    }

    @Test
    public void whenCreateCountry_thenCountryIsPersisted() {
        Country newCountry = new Country();
        newCountry.setName("New Country");
        newCountry.setAbbreviation("NC");
        newCountry.setCreatedAt(LocalDateTime.now());
        newCountry.setIsDeleted(false);

        Country savedCountry = countryRepository.save(newCountry);

        assertNotNull(savedCountry.getId(), "Country should have an ID after being saved");

        assertEquals("New Country", savedCountry.getName(), "Saved country should have the correct name");
        assertEquals("NC", savedCountry.getAbbreviation(), "Saved country should have the correct abbreviation");
        assertFalse(savedCountry.getIsDeleted(), "Saved country should not be marked as deleted");
    }
}
