package com.vaistra.master.service.cscv_master;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.DistrictDto;
import com.vaistra.master.dto.cscv_master.DistrictDto_Update;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DistrictService {
    public String addDistrict(DistrictDto districtDto);

    public String updateDistrict(Integer districtId, DistrictDto_Update districtDto);

    public String deleteDistrict(Integer districtId);

    public HttpResponse getDistrict(int pageNo, int pageSize, String sortBy, String sortDirection);

    public List<DistrictDto> getDistrictByState(Integer stateId);

    public HttpResponse getDistrictByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);
    public String uploadDistrictCSV(MultipartFile file);


}
