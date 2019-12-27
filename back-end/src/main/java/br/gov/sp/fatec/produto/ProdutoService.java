package br.gov.sp.fatec.produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    public Produto salvar(Produto produto);
    public Produto excluir(Produto produto);
    public Produto editar(Produto produto);
    public Optional<Produto> buscarPorId(Integer id);
	public List<Produto> buscarTodos();

}
