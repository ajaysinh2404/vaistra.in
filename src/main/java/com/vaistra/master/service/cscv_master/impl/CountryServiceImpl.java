package com.vaistra.master.service.cscv_master.impl;

import com.vaistra.master.dto.cscv_master.CountryDto_Update;
import org.apache.commons.csv.CSVParser;
import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.CountryDto;
import com.vaistra.master.entity.cscv_master.Country;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.IllegalArgumentException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.cscv_master.CountryRepository;
import com.vaistra.master.service.cscv_master.CountryService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class CountryServiceImpl implements CountryService {
    private final AppUtils appUtils;

    private final CountryRepository countryRepository;


    @Autowired
    public CountryServiceImpl(AppUtils appUtils, CountryRepository countryRepository){

        this.appUtils = appUtils;
        this.countryRepository = countryRepository;
    }
    @Override
    public String addCountry(CountryDto countryDto) {

        if(countryRepository.existsByCountryNameIgnoreCase(countryDto.getCountryName().trim())){
            throw new DuplicateEntryException("Country with name: " + countryDto.getCountryName() + " already exist in current record.");
        }

        Country country = new Country();

        country.setCountryName(countryDto.getCountryName().trim());
        country.setIsActive(countryDto.getIsActive());
        countryRepository.save(country);

        return "Record added successfully.";
    }

    @Override
    public String updateCountry(Integer countryId, CountryDto_Update countryDto) {
        Country country = countryRepository.findById(countryId).orElseThrow(()-> new ResourceNotFoundException("Country not found with given id: " + countryId));

        if(countryDto.getCountryName() != null){
            Country countryWithSamename = countryRepository.findByCountryNameIgnoreCase(countryDto.getCountryName().trim());

            if(countryWithSamename != null && !countryWithSamename.getCountryId().equals(country.getCountryId())){
                throw new DuplicateEntryException("Country : " + countryDto.getCountryName() + " is already exist in current record...!");
            }

            country.setCountryName(countryDto.getCountryName().trim());

        }

        if(countryDto.getIsActive() != null)
            country.setIsActive(countryDto.getIsActive());

        countryRepository.save(country);

        return "Record updated successfully.";
    }

    @Override
    public String deleteCountry(Integer countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(()-> new ResourceNotFoundException("Country not found with given id: " + countryId));
        countryRepository.deleteById(countryId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getCountry(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Country> countryPage = countryRepository.findAll(pageable);

        List<CountryDto> countries = appUtils.countriesToDtos(countryPage.getContent());

        return HttpResponse.builder()
                .pageNumber(countryPage.getNumber())
                .pageSize(countryPage.getSize())
                .totalElements(countryPage.getTotalElements())
                .totalPages(countryPage.getTotalPages())
                .isLastPage(countryPage.isLast())
                .data(countries)
                .build();
    }

    /*@Override
    public HttpResponse getIsActiveCountry(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Country> countryPage = countryRepository.findByIsActive(pageable,true);

        List<CountryDto> countries = appUtils.countriesToDtos(countryPage.getContent());

        return HttpResponse.builder()
                .pageNumber(countryPage.getNumber())
                .pageSize(countryPage.getSize())
                .totalElements(countryPage.getTotalElements())
                .totalPages(countryPage.getTotalPages())
                .isLastPage(countryPage.isLast())
                .data(countries)
                .build();
    }*/

    @Override
    public List<CountryDto> getAllActiveCountry(CountryDto countryDto) {

        List<Country> countries = countryRepository.findByIsActive(true);

        return appUtils.countriesToDtos(countries);
    }

    @Override
    public HttpResponse getCountryByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword2 = null;
        Boolean keyword3 = null;


        if(keyword.equalsIgnoreCase("true"))
            keyword3 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword3 = Boolean.FALSE;
        }

        try {
            keyword2 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword2 = null;
        }


        Page<Country> countryPage = countryRepository.findByCountryNameContainingIgnoreCaseOrCountryIdOrIsActive(pageable, keyword, keyword2, keyword3);

        List<CountryDto> countries = appUtils.countriesToDtos(countryPage.getContent());

        return HttpResponse.builder()
                .pageNumber(countryPage.getNumber())
                .pageSize(countryPage.getSize())
                .totalElements(countryPage.getTotalElements())
                .totalPages(countryPage.getTotalPages())
                .isLastPage(countryPage.isLast())
                .data(countries)
                .build();
    }

    @Override
    public String uploadCountryCSV(MultipartFile file) throws IOException {

        if(file.isEmpty()){
            throw new ResourceNotFoundException("Country CSV File not found...!");
        }
        if(!Objects.equals(file.getContentType(), "text/csv")){
            throw new IllegalArgumentException("Invalid file type. Please upload a CSV file.");
        }

        try {
            List<Country> countries = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT)
                    .stream().skip(1) // Skip the first row
                    .map(record -> {
                        Country country = new Country();
                        country.setCountryName(record.get(0).trim()); // Assuming the first column is "country"
                        country.setIsActive(Boolean.parseBoolean(record.get(1))); // Assuming the second column is "isActive"
                        return country;
                    })
                    .toList();

            // Filter out duplicates by country name
            List<Country> nonDuplicateCountries = countries.stream()
                    .filter(distinctByKey(Country::getCountryName))
                    .toList();


            long uploadedRecordCount = nonDuplicateCountries.size();
            countryRepository.saveAll(nonDuplicateCountries);

            return "CSV file uploaded successfully. " + uploadedRecordCount + " records uploaded.";

        }catch (Exception e){
            return e.getMessage();
        }
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
