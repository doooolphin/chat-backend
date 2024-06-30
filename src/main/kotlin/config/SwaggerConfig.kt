package config

import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springdoc.core.properties.SwaggerUiConfigProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableWebMvc
class SwaggerConfig {
    private final val JWT_SCHEME_NAME = "Authorization"

    @Bean
    fun swaggerUiConfig(config: SwaggerUiConfigProperties): SwaggerUiConfigProperties {
        config.showCommonExtensions = true
        config.queryConfigEnabled = true // 레퍼런스 확인 결과 xss 보안문제가 있어서 4.1.12? 버전 이상부터는 막혀있는데 이전 버전에서는 비활성화하는걸로 권장해서 제거함, 쿼리 파라미터로 스웨거 관련 설정하는데 사용되는 정보임
        config.isEnabled = false //openapi 비활성화함
        config.isDisableSwaggerDefaultUrl = true // 기본경로 '/swagger-ui/index.html' 경로 막음
        config.isUseRootPath = false // 최상단경로 사용 비활성화
        return config
    }

    @Bean
    fun openApi(): OpenAPI {
        val info = Info()
        info.title = "채팅 API"
        info.version = "1.0"
        info.description = "채팅 API 문서입니다."

        return OpenAPI()
            .addServersItem(Server().url("http://localhost:8080").description("LOCAL"))
//            .addServersItem(Server().url("https://").description("PROD"))
            .components(setComponents())
            .info(info)
            .addSecurityItem(SecurityRequirement().addList(JWT_SCHEME_NAME))
    }

    private fun setComponents(): Components {
        val components = Components().addSecuritySchemes(
            JWT_SCHEME_NAME, SecurityScheme()
                .name(JWT_SCHEME_NAME)
                .type(SecurityScheme.Type.APIKEY)
                .`in`(SecurityScheme.In.HEADER)
                .scheme("apiKey")
                .bearerFormat(JWT_SCHEME_NAME)
        ).addParameters(
            JWT_SCHEME_NAME, Parameter().`in`(ParameterIn.HEADER.toString()).required(true)
                .schema(StringSchema().format("string"))
                .name(JWT_SCHEME_NAME)
        )
        return components
    }

    @Bean
    fun appApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("채팅 백엔드 api")
            .packagesToScan("com.seona.chat.backend.api")
            .displayName("채팅 백엔드 api")
            .build()
    }
}