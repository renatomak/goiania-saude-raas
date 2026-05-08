package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "raas_processo")
@Getter
@Setter
@AllArgsConstructor
public class ListarArquivosRaasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "dt_geracao")
    private LocalDate dataGeracao;

    @Column(name = "empresa", nullable = false)
    private String codigoEmpresa;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;


    @Column(name = "path")
    private String path;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_folha", precision = 15, scale = 2)
    private BigDecimal totalFolha;
}
