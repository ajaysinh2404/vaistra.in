package com.vaistra.master.service.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.StateDto;

import java.util.List;

public interface StateService {
    public String addState(StateDto stateDto);

    public String updateState(Integer stateId, StateDto stateDto);

    public String deleteState(Integer stateId);

    public HttpResponse getState(int pageNo, int pageSize, String sortBy, String sortDirection);

    public List<StateDto> getStateByCountry(Integer countryId);

    public HttpResponse getStateByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);


}