package com.vaistra.master.service.cscv_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.StateDto;
import com.vaistra.master.dto.cscv_master.StateDto_Update;
import com.vaistra.master.entity.cscv_master.Country;
import com.vaistra.master.entity.cscv_master.State;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.IllegalArgumentException;
import com.vaistra.master.exception.IsActiveExceptionHandler;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.cscv_master.CountryRepository;
import com.vaistra.master.repository.cscv_master.StateRepository;
import com.vaistra.master.service.cscv_master.StateService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {
    private final AppUtils appUtils;

    private final StateRepository stateRepository;

    private final CountryRepository countryRepository;

    @Autowired
    public StateServiceImpl(AppUtils appUtils, StateRepository stateRepository, CountryRepository countryRepository){

        this.appUtils = appUtils;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }

    public String addState(StateDto stateDto){
        if(stateRepository.existsByStateNameIgnoreCase(stateDto.getStateName().trim())){
            throw new DuplicateEntryException("State with name: " + stateDto.getStateName() + " already exist in current record.");
        }

        State state = new State();

        Country country = countryRepository.findById(stateDto.getCountryId()).orElseThrow(()->new ResourceNotFoundException("Country not found with given id: " + stateDto.getCountryId()));

        state.setStateName(stateDto.getStateName());
        state.setIsActive(stateDto.getIsActive());
        state.setCountry(country);

        stateRepository.save(state);

        return "Record added successfully.";
    }

    @Override
    public String updateState(Integer stateId, StateDto_Update stateDto) {
        State state = stateRepository.findById(stateId).orElseThrow(()-> new ResourceNotFoundException("State not found with given id: " + stateId));

        if(stateDto.getStateName()!=null) {

            State stateWithSamename = stateRepository.findByStateNameIgnoreCase(stateDto.getStateName().trim());

            if (stateWithSamename != null && !stateWithSamename.getStateId().equals(state.getStateId())) {
                throw new DuplicateEntryException("State : " + stateDto.getStateName() + " is already exist in current record...!");
            }
            state.setStateName(stateDto.getStateName().trim());
        }

        if(stateDto.getIsActive()!=null)
            state.setIsActive(stateDto.getIsActive());

        if(stateDto.getCountryId()!=null) {
            Country country = countryRepository.findById(stateDto.getCountryId()).orElseThrow(() -> new ResourceNotFoundException("Country not found with given id: " + stateDto.getCountryId()));
            state.setCountry(country);
        }


        stateRepository.save(state);

        return "Record updated successfully.";
    }

    @Override
    public String deleteState(Integer stateId) {
        State state = stateRepository.findById(stateId).orElseThrow(()-> new ResourceNotFoundException("State not found with given id: " + stateId));
        stateRepository.deleteById(stateId);
        return "Record deleted successfully.";    }

    @Override
    public HttpResponse getState(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<State> statePage = stateRepository.findAllByCountry_isActive(true,pageable);

        List<StateDto> states = appUtils.statesToDtos(statePage.getContent());

        return HttpResponse.builder()
                .pageNumber(statePage.getNumber())
                .pageSize(statePage.getSize())
                .totalElements(statePage.getTotalElements())
                .totalPages(statePage.getTotalPages())
                .isLastPage(statePage.isLast())
                .data(states)
                .build();
    }

    @Override
    public List<StateDto> getStateByCountry(Integer countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(()-> new ResourceNotFoundException("Country not found with given id: " + countryId));

        if(!country.getIsActive()){
            throw new IsActiveExceptionHandler("Given Country Id is not currently active...!");
        }

        List<State> states = stateRepository.findAllByIsActiveAndCountry_CountryId(true,countryId);

        return appUtils.statesToDtos(states);
    }


    @Override
    public HttpResponse getStateByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword3 = null;
        Boolean keyword4 = null;


        if(keyword.equalsIgnoreCase("true"))
            keyword4 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword4 = Boolean.FALSE;
        }

        try {
            keyword3 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword3 = null;
        }


        Page<State> statePage = stateRepository.findByStateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrStateIdOrIsActive(pageable, keyword, keyword, keyword3, keyword4);

        List<StateDto> states = appUtils.statesToDtos(statePage.getContent());

        return HttpResponse.builder()
                .pageNumber(statePage.getNumber())
                .pageSize(statePage.getSize())
                .totalElements(statePage.getTotalElements())
                .totalPages(statePage.getTotalPages())
                .isLastPage(statePage.isLast())
                .data(states)
                .build();
    }

    @Override
    public String uploadStateCSV(MultipartFile file) {
        if(file.isEmpty()){
            throw new ResourceNotFoundException("State CSV File not found...!");
        }
        if(!Objects.equals(file.getContentType(), "text/csv")){
            throw new IllegalArgumentException("Invalid file type. Please upload a CSV file.");
        }

        Set<Object> seen = ConcurrentHashMap.newKeySet();

        try {

            int batchSize = 500; // Adjust the batch size as needed
            AtomicInteger recordNumber = new AtomicInteger(0);
             List<List<CSVRecord>> batches = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT)
                    .stream().skip(1) // Skip the first row
                    .collect(Collectors.groupingBy(record -> recordNumber.getAndIncrement() / batchSize))
                    .values().stream().toList();

            for (List<CSVRecord> batch : batches){
                List<State> states = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT)
                        .stream().skip(1) // Skip the first row
                        .map(record -> {
                            String stateName = record.get(1).trim();
                            Boolean isActive = Boolean.parseBoolean(record.get(2));

                            Optional<State> existState = Optional.ofNullable(stateRepository.findByStateNameIgnoreCase(stateName));

                            if(existState.isPresent()){
                                return null;
                            }

                            else {
                                State state = new State();
                                state.setStateName(stateName); // Assuming the first column is "stateName"
                                state.setIsActive(isActive); // Assuming the second column is "isActive"
                                // Get the country name from the CSV file
                                String countryName = record.get(0).trim(); // Assuming the third column is "countryName"

                                // Try to find the country by name
                                Country country = countryRepository.findByCountryNameIgnoreCase(countryName);

                                // If the country does not exist, create a new one
                                if (country == null) {
                                    country = new Country();
                                    country.setCountryName(countryName.trim());
                                    country.setIsActive(true); // You can set the "isActive" flag as needed
                                    countryRepository.save(country);
                                }

                                state.setCountry(country);
                                return state;
                            }

                        })
                        .toList();

                // Filter out duplicates by country name
                List<State> nonDuplicateState = states.stream()
                        .filter(distinctByKey(State::getStateName))
                        .toList();

                // Save the non-duplicate entities to the database
                long uploadedRecordCount = nonDuplicateState.size();
                stateRepository.saveAll(nonDuplicateState);

            }

            return "CSV file uploaded successfully records uploaded.";

        }catch (Exception e){
            return e.getMessage();
        }

    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
