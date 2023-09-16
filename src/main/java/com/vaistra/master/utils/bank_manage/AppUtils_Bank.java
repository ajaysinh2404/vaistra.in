package com.vaistra.master.utils.bank_manage;

import com.vaistra.master.dto.bank_manage.BankBranchDto;
import com.vaistra.master.dto.bank_manage.BankDto;
import com.vaistra.master.dto.cscv_master.VillageDto;
import com.vaistra.master.entity.bank_manage.Bank;
import com.vaistra.master.entity.bank_manage.BankBranch;
import com.vaistra.master.entity.cscv_master.Village;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUtils_Bank {
    private final ModelMapper modelMapper;

    @Autowired
    public AppUtils_Bank(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //____________________________________________________________________Bank Utility Methods Start_____________________________________________________

    public BankDto bankToDto(Bank bank) {

        return this.modelMapper.map(bank,BankDto.class);
    }

    public Bank dtoToBank(BankDto bankDto) {

        return this.modelMapper.map(bankDto,Bank.class);
    }

    public List<BankDto> banksToDtos(List<Bank> banks) {
        java.lang.reflect.Type targetListType = new TypeToken<List<BankDto>>() {}.getType();
        return modelMapper.map(banks, targetListType);
    }

    public List<Bank> dtosToBanks(List<BankDto> dtos) {
        java.lang.reflect.Type targetListType = new TypeToken<List<Bank>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }

    public boolean isSupportedExtension(String ext){
        int i = ext.lastIndexOf(".");

        String extension = "";

        if(i != -1){
            extension = ext.substring(i + 1);
        }

        return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("JPG") || extension.equals("JPEG")  || extension.equals("PNG");
    }

    //____________________________________________________________________Bank Utility Methods End_______________________________________________________

    //____________________________________________________________________Bank Branch Utility Methods Start_____________________________________________________

    public BankBranchDto bankBranchToDto(BankBranch bankBranch) {
        BankBranchDto bankBranchDto = new BankBranchDto();

        bankBranchDto.setBranchId(bankBranch.getBranchId());
        bankBranchDto.setBankId(bankBranch.getBank().getBankId());
        bankBranchDto.setBankName(bankBranch.getBank().getBankLongName());
        bankBranchDto.setStateId(bankBranch.getState().getStateId());
        bankBranchDto.setStateName(bankBranch.getState().getStateName());
        bankBranchDto.setDistrictId(bankBranch.getDistrict().getDistrictId());
        bankBranchDto.setDistrictName(bankBranch.getDistrict().getDistrictName());
        bankBranchDto.setBranchName(bankBranch.getBranchName());
        bankBranchDto.setBranchCode(bankBranch.getBranchCode());
        bankBranchDto.setBranchAddress(bankBranch.getBranchAddress());
        bankBranchDto.setBranchIfsc(bankBranch.getBranchIfsc());
        bankBranchDto.setBranchPhoneNumber(bankBranch.getBranchPhoneNumber());
        bankBranchDto.setBranchMicr(bankBranch.getBranchMicr());
        bankBranchDto.setFromTiming(bankBranch.getFromTiming());
        bankBranchDto.setToTiming(bankBranch.getToTiming());
        bankBranchDto.setIsActive(bankBranch.getIsActive());

        return bankBranchDto;
    }

    public List<BankBranchDto> bankBranchesToDtos(List<BankBranch> bankBranches) {
        return bankBranches.stream()
                .map(this::bankBranchToDto)
                .collect(Collectors.toList());
    }



}
