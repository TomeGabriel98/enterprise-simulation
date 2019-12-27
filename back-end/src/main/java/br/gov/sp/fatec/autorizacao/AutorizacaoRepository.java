package br.gov.sp.fatec.autorizacao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface AutorizacaoRepository extends CrudRepository<Autorizacao, Long> {
	
	public Autorizacao findByNome(String nome);
	
	public List<Autorizacao> findByNomeContainsIgnoreCase(String nome);

	public Autorizacao findFirstByNome(String nome);

}
