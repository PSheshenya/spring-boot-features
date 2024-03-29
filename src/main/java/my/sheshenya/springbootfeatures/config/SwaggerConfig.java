package my.sheshenya.springbootfeatures.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

@Configuration
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {
    @Bean
    public Docket api_10() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo-api-v1")
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("my.sheshenya.springbootfeatures"))
//                .paths(PathSelectors.any())
                .paths(paths_10())
                .build()
                .consumes(Set.of("application/json", "application/xml"))
                .produces(Set.of("application/json", "application/xml"))
                .apiInfo(apiEndPointsInfo_10());
    }
    private ApiInfo apiEndPointsInfo_10() {
        return new ApiInfoBuilder().title("Demo Service Application")
                .description("Demo Service Application on spring boot v1.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0")
                .build();
    }

    // Only select apis that matches the given Predicates.
    private Predicate<String> paths_10() {
        // Match all paths except /error
        return Predicates.and(
                PathSelectors.regex("/projects.*"),
                Predicates.not(PathSelectors.regex("/error.*"))
        );
//        return Predicates.and(
//                PathSelectors.regex("/v1.*")
//        );
    }


    @Bean
    public Docket api_mock() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo-api-mock")
                .select()
                .apis(RequestHandlerSelectors.basePackage("my.sheshenya.springbootfeatures"))
                .paths(PathSelectors.regex("/mock.*"))
                .build()
                .consumes(Set.of("application/json", "application/xml"))
                .produces(Set.of("application/json", "application/xml"))
                .apiInfo(new ApiInfoBuilder().title("Demo Service Application")
                        .description("Demo Service Application on spring boot vMock")
                        .license("Apache 2.0")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                        .version("mock")
                        .build());
    }
}