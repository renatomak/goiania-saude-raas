package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload;

import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ArquivosRaasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DownloadArquivosRaasRepository extends JpaRepository<ArquivosRaasEntity, Long> {

    @Query(value = """
            SELECT
                r.cd_raas_processo,
                r.dt_geracao,
                r.path,
                r.texto
            FROM raas_processo r
            LEFT JOIN empresa e ON e.empresa = r.empresa
            WHERE r.cd_raas_processo = :id
            """,
            nativeQuery = true)
    DownloadArquivosRaasProjection download(
            @Param("id") Long id
    );
}
