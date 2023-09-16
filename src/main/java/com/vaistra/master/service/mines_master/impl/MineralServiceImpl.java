package com.vaistra.master.service.mines_master.impl;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.MineralDto;
import com.vaistra.master.dto.mines_master.VehicleDto;
import com.vaistra.master.entity.mines_master.Mineral;
import com.vaistra.master.entity.mines_master.Vehicle;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.mines_master.MineralRepository;
import com.vaistra.master.service.mines_master.MineralService;
import com.vaistra.master.service.mines_master.VehicleService;
import com.vaistra.master.utils.mines_master.AppUtils_mines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service

public class MineralServiceImpl implements MineralService {
    private final AppUtils_mines appUtilsMines;

    private final MineralRepository mineralRepository;

    @Autowired
    public MineralServiceImpl(AppUtils_mines appUtilsMines, MineralRepository mineralRepository){
        this.appUtilsMines = appUtilsMines;
        this.mineralRepository = mineralRepository;
    }

    @Override
    public String addMineral(MineralDto mineralDto) {
        if(mineralRepository.existsByMineralNameIgnoreCase(mineralDto.getMineralName().trim())){
            throw new DuplicateEntryException("Mineral with name: " + mineralDto.getMineralName() + " already exist in current record.");
        }

        if(mineralRepository.existsByAtrNameIgnoreCase(mineralDto.getAtrName().trim())){
            throw new DuplicateEntryException("ATR with name: " + mineralDto.getAtrName() + " already exist in current record.");
        }

        if(mineralRepository.existsByHsnCodeIgnoreCase(mineralDto.getHsnCode().trim())){
            throw new DuplicateEntryException("Hsncode: " + mineralDto.getHsnCode() + " already exist in current record.");
        }

        Mineral mineral = new Mineral();

        mineral.setMineralName(mineralDto.getMineralName().trim());
        mineral.setCategory(mineralDto.getCategory().trim());
        mineral.setAtrName(mineralDto.getAtrName().trim());
        mineral.setHsnCode(mineralDto.getHsnCode().trim());
        mineral.setGrade(Arrays.asList(mineralDto.getGrade()));

        mineralRepository.save(mineral);

        return "Record added successfully.";
    }

    @Override
    public String updateMineral(Integer mineralId, MineralDto mineralDto) {
        Mineral mineral = mineralRepository.findById(mineralId).orElseThrow(()->new ResourceNotFoundException("Mineral not found with given id: " + mineralId));

        Mineral mineralWithSameName = mineralRepository.findByMineralNameIgnoreCase(mineralDto.getMineralName().trim());

        if(mineralWithSameName != null && !mineralWithSameName.getMineralId().equals(mineral.getMineralId())){
            throw new DuplicateEntryException("Mineral : " + mineralDto.getMineralName() + " is already exist in current record...!");
        }

        Mineral atrWithSameName = mineralRepository.findByAtrNameIgnoreCase(mineralDto.getAtrName().trim());

        if(atrWithSameName != null && !atrWithSameName.getMineralId().equals(mineral.getMineralId())){
            throw new DuplicateEntryException("ATR : " + mineralDto.getAtrName() + " is already exist in current record...!");
        }

        Mineral hsnWithSameName = mineralRepository.findByHsnCodeIgnoreCase(mineralDto.getHsnCode().trim());

        if(hsnWithSameName != null && !hsnWithSameName.getMineralId().equals(mineral.getMineralId())){
            throw new DuplicateEntryException("HSN Code : " + mineralDto.getHsnCode() + " is already exist in current record...!");
        }

        mineral.setMineralName(mineralDto.getMineralName().trim());
        mineral.setCategory(mineralDto.getCategory().trim());
        mineral.setAtrName(mineralDto.getAtrName().trim());
        mineral.setHsnCode(mineralDto.getHsnCode().trim());
        mineral.setGrade(Arrays.asList(mineralDto.getGrade()));

        mineralRepository.save(mineral);

        return "Record added successfully.";
    }

    @Override
    public String deleteMineral(Integer mineralId) {
        Mineral mineral = mineralRepository.findById(mineralId).orElseThrow(()->new ResourceNotFoundException("Mineral not found with given id: " + mineralId));
        mineralRepository.deleteById(mineralId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getMineral(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Mineral> mineralPage = mineralRepository.findAll(pageable);

        List<MineralDto> minerals = appUtilsMines.mineralsToDtos(mineralPage.getContent());

        return HttpResponse.builder()
                .pageNumber(mineralPage.getNumber())
                .pageSize(mineralPage.getSize())
                .totalElements(mineralPage.getTotalElements())
                .totalPages(mineralPage.getTotalPages())
                .isLastPage(mineralPage.isLast())
                .data(minerals)
                .build();
    }

    @Override
    public HttpResponse getMineralByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword5 = null;

        try {
            keyword5 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword5 = null;
        }


        Page<Mineral> mineralPage = mineralRepository.findByMineralNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrAtrNameContainingIgnoreCaseOrHsnCodeContainingIgnoreCaseOrMineralId(pageable,keyword,keyword,keyword,keyword,keyword5);

        List<MineralDto> minerals = appUtilsMines.mineralsToDtos(mineralPage.getContent());

        return HttpResponse.builder()
                .pageNumber(mineralPage.getNumber())
                .pageSize(mineralPage.getSize())
                .totalElements(mineralPage.getTotalElements())
                .totalPages(mineralPage.getTotalPages())
                .isLastPage(mineralPage.isLast())
                .data(minerals)
                .build();
    }
}
