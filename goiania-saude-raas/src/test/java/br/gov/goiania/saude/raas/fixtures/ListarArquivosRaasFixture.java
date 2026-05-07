package br.gov.goiania.saude.raas.fixtures;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.Fixture;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.gov.goiania.saude.raas.testutils.TestConstants.FIXTURE_LABEL_VALID;

public class ListarArquivosRaasFixture implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(ListarArquivosRaas.class).addTemplate(FIXTURE_LABEL_VALID, new Rule() {{
            add("mes", 5);
            add("ano", 2026);
            add("dataGeracao", LocalDate.of(2026, 5, 7));
            add("codigoEmpresa", "123");
            add("nomeEmpresa", "Empresa Teste");
            add("path", "/caminho/arquivo.txt");
            add("status", "3");
            add("totalFolha", new BigDecimal("100.00"));
        }});
    }

    public static List<ListarArquivosRaas> listaPopular() {
        return Fixture.from(ListarArquivosRaas.class).gimme(3, FIXTURE_LABEL_VALID);
    }

    public static ListarArquivosRaas valido() {
        return Fixture.from(ListarArquivosRaas.class).gimme(FIXTURE_LABEL_VALID);
    }
}
