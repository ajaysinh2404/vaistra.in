package com.vaistra.master.service.mines_master.impl;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.DesignationDto;
import com.vaistra.master.dto.mines_master.DesignationDto_Update;
import com.vaistra.master.entity.mines_master.Designation;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.mines_master.DesignationRepository;
import com.vaistra.master.service.mines_master.DesignationService;
import com.vaistra.master.utils.mines_master.AppUtils_mines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationServiceImpl implements DesignationService {
    private final AppUtils_mines appUtilsMines;

    private final DesignationRepository designationRepository;

    @Autowired
    public DesignationServiceImpl(AppUtils_mines appUtilsMines, DesignationRepository designationRepository){
        this.appUtilsMines = appUtilsMines;
        this.designationRepository = designationRepository;
    }

    @Override
    public String addDesignation(DesignationDto designationDto) {
        if(designationRepository.existsByDesignationNameIgnoreCase(designationDto.getDesignationName().trim())){
            throw new DuplicateEntryException("Designation with name: " + designationDto.getDesignationName() + " already exist in current record.");
        }

        Designation designation = new Designation();

        designation.setDesignationName(designationDto.getDesignationName().trim());

        designationRepository.save(designation);

        return "Record added successfully.";
    }

    @Override
    public String updateDesignation(Integer designationId, DesignationDto_Update designationDto) {
        Designation designation = designationRepository.findById(designationId).orElseThrow(()->new ResourceNotFoundException("Designation not found with given id: " + designationId));

        if(designationDto.getDesignationName()!=null){
            Designation designationWithSameName = designationRepository.findByDesignationNameIgnoreCase(designationDto.getDesignationName().trim());

            if(designationWithSameName != null && !designationWithSameName.getDesignationId().equals(designation.getDesignationId())){
                throw new DuplicateEntryException("Designation : " + designationDto.getDesignationName() + " is already exist in current record...!");
            }
            designation.setDesignationName(designationDto.getDesignationName().trim());
        }

        designationRepository.save(designation);

        return "Record updated successfully.";
    }

    @Override
    public String deleteDesignation(Integer designationId) {
        Designation designation = designationRepository.findById(designationId).orElseThrow(()->new ResourceNotFoundException("Designation not found with given id: " + designationId));
        designationRepository.deleteById(designationId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getDesignation(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Designation> designationPage = designationRepository.findAll(pageable);

        List<DesignationDto> designations = appUtilsMines.designationsToDtos(designationPage.getContent());

        return HttpResponse.builder()
                .pageNumber(designationPage.getNumber())
                .pageSize(designationPage.getSize())
                .totalElements(designationPage.getTotalElements())
                .totalPages(designationPage.getTotalPages())
                .isLastPage(designationPage.isLast())
                .data(designations)
                .build();
    }

    @Override
    public HttpResponse getDesignationByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword2 = null;

        try {
            keyword2 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword2 = null;
        }

        Page<Designation> designationPage = designationRepository.findByDesignationNameContainingIgnoreCaseOrDesignationId(pageable,keyword,keyword2);

        List<DesignationDto> designations = appUtilsMines.designationsToDtos(designationPage.getContent());

        return HttpResponse.builder()
                .pageNumber(designationPage.getNumber())
                .pageSize(designationPage.getSize())
                .totalElements(designationPage.getTotalElements())
                .totalPages(designationPage.getTotalPages())
                .isLastPage(designationPage.isLast())
                .data(designations)
                .build();
    }
}
