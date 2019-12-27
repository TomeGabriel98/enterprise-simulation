package br.gov.sp.fatec.autorizacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.view.View;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@CrossOrigin
@RequestMapping(value = "/autorizacao")
public class AutorizacaoController {

	@Autowired
	private AutorizacaoService autorizacaoService;

	public void setAutorizacaoService(AutorizacaoService autorizacaoService) {
		this.autorizacaoService = autorizacaoService;
	}

	@CrossOrigin
	@RequestMapping(value = "/get/{nome}", method = RequestMethod.GET)
	@JsonView(View.Anotacao.class)
	public ResponseEntity<Collection<Autorizacao>> pesquisar(@PathVariable("nome") String nome) {
		return new ResponseEntity<Collection<Autorizacao>>(autorizacaoService.buscar(nome), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	@JsonView(View.Anotacao.class)
	public ResponseEntity<Autorizacao> pesquisarPorId(@PathVariable("id") Long id) {
		return new ResponseEntity<Autorizacao>(autorizacaoService.buscarPorId(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@JsonView(View.Anotacao.class)
	public ResponseEntity<Collection<Autorizacao>> getAll() {
		return new ResponseEntity<Collection<Autorizacao>>(autorizacaoService.todos(), HttpStatus.OK);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	//@JsonView(View.Anotacao.class)
	public ResponseEntity<Autorizacao> salvar(@RequestBody Autorizacao autorizacao, UriComponentsBuilder uriComponentsBuilder) {
		autorizacao = autorizacaoService.salvar(autorizacao);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(uriComponentsBuilder.path("/getById/" + autorizacao.getId()).build().toUri());
		return new ResponseEntity<Autorizacao>(autorizacao, responseHeaders, HttpStatus.CREATED);
	}

}
