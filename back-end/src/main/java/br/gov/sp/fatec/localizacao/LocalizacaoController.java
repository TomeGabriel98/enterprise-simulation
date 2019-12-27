package br.gov.sp.fatec.localizacao;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import antlr.collections.List;
import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.equipe.Equipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="/localizacao",
produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LocalizacaoController {

	@Autowired
	private LocalizacaoService localizacaoService;

	@GetMapping(value="/{id}")
	@JsonView(LocalizacaoView.LocalizacaoResumida.class)
	public ResponseEntity<Localizacao> getLocalizacaoById(@PathVariable("id") Integer id){
		Optional<Localizacao> localizacao = localizacaoService.buscarPorId(id);
		return localizacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping(value="/excluir")
	@JsonView(LocalizacaoView.LocalizacaoResumida.class)
	public ResponseEntity<Localizacao> excluir(@RequestBody Localizacao localizacao){
		try{
			Localizacao l = localizacaoService.excluir(localizacao);

			if(l == null)
				return new ResponseEntity<Localizacao>(HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<Localizacao>(l, HttpStatus.OK);
		} catch (Exception e){
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value="/editar")
	@JsonView(LocalizacaoView.LocalizacaoResumida.class)
	public ResponseEntity<Localizacao> editar(@RequestBody Localizacao localizacao){
		try{
			Localizacao l = localizacaoService.editar(localizacao);

			if(l == null)
				return new ResponseEntity<Localizacao>(HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<Localizacao>(l, HttpStatus.OK);
		} catch (Exception e){
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public ResponseEntity<Collection<Localizacao>> getAll(){
		return new ResponseEntity<Collection<Localizacao>> (localizacaoService.buscarTodos(), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value="/buscaLocalizacao")
	public ResponseEntity<Localizacao> getLocalizacaoByEmpresaTipo(@RequestParam(value = "idEmpresa") Integer idEmpresa,
			@RequestParam(value = "tipo") String tipo){
		Optional<Localizacao> localizacao = localizacaoService.buscarPorId(idEmpresa);

		if(localizacao == null)
			return new ResponseEntity<Localizacao>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Localizacao>(localizacao.get(), HttpStatus.OK);
	}

	@Autowired
	private EmpresaServiceImpl empresaService;
/*
	@CrossOrigin
	@RequestMapping(value="defineLocalizacao/{idEmpresa}/", method=RequestMethod.POST)
	public ResponseEntity<Localizacao> defineLocalizacao(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json){

		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
		Integer idLocalizacao = Integer.parseInt(json.get("id").asText());
		boolean operando = false;
		Localizacao loc = localizacaoService.buscarPorId(idLocalizacao).get();
		for(Localizacao locEmp : empresa.getLocalizacao()) {
			if(locEmp.getMatriz()) {
				operando = locEmp.getMatriz();
			}
		}
		if(!operando) {
			loc.setMatriz(true);
		}
		loc.setOpera(true);
		localizacaoService.editar(loc);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Localizacao>(loc, responseHeaders, HttpStatus.CREATED);
	}
*/

}
