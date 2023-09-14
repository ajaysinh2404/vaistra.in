package com.vaistra.master.service.cscv_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.VillageDto;
import com.vaistra.master.entity.cscv_master.*;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.IsActiveExceptionHandler;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.cscv_master.*;
import com.vaistra.master.service.cscv_master.VillageService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillageServiceImpl implements VillageService {

    private final AppUtils appUtils;

    private final VillageRepository villageRepository;

    private final SubDistrictRepository subDistrictRepository;

    private final DistrictRepository districtRepository;

    private final StateRepository stateRepository;

    private final CountryRepository countryRepository;

    @Autowired
    public VillageServiceImpl(AppUtils appUtils, VillageRepository villageRepository, SubDistrictRepository subDistrictRepository, DistrictRepository districtRepository, StateRepository stateRepository, CountryRepository countryRepository){
        this.appUtils = appUtils;
        this.villageRepository = villageRepository;
        this.subDistrictRepository = subDistrictRepository;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public String addVillage(VillageDto villageDto) {
        if(villageRepository.existsByVillageNameIgnoreCase(villageDto.getVillageName())){
            throw new DuplicateEntryException("Village with name: " + villageDto.getVillageName() + " already exist in current record.");
        }

        Village village = new Village();
        SubDistrict subDistrict = subDistrictRepository.findById(villageDto.getSubDistrictId()).orElseThrow(()->new ResourceNotFoundException("Sub-district not found with given id: " + villageDto.getSubDistrictId()));

        village.setVillageName(villageDto.getVillageName());
        village.setIsActive(villageDto.getIsActive());
        village.setSubDistrict(subDistrict);
        village.setDistrict(subDistrict.getDistrict());
        village.setState(subDistrict.getState());
        village.setCountry(subDistrict.getCountry());

        villageRepository.save(village);

        return "Record added successfully.";
    }

    @Override
    public String updateVillage(Integer villageId, VillageDto villageDto) {
        Village village = villageRepository.findById(villageId).orElseThrow(()->new ResourceNotFoundException("Village not found with given id: " + villageId));

        Village villageWithSameName = villageRepository.findByVillageNameIgnoreCase(villageDto.getVillageName());

        if(villageWithSameName != null && !villageWithSameName.getVillageId().equals(village.getVillageId())){
            throw new DuplicateEntryException("Village : " + villageDto.getVillageName() + " is already exist in current record...!");
        }


        SubDistrict subDistrict = subDistrictRepository.findById(villageDto.getSubDistrictId()).orElseThrow(()->new ResourceNotFoundException("Sub-district not found with given id: " + villageDto.getSubDistrictId()));
        District district = districtRepository.findById(villageDto.getDistrictId()).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + villageDto.getDistrictId()));
        State state = stateRepository.findById(villageDto.getStateId()).orElseThrow(()->new ResourceNotFoundException("State not found with given id: " + villageDto.getStateId()));
        Country country = countryRepository.findById(villageDto.getCountryId()).orElseThrow(()->new ResourceNotFoundException("Country not found with given id: " + villageDto.getCountryId()));

        village.setVillageName(villageDto.getVillageName());
        village.setIsActive(villageDto.getIsActive());
        village.setSubDistrict(subDistrict);
        village.setDistrict(district);
        village.setState(state);
        village.setCountry(country);

        villageRepository.save(village);

        return "Record updated successfully.";
    }

    @Override
    public String deleteVillage(Integer villageId) {
        Village village = villageRepository.findById(villageId).orElseThrow(()->new ResourceNotFoundException("Village not found with given id: " + villageId));
        villageRepository.deleteById(villageId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getVillage(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Village> villagePage = villageRepository.findAllBySubDistrict_isActive(true,pageable);

        List<VillageDto> villages = appUtils.villagesToDtos(villagePage.getContent());

        return HttpResponse.builder()
                .pageNumber(villagePage.getNumber())
                .pageSize(villagePage.getSize())
                .totalElements(villagePage.getTotalElements())
                .totalPages(villagePage.getTotalPages())
                .isLastPage(villagePage.isLast())
                .data(villages)
                .build();
    }

    @Override
    public List<VillageDto> getVillageBySubDistrict(Integer subDistrictId) {
        SubDistrict subDistrict = subDistrictRepository.findById(subDistrictId).orElseThrow(()-> new ResourceNotFoundException("Sub-district not found with given id: " + subDistrictId));

        if(!subDistrict.getIsActive()){
            throw new IsActiveExceptionHandler("Given SUb-district Id is not currently active...!");
        }

        List<Village> villages = villageRepository.findAllByIsActiveAndSubDistrict_SubDistrictId(true,subDistrictId);

        return appUtils.villagesToDtos(villages);
    }

    @Override
    public HttpResponse getVillageByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword6 = null;
        Boolean keyword7 = null;


        if(keyword.equalsIgnoreCase("true"))
            keyword7 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword7 = Boolean.FALSE;
        }

        try {
            keyword6 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword6 = null;
        }


        Page<Village> villagePage = villageRepository.findByVillageNameContainingIgnoreCaseOrSubDistrict_SubDistrictNameContainingIgnoreCaseOrDistrict_DistrictNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrCountry_CountryNameContainingIgnoreCaseOrVillageIdOrIsActive(pageable,keyword,keyword,keyword,keyword,keyword,keyword6,keyword7);

        List<VillageDto> villages = appUtils.villagesToDtos(villagePage.getContent());

        return HttpResponse.builder()
                .pageNumber(villagePage.getNumber())
                .pageSize(villagePage.getSize())
                .totalElements(villagePage.getTotalElements())
                .totalPages(villagePage.getTotalPages())
                .isLastPage(villagePage.isLast())
                .data(villages)
                .build();
    }
}
