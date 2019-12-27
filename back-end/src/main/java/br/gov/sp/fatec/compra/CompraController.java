package br.gov.sp.fatec.compra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.armazem.ArmazemServiceImpl;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.produto.ProdutoServiceImpl;
import br.gov.sp.fatec.view.View;

@RestController
@CrossOrigin
@RequestMapping(value="/compra")
public class CompraController {
	
	@Autowired
	private CompraServiceImpl compraService;
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private ProdutoServiceImpl produtoService;
	
	@Autowired
	private ArmazemServiceImpl armazemService;
	
	@JsonView(View.Compra.class)
	@CrossOrigin
	@RequestMapping(value="/fazerCompra", method=RequestMethod.POST)
	public ResponseEntity<List<Compra>> fazerCompra(@RequestBody ObjectNode json){
		int produtoId = json.get("produto").asInt();
		int quantidade = json.get("quantidade").asInt();
		int armazemId = json.get("armazem").asInt();
		int empresaId = json.get("empresa").asInt();
		double valorVenda = json.get("valor_venda").asDouble();
		
		Armazem armazem = armazemService.buscarPorId(armazemId).get();
		Produto produto = produtoService.buscarPorId(produtoId).get();
		Empresa empresa = empresaService.buscarPorId(empresaId).get();
		
		List<Compra> compras = compraService.fazerCompra(empresa, produto, valorVenda, quantidade, armazem);
		return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
	}
	
	@JsonView(View.Compra.class)
	@CrossOrigin
	@RequestMapping(value="/listar/{idEmpresa}", method=RequestMethod.GET)
	public ResponseEntity<List<Compra>> listarCompras(@PathVariable int idEmpresa){
		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
		List<Compra> compras = compraService.listarTodosPorEmpresa(empresa);
		return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
	}

}
