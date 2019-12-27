package br.gov.sp.fatec.caixa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CaixaRepository extends JpaRepository<CaixaMovimentacao, Integer> {

	//@Query("SELECT e FROM Caixa e WHERE e.idEmpresa = ?1")
	//public List<CaixaMovimentacao> findByEmpresaId(Integer id);

}
