package com.back.cafe.global.springDoc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Api 자동 문서화를 위한 Swagger
 */

@Configuration
// Swagger UI 화면 최상단에 표시될 전역 메타데이터(문서 제목, 버전, 설명)를 설정
@OpenAPIDefinition(info = @Info(title = "API 서버", version = "beta", description = "API 서버 문서입니다."))
public class SpringDoc {

    // 'apiV1'이라는 이름의 API 명세 그룹을 생성
    @Bean
    public GroupedOpenApi groupApiV1() {
        return GroupedOpenApi.builder()
                .group("apiV1") // Swagger UI 우측 상단 드롭다운에 표시될 그룹 이름
                .pathsToMatch("/api/v1/**") // '/api/v1/'으로 시작하는 API 경로만 모아서 표시. (실제 데이터 처리용 API)
                .build();
    }

    // 'home'이라는 이름의 API 명세 그룹을 생성
    @Bean
    public GroupedOpenApi groupController() {
        return GroupedOpenApi.builder()
                .group("home")
                .pathsToExclude("/api/**") // '/api/'로 시작하는 모든 경로를 이 문서에서 숨김 (보통 단순 화면 렌더링이나 공통 처리용 경로를 남길 때 사용한다고 함)
                .build();
    }
}