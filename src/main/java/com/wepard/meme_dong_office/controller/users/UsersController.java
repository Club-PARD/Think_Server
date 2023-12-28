package com.wepard.meme_dong_office.controller.users;

import com.wepard.meme_dong_office.dto.users.response.UsersResponseDTO;
import com.wepard.meme_dong_office.exception.CustomException;
import com.wepard.meme_dong_office.exception.constants.ExceptionCode;
import com.wepard.meme_dong_office.security.TokenProvider;
import com.wepard.meme_dong_office.service.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    private final TokenProvider tokenProvider;
    private final UsersService usersService;

    @Autowired
    public UsersController(
            TokenProvider tokenProvider,
            UsersService usersService
    ){
        this.tokenProvider = tokenProvider;
        this.usersService = usersService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUsers(
            @PathVariable final Long id,
            @RequestHeader(value = "Authorization") final String token
    ){

        final Long userId = Long.parseLong(
                tokenProvider.validate(token.substring(7))
        );

        //다른 사람의 정보 조회 막기
        if(!userId.equals(id)){
            throw new CustomException(ExceptionCode.INVALID_ACCESS);
        }

        return ResponseEntity.ok().body(
                usersService.getUsers(userId)
        );
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<?> updateUsers(@PathVariable Long userId){
        UsersResponseDTO user = UsersResponseDTO.builder()
                .id(1L)
                .name("name")
                .email("email@email.com")
                .build();
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUsers(@PathVariable Long userId){
        return ResponseEntity.noContent().build();
    }
}
