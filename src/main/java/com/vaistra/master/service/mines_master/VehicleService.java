package com.vaistra.master.service.mines_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.VehicleDto;

public interface VehicleService {
    public String addVehicle(VehicleDto vehicleDto);

    public String updateVehicle(Integer vehicleId, VehicleDto vehicleDto);

    public String deleteVehicle(Integer vehicleId);

    public HttpResponse getVehicle(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getVehicleByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);
}
