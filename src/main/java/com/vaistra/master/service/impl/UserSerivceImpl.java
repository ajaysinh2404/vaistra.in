package com.vaistra.master.service.impl;

import com.vaistra.master.dto.UserDto;
import com.vaistra.master.entity.User;
import com.vaistra.master.exception.DuplicateEntryException;
import com.vaistra.master.exception.NoDataFoundException;
import com.vaistra.master.exception.ResourceNotFoundException;
import com.vaistra.master.exception.UnauthorizedException;
import com.vaistra.master.repository.UserRepository;
import com.vaistra.master.service.UserService;
import com.vaistra.master.utils.cscv_master.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSerivceImpl implements UserService {

    private final AppUtils appUtils;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSerivceImpl(AppUtils appUtils, UserRepository userRepository, PasswordEncoder passwordEncoder){

        this.appUtils = appUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String addUser(UserDto userDto) {
        if (userRepository.existsByEmailIgnoreCase(userDto.getEmail())){
            throw new DuplicateEntryException("Email: " + userDto.getEmail() + " already exist in current record...!");
        }

        User user = new User();

        user.setEmail(userDto.getEmail().trim());
        user.setPassword(passwordEncoder.encode(userDto.getPassword().trim()));
        user.setName(userDto.getName().trim());

        userRepository.save(user);

        return "User Registered successfully.";
    }

    @Override
    public String updateUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Email Id not found with given id: " + id));

        User userWithSameemail = userRepository.findByEmailIgnoreCase(user.getEmail().trim());

        if(userWithSameemail != null && !userWithSameemail.getUserId().equals(user.getUserId())){
            throw new DuplicateEntryException("Email: " + userDto.getEmail() + " is already exist in current record...!");
        }

        if(!user.getEmail().equals(userDto.getEmail()) || !userDto.getEmail().equals("admin@gmail.com")) {
            throw new UnauthorizedException("Email: " + userDto.getEmail() + " is not authorize to perform this operation");
        }
        user.setEmail(userDto.getEmail().trim());
        user.setPassword(passwordEncoder.encode(userDto.getPassword().trim()));
        user.setName(userDto.getName().trim());

        userRepository.save(user);

        return "User data updated successfully.";

    }

    @Override
    public String deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Email Id not found with given id: " + id));
        userRepository.deleteById(id);

        return "User Id: " + id + " deleted successfully.";
    }

    @Override
    public List<UserDto> getUser(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


        Page<User> users = userRepository.findAll(pageable);

        if(users.isEmpty())
            throw new NoDataFoundException("Sorry, No data found...!");

        return appUtils.usersToDtos(users.getContent());
    }

}
