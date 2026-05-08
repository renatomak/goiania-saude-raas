package br.gov.goiania.saude.raas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

    @Test
    @DisplayName("Deve chamar SpringApplication.run ao executar main")
    void deveChamarSpringApplicationRunAoExecutarMain() {
        try (MockedStatic<SpringApplication> springApplication = Mockito.mockStatic(SpringApplication.class)) {
            springApplication.when(() -> SpringApplication.run(Application.class, new String[]{})).thenReturn(null);
            Application.main(new String[]{});
            springApplication.verify(() -> SpringApplication.run(Application.class, new String[]{}));
        }
    }
}
