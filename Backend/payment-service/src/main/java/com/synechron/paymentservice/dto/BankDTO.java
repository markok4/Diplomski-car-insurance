package com.synechron.paymentservice.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@GroupSequence({BankDTO.class, CreateInfo.class, UpdateInfo.class})
public class BankDTO {

    @NotNull(groups = UpdateInfo.class)
    private Long id;
    @NotBlank(message = "Name is mandatory", groups = {CreateInfo.class, UpdateInfo.class})
    private String name;
    @NotBlank(message = "Logo is mandatory", groups = {CreateInfo.class, UpdateInfo.class})
    private String logo;
    @NotBlank(message = "Address is mandatory", groups = {CreateInfo.class, UpdateInfo.class})
    private String address;
    @NotBlank(message = "Country is mandatory", groups = {CreateInfo.class, UpdateInfo.class})
    private String country;
    @NotBlank(message = "City is mandatory", groups = {CreateInfo.class, UpdateInfo.class})
    private String city;
    @NotNull(message = "Employee number is mandatory", groups = {CreateInfo.class, UpdateInfo.class})
    private Integer employeeNumber;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private Boolean isBankrupt;
}

