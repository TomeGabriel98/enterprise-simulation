package br.gov.sp.fatec.mercado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MercadoRepository extends JpaRepository<Mercado, Integer> {

	//@Query("SELECT e FROM Caixa e WHERE e.idEmpresa = ?1")
	public List<Mercado> findByEmpresaId(Integer id);

}
