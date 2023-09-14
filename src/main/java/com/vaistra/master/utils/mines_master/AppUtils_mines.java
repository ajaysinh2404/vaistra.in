package com.vaistra.master.utils.mines_master;

import com.vaistra.master.dto.mines_master.*;
import com.vaistra.master.entity.mines_master.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUtils_mines {

    private final ModelMapper modelMapper;

    public AppUtils_mines(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


    //____________________________________________________________________Vehicle Utility Methods Start_____________________________________________________

    public VehicleDto vehicleToDto(Vehicle vehicle) {

        return this.modelMapper.map(vehicle,VehicleDto.class);
    }

    public Vehicle dtoToVehicle(VehicleDto vehicleDto) {

        return this.modelMapper.map(vehicleDto,Vehicle.class);
    }

    public List<VehicleDto> vehiclesToDtos(List<Vehicle> vehicles) {

        java.lang.reflect.Type targetListType = new TypeToken<List<VehicleDto>>() {}.getType();
        return modelMapper.map(vehicles, targetListType);
    }

    public List<Vehicle> dtosToVehicles(List<VehicleDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<Vehicle>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________Vehicle Utility Methods End_______________________________________________________

    //____________________________________________________________________Minerals Utility Methods Start_____________________________________________________

    public MineralDto mineralToDto(Mineral mineral) {

        return this.modelMapper.map(mineral,MineralDto.class);
    }

    public Mineral dtoToMineral(MineralDto mineralDto) {

        return this.modelMapper.map(mineralDto,Mineral.class);
    }

    public List<MineralDto> mineralsToDtos(List<Mineral> minerals) {

        java.lang.reflect.Type targetListType = new TypeToken<List<MineralDto>>() {}.getType();
        return modelMapper.map(minerals, targetListType);
    }

    public List<Mineral> dtosToMinerals(List<MineralDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<Mineral>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________Minerals Utility Methods End_______________________________________________________

    //____________________________________________________________________Equipment Utility Methods Start_____________________________________________________

    public EquipmentDto equipmentToDto(Equipment equipment) {

        return this.modelMapper.map(equipment,EquipmentDto.class);
    }

    public Equipment dtoToEquipment(EquipmentDto equipmentDto) {

        return this.modelMapper.map(equipmentDto,Equipment.class);
    }

    public List<EquipmentDto> equipmentsToDtos(List<Equipment> equipments) {

        java.lang.reflect.Type targetListType = new TypeToken<List<EquipmentDto>>() {}.getType();
        return modelMapper.map(equipments, targetListType);
    }

    public List<EquipmentDto> dtosToEquipments(List<EquipmentDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<Equipment>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________Equipment Utility Methods End_______________________________________________________


    //____________________________________________________________________EntityType Utility Methods Start_____________________________________________________

    public EntityTypeDto entityTypeToDto(EntityType entityType) {

        return this.modelMapper.map(entityType,EntityTypeDto.class);
    }

    public EntityType dtoToEntityType(EntityTypeDto entityTypeDto) {

        return this.modelMapper.map(entityTypeDto,EntityType.class);
    }

    public List<EntityTypeDto> entityTypesToDtos(List<EntityType> entityTypes) {

        java.lang.reflect.Type targetListType = new TypeToken<List<EntityTypeDto>>() {}.getType();
        return modelMapper.map(entityTypes, targetListType);
    }

    public List<EntityTypeDto> dtosToEntityTypes(List<EntityTypeDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<EntityType>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________EntityType Utility Methods End_______________________________________________________


    //____________________________________________________________________Designation Utility Methods Start_____________________________________________________

    public DesignationDto designationToDto(Designation designation) {

        return this.modelMapper.map(designation,DesignationDto.class);
    }

    public Designation dtoToDesignation(DesignationDto designationDto) {

        return this.modelMapper.map(designationDto,Designation.class);
    }

    public List<DesignationDto> designationsToDtos(List<Designation> designations) {

        java.lang.reflect.Type targetListType = new TypeToken<List<DesignationDto>>() {}.getType();
        return modelMapper.map(designations, targetListType);
    }

    public List<DesignationDto> dtosToDesignations(List<DesignationDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<Designation>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________Designation Utility Methods End_______________________________________________________

}
