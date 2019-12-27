package br.gov.sp.fatec.produto;

import br.gov.sp.fatec.erb.ERB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto salvar(Produto produto) {
        
        return produtoRepository.save(produto);
    }

    @Override
    public Produto excluir(Produto produto) {
        produtoRepository.delete(produto);

        return produto;
    }

    @Override
    public Produto editar(Produto produto) {
        if (produtoRepository.findById(produto.getId()).isPresent())
            return produtoRepository.save(produto);

        return null;
    }

    @Override
    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }
    
    @Override
    public List<Produto> buscarTodos(){
    	return produtoRepository.findAll();
    }

    public Produto getProdutoByNome(String nome){
        List<Produto> produtos = buscarTodos();

        for (Produto produto: produtos) {
            if (produto.getNome().equals(nome)){
                return produto;
            }
        }
        return null;
    }

}