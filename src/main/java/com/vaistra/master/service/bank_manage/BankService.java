package com.vaistra.master.service.bank_manage;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.bank_manage.BankDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BankService {

    public String addBank(BankDto bankDto, MultipartFile file) throws IOException;

    public String updateBank(Integer bankId, BankDto bankDto, MultipartFile file) throws IOException;

    public String deleteBank(Integer bankId);

    public HttpResponse getBank(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getBankByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

    public byte[] getBankLogo(Integer bankId);
}
