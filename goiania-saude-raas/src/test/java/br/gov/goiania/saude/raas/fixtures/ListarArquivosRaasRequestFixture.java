package br.gov.goiania.saude.raas.fixtures;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.Fixture;

import static br.gov.goiania.saude.raas.testutils.TestConstants.FIXTURE_LABEL_NOT_FOUND;
import static br.gov.goiania.saude.raas.testutils.TestConstants.FIXTURE_LABEL_VALID;

public class ListarArquivosRaasRequestFixture implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ListarArquivosRaasRequest.class).addTemplate(FIXTURE_LABEL_VALID, new Rule() {{
            add("mes", 5);
            add("ano", 2026);
            add("codigoEmpresa", "123");
            add("situacao", 3);
        }});

        Fixture.of(ListarArquivosRaasRequest.class).addTemplate(FIXTURE_LABEL_NOT_FOUND, new Rule() {{
            add("mes", 5);
            add("ano", 2026);
            add("codigoEmpresa", "999");
            add("situacao", 3);
        }});
    }
}

