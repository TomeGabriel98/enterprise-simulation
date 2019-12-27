package br.gov.sp.fatec.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    public Optional<Produto> findById(Integer id);

	//@Query("SELECT e FROM Produto e WHERE e.idEstoque = ?1")
	//public ArrayList<Produto> getListaProduto(Integer id);
}