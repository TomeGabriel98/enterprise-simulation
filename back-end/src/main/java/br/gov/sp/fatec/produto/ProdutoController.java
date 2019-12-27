package br.gov.sp.fatec.produto;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.erb.ERB;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="/produto")
public class ProdutoController{

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoServiceImpl produtoServiceImpl;
    
    @CrossOrigin
    @GetMapping(value="/buscarPorId")
    @JsonView({ProdutoView.ProdutoCompleto.class})
    public ResponseEntity<Produto> getProdutoById(@RequestParam(value = "id") Integer id){
        Optional<Produto> produto = produtoService.buscarPorId(id);

        return produto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @CrossOrigin
    @PostMapping(value="/salvar")
    //@JsonView({ProdutoView.ProdutoCompleto.class})
    public ResponseEntity<List<Produto>> salvar(@RequestBody ObjectNode json){
        String nome = json.get("nome").asText();
        Double valorCompra = json.get("valor_compra").asDouble();
        Double valorVenda = json.get("valor_venda").asDouble();
        
        Produto produto = new Produto(nome, valorCompra, valorVenda);
        produtoService.salvar(produto);
        List<Produto> produtos = produtoService.buscarTodos();
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping(value="/editar")
    public ResponseEntity<List<Produto>>  editar(@RequestBody ObjectNode json) {
        String nome = json.get("nome").asText();
        Double valorVenda = json.get("valor_venda").asDouble();

        Produto produto = produtoServiceImpl.getProdutoByNome(nome);
        if (produto != null){
            produto.setValorVenda(valorVenda);
            produtoServiceImpl.editar(produto);
        }

        List<Produto> produtos = produtoService.buscarTodos();
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping(value="/buscarTodos")
    public ResponseEntity<List<Produto>> listarTodos(){
    	List<Produto> produtos = produtoService.buscarTodos();
    	return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
    }

}