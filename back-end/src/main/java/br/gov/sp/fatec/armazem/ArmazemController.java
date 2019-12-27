package br.gov.sp.fatec.armazem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiro;
import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiroService;
import br.gov.sp.fatec.view.View;

@RestController
@CrossOrigin
@RequestMapping(value="/armazem")
public class ArmazemController {
	
	@Autowired
	public ArmazemServiceImpl armazemServiceImpl;
	
	@Autowired
    private ArmazemService armazemService;

    @Autowired
    private EmpresaServiceImpl empresaService;


    @GetMapping(value="/buscarPorId")
    public ResponseEntity<Armazem> getArmazemById(@RequestParam(value = "id") Integer id){
        Optional<Armazem> erb = armazemService.buscarPorId(id);

        if(erb == null)
            return new ResponseEntity<Armazem>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Armazem>(erb.get(), HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value="compraArmazem/{idEmpresa}/", method=RequestMethod.POST)
    public ResponseEntity<Empresa> compraERB(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception{
        Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
        String regiao = json.get("regiao").asText();
        Integer quantidade = Integer.parseInt(json.get("quantidade").asText());

        Armazem erb = armazemService.salvar(empresa, regiao, quantidade);

        java.util.List<Armazem> armazens = empresa.getArmazem();

        if (!armazens.contains(erb)){
            armazens.add(erb);
            empresa.setArmazem(armazens);
            empresaService.salvar(empresa);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Empresa>(empresa, responseHeaders, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value="vendaArmazem/{idEmpresa}/{idArmazem}/", method=RequestMethod.POST)
    public ResponseEntity<Empresa> vendaERB(@PathVariable Integer idEmpresa, @PathVariable Integer idArmazem, @RequestBody ObjectNode json) throws Exception {
        Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
        String regiao = json.get("regiao").asText();
        Integer quantidade = Integer.parseInt(json.get("quantidade").asText())  ;
        java.util.List<Armazem> armazems = empresa.getArmazem();
        Armazem armazem = armazemService.editar(empresa, idArmazem, regiao, quantidade);

        empresaService.salvar(empresa);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Empresa>(empresa, responseHeaders, HttpStatus.CREATED);
    }
    
    @CrossOrigin
    @GetMapping("/buscar/{idEmpresa}")
    public ResponseEntity<List<Armazem>> burcaArmazem(@PathVariable int idEmpresa){
    	List<Armazem> armazens = armazemServiceImpl.buscaPorIdEmpresa(idEmpresa);
    	return new ResponseEntity<List<Armazem>>(armazens, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/listarTodos/{idEmpresa}/")
    public ResponseEntity<Collection<Armazem>> listarArmazens(@PathVariable Integer idEmpresa){
        Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
        List<Armazem> armazems = empresa.getArmazem();
        return new ResponseEntity<Collection<Armazem>>(armazems, HttpStatus.OK);
    }
    
    @JsonView(View.Armazem.class)
    @CrossOrigin
    @GetMapping("/todos")
    public ResponseEntity<List<Armazem>> listar(){
    	return new ResponseEntity<List<Armazem>>(armazemService.buscaTodos(), HttpStatus.OK);
    }

}
