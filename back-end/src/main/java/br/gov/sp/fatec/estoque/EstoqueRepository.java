package br.gov.sp.fatec.estoque;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

    //@Query("SELECT SUM(e.quantidade * p.valorCompra) FROM Estoque e INNER JOIN e.Produto p")
    //public double getValorCustoProdutos();

	//@Query("SELECT e FROM Estoque e WHERE e.idEmpresa = ?1")
	//public Estoque getEstoque(Integer idEmpresa);

    //@Query("SELECT SUM(e.quantidade * p.valorCompra) FROM Estoque e INNER JOIN e.Produto p")
    //public double getValorCustoProdutos();

    //@Query("SELECT e FROM Estoque e WHERE e.idArmazem = ?1")
    public Estoque getEstoqueByArmazemId(Integer idArmazem);
    public List<Estoque> findByArmazemId(int idArmazem);
}
