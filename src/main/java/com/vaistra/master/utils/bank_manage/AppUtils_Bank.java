package com.vaistra.master.utils.bank_manage;

import com.vaistra.master.dto.bank_manage.BankDto;
import com.vaistra.master.dto.mines_master.VehicleDto;
import com.vaistra.master.entity.bank_manage.Bank;
import com.vaistra.master.entity.mines_master.Vehicle;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUtils_Bank {
    private final ModelMapper modelMapper;

    @Autowired
    public AppUtils_Bank(ModelMapper modelMapper){
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
}
