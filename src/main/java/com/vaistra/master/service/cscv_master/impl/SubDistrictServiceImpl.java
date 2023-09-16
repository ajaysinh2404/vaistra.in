package com.vaistra.master.service.cscv_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.SubDistrictDto;
import com.vaistra.master.entity.cscv_master.Country;
import com.vaistra.master.entity.cscv_master.District;
import com.vaistra.master.entity.cscv_master.State;
import com.vaistra.master.entity.cscv_master.SubDistrict;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.IsActiveExceptionHandler;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.cscv_master.CountryRepository;
import com.vaistra.master.repository.cscv_master.DistrictRepository;
import com.vaistra.master.repository.cscv_master.StateRepository;
import com.vaistra.master.repository.cscv_master.SubDistrictRepository;
import com.vaistra.master.service.cscv_master.SubDistrictService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubDistrictServiceImpl implements SubDistrictService {

    private final AppUtils appUtils;

    private final SubDistrictRepository subDistrictRepository;

    private final DistrictRepository districtRepository;

    private final StateRepository stateRepository;

    private final CountryRepository countryRepository;

    @Autowired
    public SubDistrictServiceImpl(AppUtils appUtils, SubDistrictRepository subDistrictRepository, DistrictRepository districtRepository, StateRepository stateRepository, CountryRepository countryRepository){
        this.appUtils = appUtils;
        this.subDistrictRepository = subDistrictRepository;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public String addSubDistrict(SubDistrictDto subDistrictDto) {
        if(subDistrictRepository.existsBySubDistrictNameIgnoreCase(subDistrictDto.getSubDistrictName().trim())){
            throw new DuplicateEntryException("Sub-District with name: " + subDistrictDto.getSubDistrictName() + " already exist in current record.");
        }

        SubDistrict subDistrict = new SubDistrict();
        District district = districtRepository.findById(subDistrictDto.getDistrictId()).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + subDistrictDto.getDistrictId()));

        subDistrict.setSubDistrictName(subDistrictDto.getSubDistrictName().trim());
        subDistrict.setIsActive(subDistrictDto.getIsActive());
        subDistrict.setDistrict(district);
        subDistrict.setState(district.getState());
        subDistrict.setCountry(district.getCountry());

        subDistrictRepository.save(subDistrict);
        return "Record added successfully.";
    }

    @Override
    public String updateSubDistrict(Integer subDistrictId, SubDistrictDto subDistrictDto) {
        SubDistrict subDistrict = subDistrictRepository.findById(subDistrictId).orElseThrow(()->new ResourceNotFoundException("Sub-District not found with given id: " + subDistrictId));

        SubDistrict subDistrictWithSameName = subDistrictRepository.findBySubDistrictNameIgnoreCase(subDistrictDto.getSubDistrictName().trim());

        if(subDistrictWithSameName != null && !subDistrictWithSameName.getSubDistrictId().equals(subDistrict.getSubDistrictId())){
            throw new DuplicateEntryException("Sub-district : " + subDistrictDto.getSubDistrictName() + " is already exist in current record...!");
        }


        District district = districtRepository.findById(subDistrictDto.getDistrictId()).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + subDistrictDto.getDistrictId()));
        State state = stateRepository.findById(subDistrictDto.getStateId()).orElseThrow(()->new ResourceNotFoundException("State not found with given id: " + subDistrictDto.getStateId()));
        Country country = countryRepository.findById(subDistrictDto.getCountryId()).orElseThrow(()->new ResourceNotFoundException("Country not found with given id: " + subDistrictDto.getCountryId()));

        subDistrict.setSubDistrictName(subDistrictDto.getSubDistrictName().trim());
        subDistrict.setIsActive(subDistrictDto.getIsActive());
        subDistrict.setDistrict(district);
        subDistrict.setState(state);
        subDistrict.setCountry(country);

        subDistrictRepository.save(subDistrict);

        return "Record updated successfully.";
    }

    @Override
    public String deleteSubDistrict(Integer subDistrictId) {
        SubDistrict subDistrict = subDistrictRepository.findById(subDistrictId).orElseThrow(()->new ResourceNotFoundException("Sub-District not found with given id: " + subDistrictId));
        subDistrictRepository.deleteById(subDistrictId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getSubDistrict(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<SubDistrict> subDistrictPage = subDistrictRepository.findAllByDistrict_isActive(true,pageable);

        List<SubDistrictDto> SubDistricts = appUtils.subDistrictsToDtos(subDistrictPage.getContent());

        return HttpResponse.builder()
                .pageNumber(subDistrictPage.getNumber())
                .pageSize(subDistrictPage.getSize())
                .totalElements(subDistrictPage.getTotalElements())
                .totalPages(subDistrictPage.getTotalPages())
                .isLastPage(subDistrictPage.isLast())
                .data(SubDistricts)
                .build();    }

    @Override
    public List<SubDistrictDto> getSubDistrictByDistrict(Integer districtId) {
        District district = districtRepository.findById(districtId).orElseThrow(()-> new ResourceNotFoundException("District not found with given id: " + districtId));

        if(!district.getIsActive()){
            throw new IsActiveExceptionHandler("Given District Id is not currently active...!");
        }

        List<SubDistrict> subDistricts = subDistrictRepository.findAllByIsActiveAndDistrict_DistrictId(true,districtId);

        return appUtils.subDistrictsToDtos(subDistricts);
    }

    @Override
    public HttpResponse getSubDistrictByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword5 = null;
        Boolean keyword6 = null;


        if(keyword.equalsIgnoreCase("true"))
            keyword6 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword6 = Boolean.FALSE;
        }

        try {
            keyword5 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword5 = null;
        }


        Page<SubDistrict> subDistrictPage = subDistrictRepository.findBySubDistrictNameContainingIgnoreCaseOrDistrict_DistrictNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrSubDistrictIdOrIsActive(pageable,keyword,keyword,keyword,keyword,keyword5,keyword6);

        List<SubDistrictDto> subDistricts = appUtils.subDistrictsToDtos(subDistrictPage.getContent());

        return HttpResponse.builder()
                .pageNumber(subDistrictPage.getNumber())
                .pageSize(subDistrictPage.getSize())
                .totalElements(subDistrictPage.getTotalElements())
                .totalPages(subDistrictPage.getTotalPages())
                .isLastPage(subDistrictPage.isLast())
                .data(subDistricts)
                .build();
    }
}
