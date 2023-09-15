package com.vaistra.master.service.bank_manage;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.bank_manage.BankBranchDto;
import com.vaistra.master.dto.bank_manage.BankDto;

import java.util.List;

public interface BankBranchService {
    public String addBankBranch(BankBranchDto bankBranchDto);

    public String updateBankBranch(Integer bankBranchId, BankBranchDto bankBranchDto);

    public String deleteBankBranch(Integer bankBranchId);

    public HttpResponse getBankBranch(int pageNo, int pageSize, String sortBy, String sortDirection);

    public HttpResponse getBankBranchByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword);

    public List<BankBranchDto> getAllActiveBankBranch();

}