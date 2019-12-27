package br.gov.sp.fatec.mercado;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin
@RequestMapping(value="/mercado")
public class MercadoController {

	@Autowired
	public MercadoServiceImpl tipoMercadoServiceImpl;

	@Autowired
	private MercadoService mercadoService;

	@RequestMapping(value="/getById/{id}", method=RequestMethod.GET)
	public ResponseEntity<Mercado> getEmpresaById(@PathVariable("id") String id){
		ArrayList<Mercado> tipoMercado = new ArrayList<Mercado>(tipoMercadoServiceImpl.buscaTipoMercado(Integer.parseInt(id)));
		System.out.println(tipoMercado);
		if(tipoMercado == null)
			return new ResponseEntity<Mercado>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Mercado>(tipoMercado.get(0), HttpStatus.OK);
	}

	@PostMapping(value="/salvar")
	public ResponseEntity<Mercado> salvar(@RequestBody Mercado tipoMercado){
		try {
			Mercado e = tipoMercadoServiceImpl.salvar(tipoMercado);
			if (e == null)
				return new ResponseEntity<Mercado>(HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<Mercado>(e, HttpStatus.CREATED);
		} catch (Exception e){
			return ResponseEntity.badRequest().build();
		}
	}

	@CrossOrigin
	@GetMapping(value="/buscarEmpresaTipo")
	public ResponseEntity<Mercado> getMercadoByEmpresaTipo(@RequestParam(value = "idEmpresa") Integer idEmpresa,
			@RequestParam(value = "tipo") String tipo){
		Optional<Mercado> tipoMercado = mercadoService.buscarPorId(idEmpresa);

		if(tipoMercado == null)
			return new ResponseEntity<Mercado>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Mercado>(tipoMercado.get(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="defineMercado/{idEmpresa}/", method=RequestMethod.POST)
	public ResponseEntity<Mercado> defineMercado(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception {
		Integer idTipoMercado = Integer.parseInt(json.get("id").asText());
		Mercado tipoMercado	 = tipoMercadoServiceImpl.buscarPorId(idTipoMercado).get();
		tipoMercado.setOpera(true);
		tipoMercadoServiceImpl.salvar(tipoMercado);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Mercado>(tipoMercado, responseHeaders, HttpStatus.CREATED);
	}
}
