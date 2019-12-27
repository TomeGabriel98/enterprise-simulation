package br.gov.sp.fatec.caixa;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;

@RestController
@CrossOrigin
@RequestMapping(value="/caixa")
public class CaixaController {
	
	@Autowired
	public CaixaServiceImpl caixaServiceImpl;

    @Autowired
    private EmpresaServiceImpl empresaService;
    
    @CrossOrigin
    @RequestMapping(value="/getCaixas/{empresaId}", method=RequestMethod.GET)
    public ResponseEntity<List<CaixaMovimentacao>> buscaPorId(@PathVariable int empresaId){
    	Empresa empresa = empresaService.buscarPorId(empresaId).get();
    	List<CaixaMovimentacao> caixas = empresa.getCaixas();
    	return new ResponseEntity<List<CaixaMovimentacao>>(caixas, HttpStatus.OK);
    }
	
	@RequestMapping(value="/getById/{id}", method=RequestMethod.GET)
	public ResponseEntity<CaixaMovimentacao> getEmpresaById(@PathVariable("id") String id){
        ArrayList<CaixaMovimentacao> caixaMovimentacao = new ArrayList<CaixaMovimentacao>(caixaServiceImpl.buscaTodasCaixasPorEmpresaId(Integer.parseInt(id)));
        System.out.println(caixaMovimentacao);
        if(caixaMovimentacao == null)
            return new ResponseEntity<CaixaMovimentacao>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<CaixaMovimentacao>(caixaMovimentacao.get(0), HttpStatus.OK);
    }
	
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public ResponseEntity<Collection<CaixaMovimentacao>> getAll(){
		return new ResponseEntity<Collection<CaixaMovimentacao>> (caixaServiceImpl.buscaTodasCaixas(), HttpStatus.OK);
	}


    @PostMapping(value="/emprestimo/{idEmpresa}")
    public ResponseEntity<Empresa> emprestimo(@PathVariable("idEmpresa") Integer idEmpresa, @RequestBody ObjectNode json) throws Exception {

	    Optional<Empresa> e = empresaService.buscarPorId(idEmpresa);

	    if (!e.isPresent())
            throw new Exception("ID Empresa não encotrado");

        Double caixa = json.get("caixa").asDouble();

        Empresa empresa = e.get();
        empresa.setCaixa(caixa + empresa.getCaixa());
        CaixaMovimentacao movimentacao = new CaixaMovimentacao();
        movimentacao.setValor(caixa);
        movimentacao.setData(new Date());
        movimentacao.setAtivo(true);
        movimentacao.setTipoOperacao("Empréstimo");
        caixaServiceImpl.salvar(movimentacao);
        empresa.getCaixas().add(movimentacao);
        

        try {

            empresa = empresaService.salvar(empresa);
            if (empresa == null)
                return new ResponseEntity<Empresa>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<Empresa>(empresa, HttpStatus.CREATED);
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();

        }

    }

    @CrossOrigin
    @GetMapping(value="/buscarEmpresaTipo")
    public ResponseEntity<ArrayList<CaixaMovimentacao>> getCaixaByEmpresaTipo(@RequestParam(value = "idEmpresa") Integer idEmpresa,
     @RequestParam(value = "tipo") String tipo){
        ArrayList<CaixaMovimentacao> equipe = (ArrayList<CaixaMovimentacao>) caixaServiceImpl.buscaTodasCaixasPorEmpresaId(idEmpresa);

        if(equipe == null)
            return new ResponseEntity<ArrayList<CaixaMovimentacao>>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ArrayList<CaixaMovimentacao>>(equipe, HttpStatus.OK);
    }
}
