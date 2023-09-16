package com.vaistra.master.service.mines_master.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.VillageDto;
import com.vaistra.master.dto.mines_master.VehicleDto;
import com.vaistra.master.entity.cscv_master.Village;
import com.vaistra.master.entity.mines_master.Vehicle;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.mines_master.VehicleRepository;
import com.vaistra.master.service.mines_master.VehicleService;
import com.vaistra.master.utils.mines_master.AppUtils_mines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final AppUtils_mines appUtilsMines;

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(AppUtils_mines appUtilsMines, VehicleRepository vehicleRepository){
        this.appUtilsMines = appUtilsMines;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public String addVehicle(VehicleDto vehicleDto) {
        if(vehicleRepository.existsByVehicleNameIgnoreCase(vehicleDto.getVehicleName().trim())){
            throw new DuplicateEntryException("Vehicle with name: " + vehicleDto.getVehicleName() + " already exist in current record.");
        }

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleName(vehicleDto.getVehicleName().trim());

        vehicleRepository.save(vehicle);

        return "Record added successfully.";
    }

    @Override
    public String updateVehicle(Integer vehicleId, VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()->new ResourceNotFoundException("Vehicle not found with given id: " + vehicleId));

        Vehicle vehicleWithSameName = vehicleRepository.findByVehicleNameIgnoreCase(vehicleDto.getVehicleName().trim());

        if(vehicleWithSameName != null && !vehicleWithSameName.getVehicleId().equals(vehicle.getVehicleId())){
            throw new DuplicateEntryException("Vehicle : " + vehicleDto.getVehicleName() + " is already exist in current record...!");
        }

        vehicle.setVehicleName(vehicleDto.getVehicleName().trim());

        vehicleRepository.save(vehicle);

        return "Record updated successfully.";
    }

    @Override
    public String deleteVehicle(Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()->new ResourceNotFoundException("Vehicle not found with given id: " + vehicleId));
        vehicleRepository.deleteById(vehicleId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getVehicle(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Vehicle> vehiclePage = vehicleRepository.findAll(pageable);

        List<VehicleDto> vehicles = appUtilsMines.vehiclesToDtos(vehiclePage.getContent());

        return HttpResponse.builder()
                .pageNumber(vehiclePage.getNumber())
                .pageSize(vehiclePage.getSize())
                .totalElements(vehiclePage.getTotalElements())
                .totalPages(vehiclePage.getTotalPages())
                .isLastPage(vehiclePage.isLast())
                .data(vehicles)
                .build();
    }

    @Override
    public HttpResponse getVehicleByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword2 = null;

        try {
            keyword2 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword2 = null;
        }

        Page<Vehicle> vehiclePage = vehicleRepository.findByVehicleNameContainingIgnoreCaseOrVehicleId(pageable,keyword,keyword2);


        List<VehicleDto> vehicles = appUtilsMines.vehiclesToDtos(vehiclePage.getContent());

        return HttpResponse.builder()
                .pageNumber(vehiclePage.getNumber())
                .pageSize(vehiclePage.getSize())
                .totalElements(vehiclePage.getTotalElements())
                .totalPages(vehiclePage.getTotalPages())
                .isLastPage(vehiclePage.isLast())
                .data(vehicles)
                .build();
    }
}
