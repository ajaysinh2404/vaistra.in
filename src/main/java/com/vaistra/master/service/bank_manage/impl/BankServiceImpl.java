package com.vaistra.master.service.bank_manage.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.bank_manage.BankDto;
import com.vaistra.master.entity.bank_manage.Bank;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.repository.bank_manage.BankRepository;
import com.vaistra.master.service.bank_manage.BankService;
import com.vaistra.master.utils.bank_manage.AppUtils_Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class BankServiceImpl implements BankService {

    private final AppUtils_Bank appUtilsBank;

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(AppUtils_Bank appUtilsBank, BankRepository bankRepository){
        this.appUtilsBank = appUtilsBank;
        this.bankRepository = bankRepository;
    }

    @Override
    public String addBank(BankDto bankDto, MultipartFile file) throws IOException {
        String extension = file.getOriginalFilename();
        if(bankRepository.existsByBankShortNameIgnoreCase(bankDto.getBankShortName())){
            throw new DuplicateEntryException("Short name: " + bankDto.getBankShortName() + " already exist in current record.");
        }

        if(bankRepository.existsByBankLongNameIgnoreCase(bankDto.getBankLongName())){
            throw new DuplicateEntryException("Long name: " + bankDto.getBankLongName() + " already exist in current record.");
        }
        if(file.isEmpty()){
            throw new ResourceNotFoundException("Logo file not found...!");
        }
        assert extension != null;
        if(!appUtilsBank.isSupportedExtension(extension)){
            throw new ResourceNotFoundException("Only JPG,PNG allowed..!");
        }

        Bank bank = new Bank();

        bank.setBankLogo(file.getBytes());
        bank.setBankLongName(bankDto.getBankLongName());
        bank.setBankShortName(bankDto.getBankShortName());
        bank.setIsActive(bankDto.getIsActive());

        bankRepository.save(bank);

        return "Record added successfully.";
    }

    @Override
    public String updateBank(Integer bankId, BankDto bankDto, MultipartFile file) throws IOException {
        String extension = file.getOriginalFilename();

        Bank bank = bankRepository.findById(bankId).orElseThrow(()->new ResourceNotFoundException("Bank not found with given id: " + bankId));

        Bank bankWithSameName = bankRepository.findByBankLongNameIgnoreCase(bankDto.getBankLongName());

        if(bankWithSameName != null && !bankWithSameName.getBankId().equals(bank.getBankId())){
            throw new DuplicateEntryException("Bank : " + bankDto.getBankLongName() + " is already exist in current record...!");
        }

        Bank bankShortNameWithSameName = bankRepository.findByBankShortNameIgnoreCase(bankDto.getBankShortName());

        if(bankShortNameWithSameName != null && !bankShortNameWithSameName.getBankId().equals(bank.getBankId())){
            throw new DuplicateEntryException("Bank short name : " + bankDto.getBankShortName() + " is already exist in current record...!");
        }

        if(file.isEmpty()){
            throw new ResourceNotFoundException("Logo file not found...!");
        }

        assert extension != null;
        if(!appUtilsBank.isSupportedExtension(extension)){
            throw new ResourceNotFoundException("Only JPG,PNG allowed..!");
        }

        bank.setBankLogo(file.getBytes());
        bank.setBankLongName(bankDto.getBankLongName());
        bank.setBankShortName(bankDto.getBankShortName());
        bank.setIsActive(bankDto.getIsActive());

        bankRepository.save(bank);

        return "Record updated successfully.";
    }

    @Override
    public String deleteBank(Integer bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(()->new ResourceNotFoundException("Bank not found with given id: " + bankId));
        bankRepository.deleteById(bankId);
        return "Record deleted successfully.";
    }

    @Override
    public HttpResponse getBank(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Bank> bankPage = bankRepository.findAll(pageable);

        List<BankDto> banks = appUtilsBank.banksToDtos(bankPage.getContent());

        return HttpResponse.builder()
                .pageNumber(bankPage.getNumber())
                .pageSize(bankPage.getSize())
                .totalElements(bankPage.getTotalElements())
                .totalPages(bankPage.getTotalPages())
                .isLastPage(bankPage.isLast())
                .data(banks)
                .build();
    }

    @Override
    public HttpResponse getBankByKeyword(int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, Integer.MAX_VALUE, sort);

        Integer keyword3 = null;
        Boolean keyword4 = null;

        try {
            keyword3 = Integer.parseInt(keyword);
        } catch (NumberFormatException e) {
            keyword3 = null;
        }

        if(keyword.equalsIgnoreCase("true"))
            keyword4 = Boolean.TRUE;
        else if (keyword.equalsIgnoreCase("false")) {
            keyword4 = Boolean.FALSE;
        }

        Page<Bank> bankPage = bankRepository.findByBankShortNameContainingIgnoreCaseOrBankLongNameContainingIgnoreCaseOrBankIdOrIsActive(pageable,keyword,keyword,keyword3,keyword4);

        List<BankDto> banks = appUtilsBank.banksToDtos(bankPage.getContent());

        return HttpResponse.builder()
                .pageNumber(bankPage.getNumber())
                .pageSize(bankPage.getSize())
                .totalElements(bankPage.getTotalElements())
                .totalPages(bankPage.getTotalPages())
                .isLastPage(bankPage.isLast())
                .data(banks)
                .build();
    }

    @Override
    public byte[] getBankLogo(Integer bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(()->new ResourceNotFoundException("Bank not found with given id: " + bankId));
        return bank.getBankLogo();
    }
}
