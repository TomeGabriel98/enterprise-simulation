package br.gov.sp.fatec.empresa;

import java.util.Collection;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Empresa findByEmail(String email);
    Empresa findByNome(String nome);
    
    //@EntityGraph(value = "adminTurno", type = EntityGraphType.FETCH)
	//public Empresa findEmpresaByAdminTurnoId(Long id);
	Collection<Empresa> findByAutorizacoes_Nome(String nome);
}