package com.vaistra.master.service.cscv_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.StateDto;
import com.vaistra.master.entity.cscv_master.Country;
import com.vaistra.master.entity.cscv_master.State;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.IsActiveExceptionHandler;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.cscv_master.CountryRepository;
import com.vaistra.master.repository.cscv_master.StateRepository;
import com.vaistra.master.service.cscv_master.StateService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(stateRepository.existsByStateNameIgnoreCase(stateDto.getStateName())){
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
    public String updateState(Integer stateId, StateDto stateDto) {
        State state = stateRepository.findById(stateId).orElseThrow(()-> new ResourceNotFoundException("State not found with given id: " + stateId));

        State stateWithSamename = stateRepository.findByStateNameIgnoreCase(stateDto.getStateName());

        if(stateWithSamename != null && !stateWithSamename.getStateId().equals(state.getStateId())){
            throw new DuplicateEntryException("State : " + stateDto.getStateName() + " is already exist in current record...!");
        }

        Country country = countryRepository.findById(stateDto.getCountryId()).orElseThrow(()->new ResourceNotFoundException("Country not found with given id: " + stateDto.getCountryId()));

        state.setStateName(stateDto.getStateName());
        state.setIsActive(stateDto.getIsActive());
        state.setCountry(country);

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
}
