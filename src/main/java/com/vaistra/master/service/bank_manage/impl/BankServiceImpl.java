package com.vaistra.master.service.bank_manage.impl;

import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.bank_manage.BankDto;
import com.vaistra.master.dto.bank_manage.BankDto_Update;
import com.vaistra.master.entity.bank_manage.Bank;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.FileSizeExceedException;
import com.vaistra.master.exception.NoDataFoundException;
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
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public String addBank(BankDto bankDto, MultipartFile file) throws IOException {
        String extension = file.getOriginalFilename();
        if(bankRepository.existsByBankShortNameIgnoreCase(bankDto.getBankShortName().trim())){
            throw new DuplicateEntryException("Short name: " + bankDto.getBankShortName() + " already exist in current record.");
        }

        if(bankRepository.existsByBankLongNameIgnoreCase(bankDto.getBankLongName().trim())){
            throw new DuplicateEntryException("Long name: " + bankDto.getBankLongName() + " already exist in current record.");
        }
        if(file.isEmpty()){
            throw new ResourceNotFoundException("Logo file not found...!");
        }
        assert extension != null;
        if(!appUtilsBank.isSupportedExtension(extension)){
            throw new ResourceNotFoundException("Only JPG,PNG allowed..!");
        }

        long fileSizeLimit = 5120L;
        if (file.getSize() > fileSizeLimit) {
            throw new FileSizeExceedException("File size exceeds the limit (5KB).");
        }


        Bank bank = new Bank();

        bank.setBankLogo(file.getBytes());
        bank.setBankLongName(bankDto.getBankLongName().trim());
        bank.setBankShortName(bankDto.getBankShortName().trim());
        bank.setIsActive(bankDto.getIsActive());

        bankRepository.save(bank);

        return "Record added successfully.";
    }

    @Transactional
    @Override
    public String updateBank(Integer bankId, BankDto_Update bankDto, MultipartFile file) throws IOException {
        Bank bank = bankRepository.findById(bankId).orElseThrow(()->new ResourceNotFoundException("Bank not found with given id: " + bankId));

        if (bankDto.getBankLongName()!=null){
            Bank bankWithSameName = bankRepository.findByBankLongNameIgnoreCase(bankDto.getBankLongName().trim());

            if(bankWithSameName != null && !bankWithSameName.getBankId().equals(bank.getBankId())){
                throw new DuplicateEntryException("Bank : " + bankDto.getBankLongName() + " is already exist in current record...!");
            }
            bank.setBankLongName(bankDto.getBankLongName().trim());
        }

        if(bankDto.getBankShortName()!=null){
            Bank bankShortNameWithSameName = bankRepository.findByBankShortNameIgnoreCase(bankDto.getBankShortName().trim());

            if(bankShortNameWithSameName != null && !bankShortNameWithSameName.getBankId().equals(bank.getBankId())){
                throw new DuplicateEntryException("Bank short name : " + bankDto.getBankShortName() + " is already exist in current record...!");
            }
            bank.setBankShortName(bankDto.getBankShortName().trim());
        }

        if (!file.isEmpty()){
            String extension = file.getOriginalFilename();

            assert extension != null;
            if(!appUtilsBank.isSupportedExtension(extension)){
                throw new ResourceNotFoundException("Only JPG,PNG allowed..!");
            }

            long fileSizeLimit = 5120L;
            if (file.getSize() > fileSizeLimit) {
                throw new FileSizeExceedException("File size exceeds the limit (5KB).");
            }
            bank.setBankLogo(file.getBytes());

        }

        if (bankDto.getIsActive()!=null)
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

    @Transactional
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

    @Transactional
    @Override
    public List<BankDto> getAllActiveBank() {
        List<Bank> banks = bankRepository.findAllByIsActive(true);

        if (banks.isEmpty())
            throw new NoDataFoundException("Bank Data not available...!");

        return appUtilsBank.banksToDtos(banks);
    }
}
