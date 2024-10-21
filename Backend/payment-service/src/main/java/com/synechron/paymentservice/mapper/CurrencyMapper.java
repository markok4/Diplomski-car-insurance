package com.synechron.paymentservice.mapper;

import com.synechron.paymentservice.dto.CurrencyDto;
import com.synechron.paymentservice.entity.Currency;
import com.synechron.paymentservice.util.DateUtil;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencyMapper implements IMapper<Currency, CurrencyDto>{


    @Override
    public Currency toEntity(CurrencyDto dto) {
        Currency currency = new Currency();
        currency.setId(dto.getId());
        currency.setName(dto.getName());
        currency.setCode(dto.getCode());
        currency.setLogo(dto.getLogo());
        currency.setIsDeleted(dto.isDeleted());
        try{
            currency.setCreationDate(DateUtil.stringToDate(dto.getCreationDate()));
        }catch(ParseException e){
            throw new RuntimeException("Error parsing date: " + dto.getCreationDate(), e);
        }
        try{
            currency.setLastUpdated(DateUtil.stringToDate(dto.getLastUpdated()));
        }catch(ParseException e){
            throw new RuntimeException("Error parsing date: " + dto.getLastUpdated(), e);
        }

        // Set other properties as needed
        return currency;
    }

    @Override
    public CurrencyDto toDTO(Currency model) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(model.getId());
        currencyDto.setName(model.getName());
        currencyDto.setCode(model.getCode());
        currencyDto.setLogo(model.getLogo());
        currencyDto.setDeleted(model.getIsDeleted());
        currencyDto.setCreationDate(DateUtil.dateToString(model.getCreationDate()));
        currencyDto.setLastUpdated(DateUtil.dateToString(model.getLastUpdated()));
        // Set other properties as needed
        return currencyDto;
    }

    @Override
    public List<CurrencyDto> listToDTO(List<Currency> currencies) {
        return currencies.stream()
                .map(this::toDTO)  // Map each Currency entity to a CurrencyDto
                .collect(Collectors.toList());  // Collect the mapped CurrencyDto objects into a list
    }

    public void updateEntityFromDto(CurrencyDto dto, Currency currency) {
        if (dto.getName() != null) {
            currency.setName(dto.getName());
        }
        if (dto.getCode() != null) {
            currency.setCode(dto.getCode());
        }
        if (dto.getLogo() != null) {
            currency.setLogo(dto.getLogo());
        }
    }


}
