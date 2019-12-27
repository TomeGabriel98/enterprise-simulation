package br.gov.sp.fatec.servicoTerceiro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServicoTerceiroRepository extends JpaRepository<ServicoTerceiro, Integer> {
    public Optional<ServicoTerceiro> findById(Integer id);
    //public List<ServicoTerceiro> findByEmpresaId(Integer id);
}
