package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dao.entity.AuthorityEntity;
import com.example.bookstoreapp.dao.entity.UserEntity;
import com.example.bookstoreapp.dao.repository.AuthorityRepository;
import com.example.bookstoreapp.dao.repository.UserRepository;
import com.example.bookstoreapp.mapper.UserMapper;
import com.example.bookstoreapp.model.dto.UserRegisterDto;
import com.example.bookstoreapp.model.exception.UniquenessViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.bookstoreapp.model.constants.ExceptionConstants.VALIDATION_EXCEPTION_CODE;
import static com.example.bookstoreapp.model.constants.ExceptionConstants.VALIDATION_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder encoder;


    public void registerUser(UserRegisterDto registerDto) {
        log.info("ActionLog.registerUser.start");

        var checkEmailAndUsername = userRepository.findByEmailOrUsername(registerDto.getEmail(), registerDto.getUsername());
        if (checkEmailAndUsername.isEmpty()) {
            AuthorityEntity authority = authorityRepository.findByAuthority("USER");
            UserEntity entity = UserMapper.mapRegisterDtoToEntity(registerDto);

            entity.setPassword(encoder.encode(entity.getPassword()));
            entity.setAuthorities(Set.of(authority));

            userRepository.save(entity);
            log.info("ActionLog.registerUser.success");
        } else {
            log.error("ActionLog.registerUser.error");
            throw new UniquenessViolationException(String.format(VALIDATION_MESSAGE, "email", "or", "username"), VALIDATION_EXCEPTION_CODE);
        }
    }


}
