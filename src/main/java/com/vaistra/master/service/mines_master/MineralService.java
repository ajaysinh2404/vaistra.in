package com.vaistra.master.service.mines_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.MineralDto;

public interface MineralService {
    public String addMineral(MineralDto mineralDto);

    public String updateMineral(Integer mineralId, MineralDto mineralDto);

    public String deleteMineral(Integer mineralId);

    public HttpResponse getMineral(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getMineralByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

}
