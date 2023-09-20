package com.vaistra.master.service.mines_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.VehicleDto;
import com.vaistra.master.dto.mines_master.VehicleDto_Update;

public interface VehicleService {
    public String addVehicle(VehicleDto vehicleDto);

    public String updateVehicle(Integer vehicleId, VehicleDto_Update vehicleDto);

    public String deleteVehicle(Integer vehicleId);

    public HttpResponse getVehicle(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getVehicleByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);
}
