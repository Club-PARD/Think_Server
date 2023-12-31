package com.wepard.meme_dong_office.service.token;

import com.wepard.meme_dong_office.config.WebSecurityConfig;
import com.wepard.meme_dong_office.dto.token.request.TokenRequestDTO;
import com.wepard.meme_dong_office.dto.token.response.TokenResponseDTO;
import com.wepard.meme_dong_office.entity.users.Users;
import com.wepard.meme_dong_office.exception.CustomException;
import com.wepard.meme_dong_office.exception.constants.ExceptionCode;
import com.wepard.meme_dong_office.repository.UsersRepository;
import com.wepard.meme_dong_office.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final UsersRepository usersRepository;
    private final TokenProvider tokenProvider;
    private final WebSecurityConfig webSecurityConfig;

    public TokenResponseDTO signIn(
            final TokenRequestDTO tokenRequestDTO
    ){
        final Users users;
        final String email = tokenRequestDTO.getEmail();
        final String password = tokenRequestDTO.getPassword();

        if(StringUtils.containsAny(password, "'", "\"", "\\")){
            throw new CustomException(ExceptionCode.INVALID_INPUT);
        }

        try{
            users = usersRepository.findByEmail(email).get();
        } catch (NoSuchElementException ex){
            throw new CustomException(ExceptionCode.LOGIN_FAILED);
        }

        boolean isPasswordMatch = webSecurityConfig.getPasswordEncoder().matches(
                password,
                users.getHashedPassword()
        );

        // when password is invalid, 패스워드 틀렸을 때
        if(!isPasswordMatch){
            throw new CustomException(ExceptionCode.LOGIN_FAILED);
        }

        //accessToken expire time : 1 hour, 엑세스 토큰 유효 시간 : 1주
        final String accessToken = tokenProvider.createToken(users.getId(), 168);
        //accessToken expire time (second), 엑세스 토큰 유효 시간 (시간)
        final Integer exprTime = 168;

        final String tokenType = tokenProvider.getTokenType();

        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .tokenType(tokenType)
                .exprTime(exprTime)
                .userId(users.getId())
                .build();

    }
}
