package com.gozip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_CONGITION_USERNAME(HttpStatus.BAD_REQUEST, "닉네임이 조건에 맞지 않습니다."),
    NOT_CONGITION_EMAIL(HttpStatus.BAD_REQUEST, "이메일이 조건에 맞지 않습니다."),
    NOT_CONGITION_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 조건에 맞지 않습니다."),
    NOT_CONGITION_INSERT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    AUTHORIZATION(HttpStatus.UNAUTHORIZED, "수정/삭제할 수 있는 권한이 없습니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "중복된 username 입니다"),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 email 입니다"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_MATCH_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일의 계정은 존재하지 않습니다."),
    INVALID_ADMIN_TOKEN(HttpStatus.BAD_REQUEST, "관리자 등록 토큰이 일치하지 않습니다."),
    NOT_FOUND_TOKEN (HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),
    NOT_INVALID_JWT (HttpStatus.NOT_FOUND, "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_TOKEN (HttpStatus.NOT_FOUND, "만료된 JWT 토큰 입니다."),
    UNSUPPORTED_TOKEN (HttpStatus.NOT_FOUND, "지원되지 않는 JWT 토큰 입니다."),
    WRONG_TOKEN (HttpStatus.NOT_FOUND, "잘못된 JWT 토큰 입니다.."),
    NO_AUTHORITY(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String msg;

}
