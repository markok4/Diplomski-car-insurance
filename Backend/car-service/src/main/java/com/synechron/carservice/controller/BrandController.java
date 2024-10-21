package com.synechron.carservice.controller;


import com.synechron.carservice.dto.BrandDTO;
import com.synechron.carservice.model.Brand;
import com.synechron.carservice.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/brands")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAllBrands(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        try {
            Page<BrandDTO> brands = brandService.getAllBrands(PageRequest.of(page, size));
            return ResponseEntity.ok(brands);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while fatching the brand: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrand(@PathVariable Long id) {

        try {
            Brand brand = brandService.getBrandById(id);
            return ResponseEntity.ok(brand);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while fatching the brand: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable Long id) {
        try {
            brandService.deleteBrandById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while fatching the brand: " + e.getMessage());
        }
    }
    @PostMapping()
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandDTO brand) {
        try {
            BrandDTO createdBrand = brandService.createBrand(brand);
            return new ResponseEntity<>(createdBrand, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while fatching the brand: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        try {
            brandService.restore(id);
            return ResponseEntity.ok("Brand with ID " + id + " has been successfully restore");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Brand not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while restoring the brand: " + e.getMessage());
        }
    }
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        try {
            Brand brand = brandService.getBrandById(id);
            if(brand.getIsDeleted()) {
                brandService.restore(id);
                return ResponseEntity.ok("Brand with ID " + id + " has been successfully restore");
            }
            else
            {
                brandService.deactivate(id);
                return ResponseEntity.ok("Brand with ID " + id + " has been successfully restore");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Brand not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while restoring the brand: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @Valid  @RequestBody BrandDTO brandDto){
        try {

            BrandDTO updatedBrand = brandService.updateBrand(id, brandDto);
            return ResponseEntity.ok(updatedBrand);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while fatching the brand: " + e.getMessage());
        }
    }
}
