package com.vaistra.master.service.mines_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.EquipmentDto;
import com.vaistra.master.entity.mines_master.Equipment;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.mines_master.EquipmentRepository;
import com.vaistra.master.service.mines_master.EquipmentService;
import com.vaistra.master.utils.mines_master.AppUtils_mines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final AppUtils_mines appUtilsMines;

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentServiceImpl(AppUtils_mines appUtilsMines, EquipmentRepository equipmentRepository){
        this.appUtilsMines = appUtilsMines;

        this.equipmentRepository = equipmentRepository;
    }
    @Override
    public String addEquipment(EquipmentDto equipmentDto) {
        if(equipmentRepository.existsByEquipmentNameIgnoreCase(equipmentDto.getEquipmentName().trim())){
            throw new DuplicateEntryException("Equipment with name: " + equipmentDto.getEquipmentName() + " already exist in current record.");
        }

        Equipment equipment = new Equipment();

        equipment.setEquipmentName(equipmentDto.getEquipmentName().trim());

        equipmentRepository.save(equipment);

        return "Record added successfully.";
    }

    @Override
    public String updateEquipment(Integer equipmentId, EquipmentDto equipmentDto) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(()->new ResourceNotFoundException("Equipment not found with given id: " + equipmentId));

        Equipment equipmentWithSameName = equipmentRepository.findByEquipmentNameIgnoreCase(equipmentDto.getEquipmentName().trim());

        if(equipmentWithSameName != null && !equipmentWithSameName.getEquipmentId().equals(equipment.getEquipmentId())){
            throw new DuplicateEntryException("Equipment : " + equipmentDto.getEquipmentName() + " is already exist in current record...!");
        }

        equipment.setEquipmentName(equipmentDto.getEquipmentName().trim());

        equipmentRepository.save(equipment);

        return "Record updated successfully.";
    }

    @Override
    public String deleteEquipment(Integer equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(()->new ResourceNotFoundException("Equipment not found with given id: " + equipmentId));
        equipmentRepository.deleteById(equipmentId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getEquipment(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Equipment> equipmentPage = equipmentRepository.findAll(pageable);

        List<EquipmentDto> equipments = appUtilsMines.equipmentsToDtos(equipmentPage.getContent());

        return HttpResponse.builder()
                .pageNumber(equipmentPage.getNumber())
                .pageSize(equipmentPage.getSize())
                .totalElements(equipmentPage.getTotalElements())
                .totalPages(equipmentPage.getTotalPages())
                .isLastPage(equipmentPage.isLast())
                .data(equipments)
                .build();
    }

    @Override
    public HttpResponse getEquipmentByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword2 = null;

        try {
            keyword2 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword2 = null;
        }

        Page<Equipment> equipmentPage = equipmentRepository.findByEquipmentNameContainingIgnoreCaseOrEquipmentId(pageable,keyword,keyword2);

        List<EquipmentDto> equipments = appUtilsMines.equipmentsToDtos(equipmentPage.getContent());

        return HttpResponse.builder()
                .pageNumber(equipmentPage.getNumber())
                .pageSize(equipmentPage.getSize())
                .totalElements(equipmentPage.getTotalElements())
                .totalPages(equipmentPage.getTotalPages())
                .isLastPage(equipmentPage.isLast())
                .data(equipments)
                .build();
    }
}
