package com.vaistra.master.service.mines_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.DesignationDto;
import com.vaistra.master.dto.mines_master.DesignationDto_Update;
import com.vaistra.master.dto.mines_master.EquipmentDto;

public interface DesignationService {
    public String addDesignation(DesignationDto designationDto);

    public String updateDesignation(Integer designationId, DesignationDto_Update designationDto);

    public String deleteDesignation(Integer designationId);

    public HttpResponse getDesignation(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getDesignationByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

}
