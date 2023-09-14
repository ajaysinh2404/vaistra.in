package com.vaistra.master.service.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.CountryDto;

import java.util.List;

public interface CountryService {

    public String addCountry(CountryDto countryDto);

    public String updateCountry(Integer countryId, CountryDto countryDto);

    public String deleteCountry(Integer countryId);

    public HttpResponse getCountry(int pageNo, int pageSize, String sortBy, String sortDirection);

    //public HttpResponse getIsActiveCountry(int pageNo, int pageSize, String sortBy, String sortDirection);

    public List<CountryDto> getAllActiveCountry(CountryDto countryDto);

    public HttpResponse getCountryByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

}
