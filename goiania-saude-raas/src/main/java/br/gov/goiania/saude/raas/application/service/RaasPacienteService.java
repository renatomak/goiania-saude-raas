package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasPacienteService {

    private static final int TAMANHO_LINHA_15 = 406;

    private static final String CODIGO_LINHA_15 = "15";
    private static final String FILLER_4 = "    ";

    public String gerarLinha15(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder(420);

        appendIdentificacao(sb, p);
        appendEnderecoNascimento(sb, p);
        appendResponsavelContato(sb, p);
        appendAdministrativo(sb, p);
        appendFinal(sb, p);

        final String linha = sb.toString();
        RaasGenerationDomainService.validarTamanhoLinha(
                linha, TAMANHO_LINHA_15, "15");
        return linha;
    }

    private void appendIdentificacao(final StringBuilder sb,
                                      final PacientePsicossocialDTO p) {
        sb.append(CODIGO_LINHA_15);
        sb.append(RaasPaddingUtil.leftPadZeros(p.getUf(), 2));
        sb.append(RaasPaddingUtil.leftPadZeros(p.getCompetencia(), 6));
        sb.append(RaasPaddingUtil.leftPadZeros(p.getCnes(), 7));
        sb.append(RaasGenerationDomainService.resolverCns(
                p.getCnsPaciente(), p.getCpfPaciente(), 15));
        sb.append(RaasPaddingUtil.rightPad(p.getDataInicio(), 8));
        sb.append(RaasPaddingUtil.rightPad(p.getDataFim(), 8));
        sb.append(RaasPaddingUtil.rightPad(p.getNomePaciente(), 30));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroProntuario(), 10));
        sb.append(RaasPaddingUtil.rightPad(p.getNomeMae(), 30));
    }

    private void appendEnderecoNascimento(final StringBuilder sb,
                                           final PacientePsicossocialDTO p) {
        sb.append(RaasPaddingUtil.rightPad(p.getLogradouro(), 30));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroEndereco(), 5));
        sb.append(RaasPaddingUtil.rightPad(p.getComplemento(), 10));
        sb.append(RaasPaddingUtil.rightPad(p.getCep(), 8));
        sb.append(RaasPaddingUtil.rightPad(p.getMunicipioIbge(), 7));
        sb.append(RaasPaddingUtil.rightPad(p.getDataNascimento(), 8));
        sb.append(RaasPaddingUtil.rightPad(p.getSexo(), 1));
        sb.append(RaasPaddingUtil.rightPad(p.getRacaCor(), 2));
    }

    private void appendResponsavelContato(final StringBuilder sb,
                                           final PacientePsicossocialDTO p) {
        sb.append(RaasPaddingUtil.rightPad(p.getNomeResponsavel(), 30));
        sb.append(RaasPaddingUtil.rightPad(p.getNacionalidade(), 3));
        sb.append(RaasPaddingUtil.rightPad(p.getEtnia(), 4));
        sb.append(RaasPaddingUtil.rightPad(p.getTelefone(), 11));
        sb.append(RaasPaddingUtil.rightPad(p.getCelular(), 11));
        sb.append(RaasPaddingUtil.rightPad(p.getMotivoSaida(), 2));
    }

    private void appendAdministrativo(final StringBuilder sb,
                                       final PacientePsicossocialDTO p) {
        sb.append(RaasPaddingUtil.rightPad(p.getDtOcorrencia(), 8));
        sb.append(RaasPaddingUtil.rightPad(p.getCidPrincipal(), 4));
        sb.append(RaasPaddingUtil.rightPad(p.getCidSecundario1(), 4));
        sb.append(RaasPaddingUtil.rightPad(p.getCidSecundario2(), 4));
        sb.append(RaasPaddingUtil.rightPad(p.getCidSecundario3(), 4));
        sb.append(RaasPaddingUtil.rightPad(p.getCidCausasAssociadas(), 4));
        sb.append(RaasPaddingUtil.rightPad(p.getCaraterAtendimento(), 2));
        sb.append(RaasPaddingUtil.rightPad(p.getOrigemPaciente(), 2));
        sb.append(RaasPaddingUtil.rightPad(p.getCoberturaEsf(), 1));
        sb.append(RaasPaddingUtil.rightPad(p.getCnesEsf(), 7));
        sb.append(RaasPaddingUtil.leftPadZeros(
                String.valueOf(p.getTotalProcedimentos() != null
                        ? p.getTotalProcedimentos() : 0), 5));
        sb.append(RaasPaddingUtil.rightPad(p.getDestinoPaciente(), 2));
        sb.append(RaasPaddingUtil.rightPad(p.getOrigemInformacoes(), 3));
        sb.append(RaasPaddingUtil.rightPad(p.getSituacaoRua(), 1));
        sb.append(RaasPaddingUtil.rightPad(p.getUsuarioDrogas(), 1));
        sb.append(formatarTipoDroga(p));
    }

    private void appendFinal(final StringBuilder sb,
                              final PacientePsicossocialDTO p) {
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroAutorizacao(), 13));
        sb.append(RaasPaddingUtil.rightPad(p.getDescricaoBairro(), 30));
        sb.append(RaasPaddingUtil.rightPad(p.getTipoLogradouro(), 3));
        sb.append(RaasPaddingUtil.rightPad(p.getEmailPaciente(), 40));
        sb.append(RaasGenerationDomainService.resolverCpf(
                p.getCpfPaciente(), p.getCnsPaciente(), 11));
        sb.append(FILLER_4);
    }

    private String formatarTipoDroga(final PacientePsicossocialDTO p) {
        if (!"S".equalsIgnoreCase(p.getUsuarioDrogas())) {
            return "   ";
        }
        return RaasPaddingUtil.rightPad(
                p.getTipoDrogaAlcool() != null ? p.getTipoDrogaAlcool() : "", 1)
                + RaasPaddingUtil.rightPad(
                p.getTipoDrogaCrack() != null ? p.getTipoDrogaCrack() : "", 1)
                + RaasPaddingUtil.rightPad(
                p.getTipoDrogaOutros() != null ? p.getTipoDrogaOutros() : "", 1);
    }
}
