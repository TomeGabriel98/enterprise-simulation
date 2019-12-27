package br.gov.sp.fatec.emprestimo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.view.View;

@RestController
@CrossOrigin
@RequestMapping(value="/emprestimo")
public class EmprestimoController {
	
	@Autowired
	private EmprestimoServiceImpl emprestimoService;
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@JsonView(View.Emprestimo.class)
	@CrossOrigin
	@RequestMapping(value="/novo", method=RequestMethod.POST)
	public ResponseEntity<Emprestimo> novo(@RequestBody ObjectNode json) {
		String empresaId = json.get("empresa").asText();
		Double valor = Double.parseDouble(json.get("valor").asText());
		int rodada = Integer.parseInt(json.get("rodada").asText());
		Empresa e = empresaService.buscarPorId(Integer.parseInt(empresaId)).get();
		Emprestimo emprestimo = emprestimoService.salvar(e, valor, rodada);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Emprestimo>(emprestimo, responseHeaders, HttpStatus.CREATED);
	}
	
	@JsonView(View.Emprestimo.class)
	@CrossOrigin
	@RequestMapping(value="/novoBNDS", method=RequestMethod.POST)
	public ResponseEntity<Emprestimo> novoBNDS(@RequestBody ObjectNode json) {
		String empresaId = json.get("empresa").asText();
		Double valor = Double.parseDouble(json.get("valor").asText());
		int rodada = Integer.parseInt(json.get("rodada").asText());
		Empresa e = empresaService.buscarPorId(Integer.parseInt(empresaId)).get();
		Emprestimo emprestimo = emprestimoService.salvarBNDS(e, valor, rodada);
		double valorAtual = e.getEmprestimo();
		e.setEmprestimo(valorAtual+valor);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Emprestimo>(emprestimo, responseHeaders, HttpStatus.CREATED);
	}
	
	@JsonView(View.Emprestimo.class)
	@CrossOrigin
	@RequestMapping(value="/getAll/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Emprestimo>> listar(@PathVariable int id){
		Empresa empresa = empresaService.buscarPorId(id).get();
		List<Emprestimo> e = emprestimoService.listaTodos(empresa);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<List<Emprestimo>>(e, responseHeaders, HttpStatus.OK);
	}
	
	@JsonView(View.Emprestimo.class)
	@CrossOrigin
	@RequestMapping(value="/pagar", method=RequestMethod.POST)
	public ResponseEntity<List<Emprestimo>> pagar(@RequestBody ObjectNode json){
		double valor = json.get("valor").asDouble();
		int idEmpresa = json.get("idEmpresa").asInt();
		long idEmprestimo = json.get("idEmprestimo").asLong();
		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
		Emprestimo emprestimo = emprestimoService.buscaPorId(idEmprestimo);
		
		List<Emprestimo> emprestimos = emprestimoService.pagamento(empresa, emprestimo, valor);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<List<Emprestimo>>(emprestimos, responseHeaders, HttpStatus.OK);
	}

}
