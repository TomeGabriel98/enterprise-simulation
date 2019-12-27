package br.gov.sp.fatec.erb;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.equipe.Equipe;

@RestController
@RequestMapping(value="/erb")
public class ERBController {

    @Autowired
    private ERBService erbService;
    
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private ERBServiceImpl erbServiceImpl;

    @GetMapping(value="/buscarPorId")
    public ResponseEntity<ERB> getERById(@RequestParam(value = "id") Integer id){
        Optional<ERB> erb = erbService.findById(id);

        if(erb == null)
            return new ResponseEntity<ERB>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ERB>(erb.get(), HttpStatus.OK);
    }
//    @PostMapping(value="/salvar")
//    public ResponseEntity<ERB> salvar(@RequestBody ERB erb){
//        try {
//            ERB e = erbService.salvar(erb);
//
//            if (e == null)
//                return new ResponseEntity<ERB>(HttpStatus.BAD_REQUEST);
//            else
//                return new ResponseEntity<ERB>(e, HttpStatus.CREATED);
//        } catch (Exception e){
//            return ResponseEntity.badRequest().build();
//        }
//    }

    /*@DeleteMapping(value="/excluir")
    public ResponseEntity<ERB> excluir(@RequestBody ERB erb){
        try{
            ERB e = erbService.excluir(erb);

            if(e == null)
                return new ResponseEntity<ERB>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<ERB>(e, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }*/

    @CrossOrigin
    @GetMapping(value="/buscarEmpresaTipo")
    public ResponseEntity<ERB> getERBByEmpresaTipo(@RequestParam(value = "idEmpresa") Integer idEmpresa,
     @RequestParam(value = "id") Integer id){
        Optional<ERB> equipe = erbService.findById(id);

        if(equipe == null)
            return new ResponseEntity<ERB>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ERB>(equipe.get(), HttpStatus.OK);
    }
    
	@CrossOrigin
	@RequestMapping(value="compraERB/{idEmpresa}/", method=RequestMethod.POST)
	public ResponseEntity<Empresa> compraERB(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception{
		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
		String regiao = json.get("regiao").asText();
		Integer quantidade = Integer.parseInt(json.get("quantidade").asText());

		ERB erb = erbService.salvar(empresa, regiao, quantidade);

		List<ERB> erbs = empresa.getERB();

		if (!erbs.contains(erb)){
			erbs.add(erb);
			empresa.setERB(erbs);
			empresaService.salvar(empresa);
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(empresa, responseHeaders, HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value="vendaERB/{idEmpresa}/", method=RequestMethod.POST)
	public ResponseEntity<Empresa> vendaERB(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception {
		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
		String regiao = json.get("regiao").asText();
		Integer quantidade = Integer.parseInt(json.get("quantidade").asText());
		List<ERB> erbs = empresa.getERB();
		ERB erb = erbService.editar(empresa, regiao, quantidade);
		//erbs.add(erb);
		//empresa.setERB(erbs);
		//empresaService.salvar(empresa);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(empresa, responseHeaders, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/listaERB/{idEmpresa}")
	public ResponseEntity<Collection<ERB>> listarERBs(@PathVariable Integer idEmpresa){
		Empresa e = empresaService.buscarPorId(idEmpresa).get();
		List<ERB> lista = e.getERB();
		return new ResponseEntity<Collection<ERB>>(lista, HttpStatus.OK);
	}
}
