package com.vaistra.master.service.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.CountryDto;
import com.vaistra.master.dto.cscv_master.CountryDto_Update;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CountryService {

    public String addCountry(CountryDto countryDto);

    public String updateCountry(Integer countryId, CountryDto_Update countryDto);

    public String deleteCountry(Integer countryId);

    public HttpResponse getCountry(int pageNo, int pageSize, String sortBy, String sortDirection);

    //public HttpResponse getIsActiveCountry(int pageNo, int pageSize, String sortBy, String sortDirection);

    public List<CountryDto> getAllActiveCountry(CountryDto countryDto);

    public HttpResponse getCountryByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

    public String uploadCountryCSV(MultipartFile file) throws IOException;


}
