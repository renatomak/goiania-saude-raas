package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import java.util.Collections;

public final class PacientePsicossocialDTOMock {

    private PacientePsicossocialDTOMock() { }

    public static PacientePsicossocialDTO valido() {
        final PacientePsicossocialDTO dto = criarBase();
        preencherDadosPessoais(dto, "GO", "202405", "1234567",
                "123456789012345", "12345678901", "20240501",
                "20240531", "PACIENTE TESTE", "MAE TESTE");
        preencherEndereco(dto, "RUA TESTE", "123", "APTO 1",
                "74000000", "1234567", "CENTRO", "RUA");
        preencherDadosAdicionais(dto, "19900101", "M", "01",
                "RESPONSAVEL TESTE", "0001", "62999999999",
                "6233333333", "A", "F200", "S", "7654321", "D");
        preencherExtras(dto, "1234567890", "O", "N", "N", "N",
                "N", "N", "paciente@teste.com");
        dto.setAcoes(Collections.emptyList());
        return dto;
    }

    public static PacientePsicossocialDTO exemplo() {
        final PacientePsicossocialDTO dto = criarBase();
        preencherDadosPessoais(dto, "SP", "202406", "7654321",
                "543210987654321", "10987654321", "20240601",
                "20240630", "PACIENTE EXEMPLO", "MAE EXEMPLO");
        preencherEndereco(dto, "AV EXEMPLO", "321", "APTO 2",
                "75000000", "7654321", "BAIRRO EXEMPLO", "AV");
        preencherDadosAdicionais(dto, "19850101", "F", "02",
                "RESPONSAVEL EXEMPLO", "0002", "62988888888",
                "6244444444", "B", "F201", "N", "1234567", "E");
        preencherExtras(dto, "0987654321", "P", "S", "S", "S",
                "S", "S", "exemplo@teste.com");
        dto.setAcoes(Collections.emptyList());
        return dto;
    }

    private static PacientePsicossocialDTO criarBase() {
        return new PacientePsicossocialDTO();
    }

    private static void preencherDadosPessoais(final PacientePsicossocialDTO dto,
            final String uf, final String competencia, final String cnes,
            final String cns, final String cpf, final String dataInicio,
            final String dataFim, final String nome, final String nomeMae) {
        dto.setUf(uf);
        dto.setCompetencia(competencia);
        dto.setCnes(cnes);
        dto.setCnsPaciente(cns);
        dto.setCpfPaciente(cpf);
        dto.setDataInicio(dataInicio);
        dto.setDataFim(dataFim);
        dto.setNomePaciente(nome);
        dto.setNomeMae(nomeMae);
    }

    private static void preencherEndereco(final PacientePsicossocialDTO dto,
            final String logradouro, final String numero,
            final String complemento, final String cep,
            final String municipio, final String bairro,
            final String tipoLogradouro) {
        dto.setLogradouro(logradouro);
        dto.setNumeroEndereco(numero);
        dto.setComplemento(complemento);
        dto.setCep(cep);
        dto.setMunicipioIbge(municipio);
        dto.setDescricaoBairro(bairro);
        dto.setTipoLogradouro(tipoLogradouro);
    }

    private static void preencherDadosAdicionais(final PacientePsicossocialDTO dto,
            final String nascimento, final String sexo, final String raca,
            final String responsavel, final String etnia, final String celular,
            final String telefone, final String motivoSaida, final String cid,
            final String coberturaEsf, final String cnesEsf,
            final String destino) {
        dto.setDataNascimento(nascimento);
        dto.setSexo(sexo);
        dto.setRacaCor(raca);
        dto.setNomeResponsavel(responsavel);
        dto.setEtnia(etnia);
        dto.setCelular(celular);
        dto.setTelefone(telefone);
        dto.setMotivoSaida(motivoSaida);
        dto.setCidPrincipal(cid);
        dto.setCoberturaEsf(coberturaEsf);
        dto.setCnesEsf(cnesEsf);
        dto.setDestinoPaciente(destino);
    }

    private static void preencherExtras(final PacientePsicossocialDTO dto,
            final String prontuario, final String origem, final String situacaoRua,
            final String usuarioDrogas, final String drogaAlcool,
            final String drogaCrack, final String drogaOutros,
            final String email) {
        dto.setNumeroProntuario(prontuario);
        dto.setOrigemPaciente(origem);
        dto.setSituacaoRua(situacaoRua);
        dto.setUsuarioDrogas(usuarioDrogas);
        dto.setTipoDrogaAlcool(drogaAlcool);
        dto.setTipoDrogaCrack(drogaCrack);
        dto.setTipoDrogaOutros(drogaOutros);
        dto.setEmailPaciente(email);
    }
}
