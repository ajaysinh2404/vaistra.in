package com.vaistra.master.service.cscv_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.DistrictDto;
import com.vaistra.master.dto.cscv_master.DistrictDto_Update;
import com.vaistra.master.entity.cscv_master.Country;
import com.vaistra.master.entity.cscv_master.District;
import com.vaistra.master.entity.cscv_master.State;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.IllegalArgumentException;
import com.vaistra.master.exception.IsActiveExceptionHandler;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.cscv_master.CountryRepository;
import com.vaistra.master.repository.cscv_master.DistrictRepository;
import com.vaistra.master.repository.cscv_master.StateRepository;
import com.vaistra.master.service.cscv_master.DistrictService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final AppUtils appUtils;

    private final DistrictRepository districtRepository;

    private final StateRepository stateRepository;

    private final CountryRepository countryRepository;

    @Autowired
    public DistrictServiceImpl(AppUtils appUtils, DistrictRepository districtRepository, StateRepository stateRepository, CountryRepository countryRepository){
        this.appUtils = appUtils;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }
    @Override
    public String addDistrict(DistrictDto districtDto) {
        if(districtRepository.existsByDistrictNameIgnoreCase(districtDto.getDistrictName().trim())){
            throw new DuplicateEntryException("District with name: " + districtDto.getDistrictName() + " already exist in current record.");
        }

        District district = new District();
        State state = stateRepository.findById(districtDto.getStateId()).orElseThrow(()->new ResourceNotFoundException("State not found with given id: " + districtDto.getStateId()));

        district.setDistrictName(districtDto.getDistrictName().trim());
        district.setIsActive(districtDto.getIsActive());
        district.setState(state);
        district.setCountry(state.getCountry());

        districtRepository.save(district);

        return "Record added successfully.";
    }

    @Override
    public String updateDistrict(Integer districtId, DistrictDto_Update districtDto) {
        District district = districtRepository.findById(districtId).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + districtId));

        if(districtDto.getDistrictName()!=null) {
            District districtWithSameName = districtRepository.findByDistrictNameIgnoreCase(districtDto.getDistrictName().trim());

            if (districtWithSameName != null && !districtWithSameName.getDistrictId().equals(district.getDistrictId())) {
                throw new DuplicateEntryException("District : " + districtDto.getDistrictName() + " is already exist in current record...!");
            }
            district.setDistrictName(districtDto.getDistrictName().trim());
        }

        if(districtDto.getStateId()!=null) {
            State state = stateRepository.findById(districtDto.getStateId()).orElseThrow(() -> new ResourceNotFoundException("State not found with given id: " + districtDto.getStateId()));
            district.setState(state);
        }

        if(districtDto.getCountryId()!=null){
            Country country = countryRepository.findById(districtDto.getCountryId()).orElseThrow(()->new ResourceNotFoundException("Country not found with given id: " + districtDto.getCountryId()));
            district.setCountry(country);
        }

        if(districtDto.getIsActive()!=null){
            district.setIsActive(districtDto.getIsActive());
        }

        districtRepository.save(district);

        return "Record updated successfully.";
    }

    @Override
    public String deleteDistrict(Integer districtId) {
        District district = districtRepository.findById(districtId).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + districtId));
        districtRepository.deleteById(districtId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getDistrict(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<District> districtPage = districtRepository.findAllByState_isActive(true,pageable);

        List<DistrictDto> districts = appUtils.districtsToDtos(districtPage.getContent());

        return HttpResponse.builder()
                .pageNumber(districtPage.getNumber())
                .pageSize(districtPage.getSize())
                .totalElements(districtPage.getTotalElements())
                .totalPages(districtPage.getTotalPages())
                .isLastPage(districtPage.isLast())
                .data(districts)
                .build();
    }

    @Override
    public List<DistrictDto> getDistrictByState(Integer stateId) {
        State state = stateRepository.findById(stateId).orElseThrow(()-> new ResourceNotFoundException("State not found with given id: " + stateId));

        if(!state.getIsActive()){
            throw new IsActiveExceptionHandler("Given State Id is not currently active...!");
        }

        List<District> districts = districtRepository.findAllByIsActiveAndState_StateId(true,stateId);

        return appUtils.districtsToDtos(districts);
    }

    @Override
    public HttpResponse getDistrictByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword4 = null;
        Boolean keyword5 = null;


        if(keyword.equalsIgnoreCase("true"))
            keyword5 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword5 = Boolean.FALSE;
        }

        try {
            keyword4 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword4 = null;
        }


        Page<District> districtPage = districtRepository.findByDistrictNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrDistrictIdOrIsActive(pageable,keyword,keyword,keyword,keyword4,keyword5);

        List<DistrictDto> districts = appUtils.districtsToDtos(districtPage.getContent());

        return HttpResponse.builder()
                .pageNumber(districtPage.getNumber())
                .pageSize(districtPage.getSize())
                .totalElements(districtPage.getTotalElements())
                .totalPages(districtPage.getTotalPages())
                .isLastPage(districtPage.isLast())
                .data(districts)
                .build();
    }

    @Transactional
    @Override
    public String uploadDistrictCSV(MultipartFile file) {
        if(file.isEmpty()){
            throw new ResourceNotFoundException("District CSV File not found...!");
        }
        if(!Objects.equals(file.getContentType(), "text/csv")){
            throw new IllegalArgumentException("Invalid file type. Please upload a CSV file.");
        }

        try {
            List<District> districts = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT)
                    .stream().skip(1) // Skip the first row
                    .map(record -> {

                        String districtName = record.get(2).trim();
                        Boolean isActive = Boolean.parseBoolean(record.get(3));

                        Optional<District> existDistrict = Optional.ofNullable(districtRepository.findByDistrictNameIgnoreCase(districtName));

                        if (existDistrict.isPresent()){
                            return null;
                        }
                        else {
                            District district = new District();

                            district.setDistrictName(districtName); // Assuming the first column is "stateName"
                            district.setIsActive(isActive); // Assuming the second column is "isActive"

                            // Get the State name from the CSV file
                            String stateName = record.get(1).trim();

                            // Try to find the State by name
                            State state = stateRepository.findByStateNameIgnoreCase(stateName);

                            // If the state does not exist, create a new one
                            if(state == null){
                                state = new State();
                                state.setStateName(stateName.trim());
                                state.setIsActive(true);

                                // Get the State name from the CSV file
                                String countryName = record.get(0).trim();

                                // Try to find the State by name
                                Country country = countryRepository.findByCountryNameIgnoreCase(countryName);

                                // If the country does not exist, create a new one
                                if (country == null) {
                                    country = new Country();
                                    country.setCountryName(countryName.trim());
                                    country.setIsActive(true); // You can set the "isActive" flag as needed
                                    countryRepository.save(country);
                                }

                                state.setCountry(country);
                                stateRepository.save(state);

                            }
                            district.setState(state);
                            district.setCountry(state.getCountry());
                            return district;

                        }
                    })
                    .toList();

            // Filter out duplicates by district name
            List<District> nonDuplicateDistrict = districts.stream()
                    .filter(distinctByKey(District::getDistrictName))
                    .toList();

            // Save the non-duplicate entities to the database
            long uploadedRecordCount = nonDuplicateDistrict.size();
            districtRepository.saveAll(nonDuplicateDistrict);

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
