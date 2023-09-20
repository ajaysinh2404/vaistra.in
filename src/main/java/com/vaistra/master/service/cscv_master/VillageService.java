package com.vaistra.master.service.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.VillageDto;
import com.vaistra.master.dto.cscv_master.VillageDto_Update;

import java.util.List;

public interface VillageService {
    public String addVillage(VillageDto villageDto);

    public String updateVillage(Integer villageId, VillageDto_Update villageDto);

    public String deleteVillage(Integer villageId);

    public HttpResponse getVillage(int pageNo, int pageSize, String sortBy, String sortDirection);

    public List<VillageDto> getVillageBySubDistrict(Integer subDistrictId);

    public HttpResponse getVillageByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);
}
