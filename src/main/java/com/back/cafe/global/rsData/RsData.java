package com.back.cafe.global.rsData;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 요청요청에 대한 응답 시 공통으로 사용할 객채 지정
 * @param msg 응답 메시지
 * @param resultCode 응답 코드
 * @param data 응답 데이터
 * @param <T>
 */
public record RsData<T>(
        String msg,
        String resultCode,
        T data
) {
    public RsData(String msg, String resultCode) {
        this(msg, resultCode, null);
    }

    @JsonIgnore
    public int getStatusCode() {
        return Integer.parseInt(resultCode.split("-")[0]);
    }
}