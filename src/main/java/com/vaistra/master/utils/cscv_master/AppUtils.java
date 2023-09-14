package com.vaistra.master.utils.cscv_master;

import com.vaistra.master.dto.UserDto;
import com.vaistra.master.dto.cscv_master.*;
import com.vaistra.master.entity.User;
import com.vaistra.master.entity.cscv_master.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class AppUtils {
    private final ModelMapper modelMapper;

    @Autowired
    public AppUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<Village, VillageDto>() {
            @Override
            protected void configure() {
                map().setSubDistrictName(source.getSubDistrict().getSubDistrictName());
                map().setSubDistrictId(source.getSubDistrict().getSubDistrictId());
            }
        });

    }

    //____________________________________________________________________User Utility Methods Start_____________________________________________________
    public UserDto userToDto(User user) {

        return this.modelMapper.map(user,UserDto.class);
    }

    public User dtoToUser(UserDto userDto) {

        return this.modelMapper.map(userDto,User.class);
    }

    public List<UserDto> usersToDtos(List<User> users) {

        java.lang.reflect.Type targetListType = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(users, targetListType);
    }

    public List<User> dtosToUsers(List<UserDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<User>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________User Utility Methods End_______________________________________________________

    //____________________________________________________________________Country Utility Methods Start_____________________________________________________

    public CountryDto countryToDto(Country country) {

        return this.modelMapper.map(country,CountryDto.class);
    }

    public Country dtoToCountry(CountryDto countryDto) {

        return this.modelMapper.map(countryDto,Country.class);
    }

    public List<CountryDto> countriesToDtos(List<Country> countries) {

        java.lang.reflect.Type targetListType = new TypeToken<List<CountryDto>>() {}.getType();
        return modelMapper.map(countries, targetListType);
    }

    public List<Country> dtosToCountries(List<CountryDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<Country>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________Country Utility Methods End_______________________________________________________


    //____________________________________________________________________State Utility Methods Start_____________________________________________________

    public StateDto stateToDto(State state) {

        return this.modelMapper.map(state,StateDto.class);
    }

    public State dtoToState(StateDto stateDto) {

        return this.modelMapper.map(stateDto,State.class);
    }

    public List<StateDto> statesToDtos(List<State> states) {

        java.lang.reflect.Type targetListType = new TypeToken<List<StateDto>>() {}.getType();
        return modelMapper.map(states, targetListType);
    }

    public List<State> dtosToStates(List<StateDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<State>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________State Utility Methods End_______________________________________________________

    //____________________________________________________________________District Utility Methods Start_____________________________________________________

    public DistrictDto districtToDto(District district) {

        return this.modelMapper.map(district,DistrictDto.class);
    }

    public District dtoToDistrict(DistrictDto districtDto) {

        return this.modelMapper.map(districtDto,District.class);
    }

    public List<DistrictDto> districtsToDtos(List<District> districts) {

        java.lang.reflect.Type targetListType = new TypeToken<List<DistrictDto>>() {}.getType();
        return modelMapper.map(districts, targetListType);
    }

    public List<District> dtosToDistricts(List<DistrictDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<District>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________District Utility Methods End_______________________________________________________


    //____________________________________________________________________Sub-district Utility Methods Start_____________________________________________________

    public SubDistrictDto subDistrictToDto(SubDistrict subDistrict) {

        return this.modelMapper.map(subDistrict,SubDistrictDto.class);
    }

    public SubDistrict dtoToSubDistrict(SubDistrictDto subDistrictDto) {

        return this.modelMapper.map(subDistrictDto,SubDistrict.class);
    }

    public List<SubDistrictDto> subDistrictsToDtos(List<SubDistrict> subDistricts) {

        java.lang.reflect.Type targetListType = new TypeToken<List<SubDistrictDto>>() {}.getType();
        return modelMapper.map(subDistricts, targetListType);
    }

    public List<SubDistrict> dtosToSubDistricts(List<SubDistrictDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<SubDistrict>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________Sub-district Utility Methods End_____________________________________________________


    //____________________________________________________________________Village Utility Methods Start_____________________________________________________

    public VillageDto villageToDto(Village village) {
        return this.modelMapper.map(village, VillageDto.class);
    }



    public Village dtoToVillage(VillageDto villageDto) {

        return this.modelMapper.map(villageDto,Village.class);
    }
    public List<VillageDto> villagesToDtos(List<Village> villages) {
        return villages.stream()
                .map(this::villageToDto)
                .collect(Collectors.toList());
    }


    public List<Village> dtosToVillages(List<VillageDto> dtos) {

        java.lang.reflect.Type targetListType = new TypeToken<List<Village>>() {}.getType();
        return modelMapper.map(dtos, targetListType);

    }
    //____________________________________________________________________Village Utility Methods End_____________________________________________________

}
