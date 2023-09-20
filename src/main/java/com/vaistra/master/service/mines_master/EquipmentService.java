package com.vaistra.master.service.mines_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.EquipmentDto;
import com.vaistra.master.dto.mines_master.EquipmentDto_Update;
import com.vaistra.master.dto.mines_master.VehicleDto;

public interface EquipmentService {
    public String addEquipment(EquipmentDto equipmentDto);

    public String updateEquipment(Integer equipmentId, EquipmentDto_Update equipmentDto);

    public String deleteEquipment(Integer equipmentId);

    public HttpResponse getEquipment(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getEquipmentByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

}
