package com.vaistra.master.service.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.SubDistrictDto;

import java.util.List;

public interface SubDistrictService {
    public String addSubDistrict(SubDistrictDto subDistrictDto);

    public String updateSubDistrict(Integer subDistrictId, SubDistrictDto subDistrictDto);

    public String deleteSubDistrict(Integer subDistrictId);

    public HttpResponse getSubDistrict(int pageNo, int pageSize, String sortBy, String sortDirection);

    public List<SubDistrictDto> getSubDistrictByDistrict(Integer districtId);

    public HttpResponse getSubDistrictByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

}
