package com.synechron.carservice.model;

import com.synechron.carservice.config.TestDatabaseConfig;
import com.synechron.carservice.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ModelRepositoryTest {
    @Autowired
    ModelRepository modelRepository;

    @Test
    public void whenFindAll_thenReturnAllCountries() {
        List<Model> models = modelRepository.findAll();

        assertEquals(4, models.size(), "Should return all models");
    }

    @Test
    public void whenFindById_thenReturnCorrectModel() {
        Optional<Model> foundModel = modelRepository.findById(1L);

        assertTrue(foundModel.isPresent(), "Model should be found");
        assertEquals("Huracan", foundModel.get().getName(), "Model should have the correct name");
    }

    @Test
    public void whenFindById_thenReturnEmptyForNonExistentModel() {
        Optional<Model> foundModel = modelRepository.findById(99L);

        assertFalse(foundModel.isPresent(), "Model should not be found");
    }
}