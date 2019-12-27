package br.gov.sp.fatec.turno;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.produto.Produto;



public interface TurnoRepository extends JpaRepository<Turno, Long> {

	//public Optional<Turno> findByEmpresaIdAndIndice(Integer empres_id, Integer indice);

	public Turno save(Turno turno);

	public Optional<Turno> findById(long i);
	
	
	
}
