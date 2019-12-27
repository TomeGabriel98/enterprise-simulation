package br.gov.sp.fatec.equipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="/equipe",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EquipeController {

    @Autowired
    private EquipeServiceImpl equipeService;

	@Autowired
	private EmpresaServiceImpl empresaService;
    
    @CrossOrigin
    @GetMapping(value="/buscarPorId")
    public ResponseEntity<Equipe> getEquipeById(@RequestParam(value = "id") Integer id){
        Optional<Equipe> equipe = equipeService.buscarPorId(id);

        if(equipe == null)
            return new ResponseEntity<Equipe>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Equipe>(equipe.get(), HttpStatus.OK);
    }



    @CrossOrigin
    @GetMapping(value="/buscarEmpresaTipo")
    public ResponseEntity<Equipe> getEquipeByEmpresaTipo(@RequestParam(value = "idEmpresa") Integer idEmpresa,
     @RequestParam(value = "tipo") String tipo){
        Optional<Equipe> equipe = equipeService.buscarPorId(idEmpresa);

        if(equipe == null)
            return new ResponseEntity<Equipe>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Equipe>(equipe.get(), HttpStatus.OK);
    }
    
	
	@CrossOrigin
	@RequestMapping(value="contrataEquipe/{idEmpresa}/", method=RequestMethod.POST)
	public ResponseEntity<Empresa> contrataEquipe(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception{
		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
        String tipoEquipe = json.get("tipo").asText();
		Integer quantidade = Integer.parseInt(json.get("quantidade").asText());
		String regiao = json.get("regiao").asText();

		Equipe e = equipeService.salvar(regiao, empresa, tipoEquipe, quantidade);

        List<Equipe> equipes = empresa.getEquipes();

        if (!equipes.contains(e)){
            equipes.add(e);
            empresa.setEquipes(equipes);
            empresaService.salvar(empresa);
        }

		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(empresa, responseHeaders, HttpStatus.CREATED);
	}

    @CrossOrigin
    @RequestMapping(value="demiteEquipe/{idEmpresa}/", method=RequestMethod.POST)
    public ResponseEntity<Object> demiteEquipe(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception{
        Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
        String tipoEquipe = json.get("tipo").asText();
        Integer idEquipe = Integer.parseInt(json.get("idEquipe").asText());
        Integer quantidade = Integer.parseInt(json.get("quantidade").asText());
        String regiao = json.get("regiao").asText();

        Object equipe = equipeService.editar(regiao, idEquipe, tipoEquipe, quantidade, empresa);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Object>(equipe, responseHeaders, HttpStatus.CREATED);
    }
	

}