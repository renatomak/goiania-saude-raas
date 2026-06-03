package br.gov.goiania.saude.raas.application.ports.in;

public interface GerarRaasPsicossocialPortIn {

    String execute(Integer mes, Integer ano);

    String executePaciente(Integer mes, Integer ano, Long cdRaasPsi);
}
