package com.vaistra.master.batchConfig.cscv_master.country;

import com.vaistra.master.entity.cscv_master.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;


@Slf4j
public class countryProcessor implements ItemProcessor<Country,Country> {

    @Override
    public Country process(Country country) throws Exception {
        country.setCountryName(country.getCountryName().trim());
        country.setIsActive(country.getIsActive());
        return country;
    }
}
