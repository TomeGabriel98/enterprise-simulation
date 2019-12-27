package br.gov.sp.fatec.anotacao;

import java.util.List;


public interface AnotacaoService {
	
	public Anotacao salvar(Anotacao autorizacao);
	
	public void excluir(Long idAnotacao);
	
	public List<Anotacao> todos();
	
	public List<Anotacao> buscarPorUsuario(String nome);
	
	public Anotacao buscarPorId(Long idAnotacao);

}
