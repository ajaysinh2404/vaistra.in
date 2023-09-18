package com.vaistra.master.service;

import com.vaistra.master.dto.UserDto;
import com.vaistra.master.dto.UserDto_Update;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.List;

public interface UserService {
    public String addUser(UserDto userDto);

    public String updateUser(Integer id, UserDto_Update userDtoUpdate, UserDetails userDetails);

    public String deleteUser(Integer id);

    public List<UserDto> getUser(int pageNo, int pageSize, String sortBy, String sortDirection);

}

