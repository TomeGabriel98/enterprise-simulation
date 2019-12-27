package br.gov.sp.fatec.emprestimo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{
	
	List<Emprestimo> findAllByEmpresa_Id(int id);

}
