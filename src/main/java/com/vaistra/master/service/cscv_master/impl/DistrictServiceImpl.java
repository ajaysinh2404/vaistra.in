package com.vaistra.master.service.cscv_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.DistrictDto;
import com.vaistra.master.entity.cscv_master.Country;
import com.vaistra.master.entity.cscv_master.District;
import com.vaistra.master.entity.cscv_master.State;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.IsActiveExceptionHandler;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.cscv_master.CountryRepository;
import com.vaistra.master.repository.cscv_master.DistrictRepository;
import com.vaistra.master.repository.cscv_master.StateRepository;
import com.vaistra.master.service.cscv_master.DistrictService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(districtRepository.existsByDistrictNameIgnoreCase(districtDto.getDistrictName())){
            throw new DuplicateEntryException("District with name: " + districtDto.getDistrictName() + " already exist in current record.");
        }

        District district = new District();
        State state = stateRepository.findById(districtDto.getStateId()).orElseThrow(()->new ResourceNotFoundException("State not found with given id: " + districtDto.getStateId()));

        district.setDistrictName(districtDto.getDistrictName());
        district.setIsActive(districtDto.getIsActive());
        district.setState(state);
        district.setCountry(state.getCountry());

        districtRepository.save(district);

        return "Record added successfully.";
    }

    @Override
    public String updateDistrict(Integer districtId, DistrictDto districtDto) {
        District district = districtRepository.findById(districtId).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + districtId));

        District districtWithSameName = districtRepository.findByDistrictNameIgnoreCase(districtDto.getDistrictName());

        if(districtWithSameName != null && !districtWithSameName.getDistrictId().equals(district.getDistrictId())){
            throw new DuplicateEntryException("District : " + districtDto.getDistrictName() + " is already exist in current record...!");
        }


        State state = stateRepository.findById(districtDto.getStateId()).orElseThrow(()->new ResourceNotFoundException("State not found with given id: " + districtDto.getStateId()));
        Country country = countryRepository.findById(districtDto.getCountryId()).orElseThrow(()->new ResourceNotFoundException("Country not found with given id: " + districtDto.getCountryId()));


        district.setDistrictName(districtDto.getDistrictName());
        district.setIsActive(districtDto.getIsActive());
        district.setState(state);
        district.setCountry(country);

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
}
