package com.vaistra.master.service.bank_manage.impl;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.bank_manage.BankBranchDto;
import com.vaistra.master.dto.bank_manage.BankBranchDto_Update;
import com.vaistra.master.entity.bank_manage.Bank;
import com.vaistra.master.entity.bank_manage.BankBranch;
import com.vaistra.master.entity.cscv_master.District;
import com.vaistra.master.entity.cscv_master.State;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.NoDataFoundException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.bank_manage.BankBranchRepository;
import com.vaistra.master.repository.bank_manage.BankRepository;
import com.vaistra.master.repository.cscv_master.DistrictRepository;
import com.vaistra.master.repository.cscv_master.StateRepository;
import com.vaistra.master.service.bank_manage.BankBranchService;
import com.vaistra.master.utils.bank_manage.AppUtils_Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankBranchServiceImpl implements BankBranchService {
    private final AppUtils_Bank appUtils_bank;

    private final BankRepository bankRepository;

    private final StateRepository stateRepository;

    private final DistrictRepository districtRepository;

    private final BankBranchRepository bankBranchRepository;

    public BankBranchServiceImpl(AppUtils_Bank appUtilsBank, BankRepository bankRepository, StateRepository stateRepository, DistrictRepository districtRepository, BankBranchRepository bankBranchRepository){
        this.appUtils_bank = appUtilsBank;
        this.bankRepository = bankRepository;
        this.stateRepository = stateRepository;
        this.districtRepository = districtRepository;
        this.bankBranchRepository = bankBranchRepository;
    }

    @Override
    public String addBankBranch(BankBranchDto bankBranchDto) {
        if(bankBranchRepository.existsByBranchNameIgnoreCase(bankBranchDto.getBranchName().trim())){
            throw new DuplicateEntryException("Branch name: " + bankBranchDto.getBranchName() + " already exist in current record.");
        }

        if(bankBranchRepository.existsByBranchCodeIgnoreCase(bankBranchDto.getBranchCode().trim())){
            throw new DuplicateEntryException("Branch Code: " + bankBranchDto.getBranchCode() + " already exist in current record.");
        }

        if(bankBranchRepository.existsByBranchIfscIgnoreCase(bankBranchDto.getBranchIfsc().trim())){
            throw new DuplicateEntryException("Branch IFSC Code: " + bankBranchDto.getBranchIfsc() + " already exist in current record.");
        }

        if(bankBranchRepository.existsByBranchMicrIgnoreCase(bankBranchDto.getBranchMicr().trim())){
            throw new DuplicateEntryException("Branch MICR Code: " + bankBranchDto.getBranchMicr() + " already exist in current record.");
        }

        BankBranch bankBranch = new BankBranch();

        Bank bank = bankRepository.findById(bankBranchDto.getBankId()).orElseThrow(()->new ResourceNotFoundException("Bank not found with given id: " + bankBranchDto.getBankId()));
        State state = stateRepository.findById(bankBranchDto.getStateId()).orElseThrow(()->new ResourceNotFoundException("State not found with given id: " + bankBranchDto.getStateId()));
        District district = districtRepository.findById(bankBranchDto.getDistrictId()).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + bankBranchDto.getDistrictId()));

        bankBranch.setBank(bank);
        bankBranch.setState(state);
        bankBranch.setDistrict(district);
        bankBranch.setBranchName(bankBranchDto.getBranchName().trim());
        bankBranch.setBranchCode(bankBranchDto.getBranchCode().trim());
        bankBranch.setBranchAddress(bankBranchDto.getBranchAddress().trim());
        bankBranch.setBranchIfsc(bankBranchDto.getBranchIfsc().trim());
        bankBranch.setBranchPhoneNumber(bankBranchDto.getBranchPhoneNumber().trim());
        bankBranch.setBranchMicr(bankBranchDto.getBranchMicr().trim());
        bankBranch.setFromTiming(bankBranchDto.getFromTiming());
        bankBranch.setToTiming(bankBranchDto.getToTiming());
        bankBranch.setIsActive(bankBranchDto.getIsActive());

        bankBranchRepository.save(bankBranch);

        return "Record added successfully.";
    }

    @Override
    public String updateBankBranch(Integer branchId, BankBranchDto_Update bankBranchDto) {
        BankBranch bankBranch = bankBranchRepository.findById(branchId).orElseThrow(()->new ResourceNotFoundException("Bank Branch not found with given id: " + branchId));

        if (bankBranchDto.getBranchName()!=null){
            BankBranch bankBranchWithSameName = bankBranchRepository.findByBranchNameIgnoreCase(bankBranchDto.getBranchName().trim());

            if(bankBranchWithSameName != null && !bankBranchWithSameName.getBranchId().equals(bankBranch.getBranchId())){
                throw new DuplicateEntryException("Bank Branch: " + bankBranchDto.getBranchName() + " is already exist in current record...!");
            }
            bankBranch.setBranchName(bankBranchDto.getBranchName().trim());
        }

        if(bankBranchDto.getBranchCode()!=null){
            BankBranch bankBranchCodeWithSame = bankBranchRepository.findByBranchCodeIgnoreCase(bankBranchDto.getBranchCode().trim());

            if(bankBranchCodeWithSame != null && !bankBranchCodeWithSame.getBranchId().equals(bankBranch.getBranchId())){
                throw new DuplicateEntryException("Bank Branch Code: " + bankBranchDto.getBranchCode() + " is already exist in current record...!");
            }
            bankBranch.setBranchCode(bankBranchDto.getBranchCode().trim());
        }

        if(bankBranchDto.getBranchIfsc()!=null){
            BankBranch bankBranchIFSCCodeWithSame = bankBranchRepository.findByBranchIfscIgnoreCase(bankBranchDto.getBranchIfsc().trim());

            if(bankBranchIFSCCodeWithSame != null && !bankBranchIFSCCodeWithSame.getBranchId().equals(bankBranch.getBranchId())){
                throw new DuplicateEntryException("Bank Branch IFSC Code: " + bankBranchDto.getBranchIfsc() + " is already exist in current record...!");
            }
            bankBranch.setBranchIfsc(bankBranchDto.getBranchIfsc().trim());
        }

        if (bankBranchDto.getBranchMicr()!=null){
            BankBranch bankBranchMICRCodeWithSame = bankBranchRepository.findByBranchMicrIgnoreCase(bankBranchDto.getBranchMicr().trim());

            if(bankBranchMICRCodeWithSame != null && !bankBranchMICRCodeWithSame.getBranchId().equals(bankBranch.getBranchId())){
                throw new DuplicateEntryException("Bank Branch MICR Code: " + bankBranchDto.getBranchMicr() + " is already exist in current record...!");
            }
            bankBranch.setBranchMicr(bankBranchDto.getBranchMicr().trim());

        }


        if (bankBranchDto.getBankId()!=null){
            Bank bank = bankRepository.findById(bankBranchDto.getBankId()).orElseThrow(()->new ResourceNotFoundException("Bank not found with given id: " + bankBranchDto.getBankId()));
            bankBranch.setBank(bank);
        }

        if(bankBranchDto.getStateId()!=null){
            State state = stateRepository.findById(bankBranchDto.getStateId()).orElseThrow(()->new ResourceNotFoundException("State not found with given id: " + bankBranchDto.getStateId()));
            bankBranch.setState(state);
        }

        if(bankBranchDto.getDistrictId()!=null){
            District district = districtRepository.findById(bankBranchDto.getDistrictId()).orElseThrow(()->new ResourceNotFoundException("District not found with given id: " + bankBranchDto.getDistrictId()));
            bankBranch.setDistrict(district);
        }

        if (bankBranchDto.getBranchAddress()!=null)
            bankBranch.setBranchAddress(bankBranchDto.getBranchAddress().trim());

        if (bankBranchDto.getBranchPhoneNumber()!=null)
            bankBranch.setBranchPhoneNumber(bankBranchDto.getBranchPhoneNumber().trim());

        if (bankBranchDto.getFromTiming()!=null)
            bankBranch.setFromTiming(bankBranchDto.getFromTiming());

        if (bankBranchDto.getToTiming()!=null)
            bankBranch.setToTiming(bankBranchDto.getToTiming());

        if (bankBranchDto.getIsActive()!=null)
            bankBranch.setIsActive(bankBranchDto.getIsActive());

        bankBranchRepository.save(bankBranch);

        return "Record updated successfully.";
    }

    @Override
    public String deleteBankBranch(Integer branchId) {
        BankBranch bankBranch = bankBranchRepository.findById(branchId).orElseThrow(()->new ResourceNotFoundException("Bank Branch not found with given id: " + branchId));
        bankBranchRepository.deleteById(branchId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getBankBranch(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<BankBranch> bankBranchPage = bankBranchRepository.findAll(pageable);

        List<BankBranchDto> bankBranches = appUtils_bank.bankBranchesToDtos(bankBranchPage.getContent());

        return HttpResponse.builder()
                .pageNumber(bankBranchPage.getNumber())
                .pageSize(bankBranchPage.getSize())
                .totalElements(bankBranchPage.getTotalElements())
                .totalPages(bankBranchPage.getTotalPages())
                .isLastPage(bankBranchPage.isLast())
                .data(bankBranches)
                .build();
    }

    @Transactional
    @Override
    public HttpResponse getBankBranchByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword9 = null;
        Boolean keyword10 = null;

        try {
            keyword9 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword9 = null;
        }

        if(keyword.equalsIgnoreCase("true"))
            keyword10 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword10 = Boolean.FALSE;
        }

       Page<BankBranch> bankBranchPage = bankBranchRepository.findByBank_BankLongNameContainingIgnoreCaseOrState_StateNameContainingIgnoreCaseOrDistrict_DistrictNameContainingIgnoreCaseOrBranchNameContainingIgnoreCaseOrBranchCodeContainingIgnoreCaseOrBranchAddressContainingIgnoreCaseOrBranchIfscContainingIgnoreCaseOrBranchPhoneNumberContainingIgnoreCaseOrBranchIdOrIsActive(pageable,keyword,keyword,keyword,keyword,keyword,keyword,keyword,keyword,keyword9,keyword10);

        List<BankBranchDto> bankBranches = appUtils_bank.bankBranchesToDtos(bankBranchPage.getContent());

        return HttpResponse.builder()
                .pageNumber(bankBranchPage.getNumber())
                .pageSize(bankBranchPage.getSize())
                .totalElements(bankBranchPage.getTotalElements())
                .totalPages(bankBranchPage.getTotalPages())
                .isLastPage(bankBranchPage.isLast())
                .data(bankBranches)
                .build();

    }

    @Transactional
    @Override
    public List<BankBranchDto> getAllActiveBankBranch() {
        List<BankBranch> bankBranches = bankBranchRepository.findAllByIsActive(true);

        if (bankBranches.isEmpty())
            throw new NoDataFoundException("Bank Branch Data not available...!");

        return appUtils_bank.bankBranchesToDtos(bankBranches);
    }
}
