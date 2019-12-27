package br.gov.sp.fatec.estoque;

import java.util.List;

public interface EstoqueService {

    public Estoque salvar(Estoque estoque) throws Exception;    
	public List<Estoque> buscaEstoque(int id);
	public Estoque atualizar(Estoque estoque) throws Exception;

}
