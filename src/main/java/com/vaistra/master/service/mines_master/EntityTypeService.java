package com.vaistra.master.service.mines_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.EntityTypeDto;
import com.vaistra.master.dto.mines_master.EquipmentDto;

public interface EntityTypeService {
    public String addEntityType(EntityTypeDto entityTypeDto);

    public String updateEntityType(Integer entityTypeId, EntityTypeDto entityTypeDto);

    public String deleteEntityType(Integer entityTypeId);

    public HttpResponse getEntityType(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getEntityTypeByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

}
