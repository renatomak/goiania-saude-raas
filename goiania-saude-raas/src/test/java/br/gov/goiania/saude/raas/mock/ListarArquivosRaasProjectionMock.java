package br.gov.goiania.saude.raas.mock;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos.ListarArquivosRaasProjection;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public final class ListarArquivosRaasProjectionMock {
    private ListarArquivosRaasProjectionMock() { }
    public static ListarArquivosRaasProjection valido() {
        return new ProjectionValida();
    }
    public static List<ListarArquivosRaasProjection> listaPopular() {
        return List.of(valido(), valido(), valido());
    }
    private static class ProjectionValida implements ListarArquivosRaasProjection {
        @Override
        public Long getId() {
            return 1L;
        }
        @Override
        public Integer getMes() {
            return 5;
        }
        @Override
        public Integer getAno() {
            return 2026;
        }
        @Override
        public LocalDate getDtGeracao() {
            return LocalDate.of(2026, 5, 7);
        }
        @Override
        public Long getEmpresa() {
            return 123L;
        }
        @Override
        public String getNomeEmpresa() {
            return "Empresa Teste";
        }
        @Override
        public String getPath() {
            return "/caminho/arquivo.txt";
        }
        @Override
        public Integer getStatus() {
            return 3;
        }
        @Override
        public BigDecimal getTotalFolha() {
            return new BigDecimal("100.00");
        }
    }
}
