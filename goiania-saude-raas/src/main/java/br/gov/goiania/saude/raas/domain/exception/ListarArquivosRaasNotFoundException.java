package br.gov.goiania.saude.raas.domain.exception;

public class ListarArquivosRaasNotFoundException extends RuntimeException {

    public ListarArquivosRaasNotFoundException(final String codigoEmpresa, final Integer mes, final Integer ano) {
        super(String.format("Nenhum RAAS encontrado para empresa %s na competencia %02d/%d",
                codigoEmpresa, mes, ano));
    }
}
