package br.gov.sp.fatec.anotacao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface AnotacaoRepository extends CrudRepository<Anotacao, Long> {
	
	public List<Anotacao> findByUsuarioNome(String nome);

}
