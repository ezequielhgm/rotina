package br.com.rotina.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Test
    void contextLoads() {
    }

    @Test
    void mapsTaskCreationEndpoint() {
        boolean taskPostMappingExists = requestMappingHandlerMapping.getHandlerMethods().keySet().stream()
                .anyMatch(mapping -> mapping.getPatternValues().contains("/api/v1/task")
                        && mapping.getMethodsCondition().getMethods().contains(RequestMethod.POST));

        assertThat(taskPostMappingExists).isTrue();
    }

}
