package br.gov.sp.fatec.armazem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.fatec.produto.Produto;

@Repository
public interface ArmazemRepository extends JpaRepository<Armazem, Integer>  {
	 public Optional<Armazem> findById(Integer id);
	 public Armazem findByRegiao(String regiao);
	 public List<Armazem> findByEmpresa_Id(int id);
}
