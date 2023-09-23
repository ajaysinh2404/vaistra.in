package com.vaistra.master.batchConfig.cscv_master.country;

import com.vaistra.master.entity.cscv_master.Country;
import com.vaistra.master.repository.cscv_master.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class countryWriter implements ItemWriter<Country> {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void write(Chunk<? extends Country> chunk) throws Exception {
        countryRepository.saveAll(chunk.getItems());
    }
}
