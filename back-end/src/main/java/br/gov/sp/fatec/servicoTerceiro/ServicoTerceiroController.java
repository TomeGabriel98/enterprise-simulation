package br.gov.sp.fatec.servicoTerceiro;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value="/servico",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ServicoTerceiroController{

    @Autowired
    private ServicoTerceiroService servicoService;
    
	@Autowired
	private EmpresaServiceImpl empresaService;

    @GetMapping(value="/buscarPorId")
    @JsonView(ServicoTerceiroView.ServicoTerceiroCompleto.class)
    public ResponseEntity<ServicoTerceiro> getServicoById(@RequestParam(value = "id") Integer id){
        Optional<ServicoTerceiro> servico = servicoService.buscarPorId(id);

        if(servico.isPresent()){
            return new ResponseEntity<ServicoTerceiro>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ServicoTerceiro>(servico.get(), HttpStatus.OK);
    }

    @DeleteMapping(value="/excluir")
    @JsonView(ServicoTerceiroView.ServicoTerceiroCompleto.class)
    public ResponseEntity<ServicoTerceiro> excluir(@RequestBody ServicoTerceiro servico) {
        try {
            ServicoTerceiro s = servicoService.excluir(servico);

            if (s == null) {
                return new ResponseEntity<ServicoTerceiro>(HttpStatus.BAD_REQUEST);
            } else
                return new ResponseEntity<ServicoTerceiro>(s, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @CrossOrigin
    @GetMapping(value="/buscarEmpresaTipo")
    public ResponseEntity<ServicoTerceiro> getServicoTerceiroByEmpresaTipo(@RequestParam(value = "idEmpresa") Integer idEmpresa,
     @RequestParam(value = "tipo") String tipo){
        Optional<ServicoTerceiro> servicoTerceiro = servicoService.buscarPorId(idEmpresa);

        if(servicoTerceiro == null)
            return new ResponseEntity<ServicoTerceiro>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ServicoTerceiro>(servicoTerceiro.get(), HttpStatus.OK);
    }
    
	@CrossOrigin
	@RequestMapping(value="contrataServicoTerceiro/{idEmpresa}/", method=RequestMethod.POST)
	public ResponseEntity<Empresa> compraServicoTerceiro(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception{
		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();

		Integer quantidade = Integer.parseInt(json.get("quantidade").asText());
		String regiao = json.get("regiao").asText();
		String tipoServico = json.get("tipo").asText();

		ServicoTerceiro servicoTerceiro = servicoService.salvar(regiao, tipoServico, empresa, quantidade);

        List<ServicoTerceiro> servicosTerceiros = empresa.getServico();

        if (!servicosTerceiros.contains(servicoTerceiro)){
            servicosTerceiros.add(servicoTerceiro);
            empresa.setServico(servicosTerceiros);
            empresaService.salvar(empresa);
        }

		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(empresa, responseHeaders, HttpStatus.CREATED);
	}

    @CrossOrigin
    @RequestMapping(value="demiteServicoTerceiro/{idEmpresa}/", method=RequestMethod.POST)
    public ResponseEntity<Object> demiteEquipe(@PathVariable Integer idEmpresa, @RequestBody ObjectNode json) throws Exception{
        Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
        Integer quantidade = Integer.parseInt(json.get("quantidade").asText());
        String regiao = json.get("regiao").asText();
        String tipoServico = json.get("tipo").asText();

        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Object>(servicoService.editar(regiao, tipoServico, empresa, quantidade), responseHeaders, HttpStatus.CREATED);
    }
}