package com.example.bookstoreapp.mapper;


import com.example.bookstoreapp.dao.entity.UserEntity;
import com.example.bookstoreapp.model.dto.UserRegisterDto;


public  class UserMapper {

    public static UserEntity mapRegisterDtoToEntity(UserRegisterDto dto){
        return UserEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}
