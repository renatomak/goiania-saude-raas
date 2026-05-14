package br.gov.goiania.saude.raas.infrastructure.config;

import br.gov.goiania.saude.raas.application.ports.in.ListarArquivosRaasUseCasePort;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.application.usecase.ListarArquivosRaasUseCase;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ListarArquivosRaasPort repositoryPort;
    private final ListarArquivosRaasMapper mapper;

    @Bean
    public ListarArquivosRaasUseCasePort listarRaasUseCase() {
        return new ListarArquivosRaasUseCase(repositoryPort, mapper);
    }
}
