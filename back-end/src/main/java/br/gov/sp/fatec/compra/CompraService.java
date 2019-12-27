package br.gov.sp.fatec.compra;

import java.util.List;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.produto.Produto;

public interface CompraService {

	List<Compra> fazerCompra(Empresa empresa, Produto produto, double valorVenda, int quantidade, Armazem armazem);
    //public Compra salvar(Empresa empresa, String produto, Integer quantidade, Double valor);

	List<Compra> listarTodosPorEmpresa(Empresa empresa);

	Compra salvar(Empresa empresa, String produto, Integer quantidade, Double valor);
}
