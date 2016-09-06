package juli.config.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 开发环境配置
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class DevConfiguration {
    @Bean
    public Docket swaggerDocConfig() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("巨力API文档").build();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("juli.api"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo);
    }
}
