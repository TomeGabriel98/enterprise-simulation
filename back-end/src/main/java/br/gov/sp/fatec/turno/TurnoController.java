package br.gov.sp.fatec.turno;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;


@RestController
@CrossOrigin
@RequestMapping(value="/turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    
    @Autowired
    private EmpresaServiceImpl empresaService;

    
    @CrossOrigin
    @PostMapping(value="/travarTurno/{id}")
    public ResponseEntity<List<Turno>> travarTodos(@PathVariable Long id){
    	Turno turno = turnoService.buscaPorId(id);
    	turnoService.travarTodos(turno);
    	List<Turno> turnos = turnoService.listarTodos();
    	return new ResponseEntity<List<Turno>>(turnos, HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping(value="/mudarTurno/{id}")
    public ResponseEntity<List<Turno>> mudarTurno(@PathVariable Long id){
    	Turno turno = turnoService.buscaPorId(id);
    	List<Turno> turnos = turnoService.mudarTurno(turno);
    	return new ResponseEntity<List<Turno>>(turnos, HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping(value="addToTurno/{idTurno}/{idEmpresa}/")
    public ResponseEntity<Turno> addToTurno(@PathVariable Long idTurno, @PathVariable Integer idEmpresa){
    	Turno turno = turnoService.buscaPorId(idTurno);
    	Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
    	List<Empresa> empresasTurno = turno.getEmpresas();
    	empresasTurno.add(empresa);
    	turnoService.salvar(turno);
    	return new ResponseEntity<Turno>(turno, HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping(value="/novoTurno/")
    public ResponseEntity<Turno> novoTurno(@RequestBody ObjectNode json){
    	String nome = json.get("nome").asText();
    	Turno turno = turnoService.novoTurno(nome);
    	return new ResponseEntity<Turno>(turno, HttpStatus.CREATED);
    }
    
    @CrossOrigin
    @DeleteMapping(value="/apagaTurno/{id}")
    public ResponseEntity<List<Turno>> apagaTurno(@PathVariable Long id){
    	Turno turno = turnoService.buscaPorId(id);
    	List<Turno> t = turnoService.deleteTurno(turno);
    	return new ResponseEntity<List<Turno>>(t, HttpStatus.OK);    	
    }

    @GetMapping(value="/getAll")
    public ResponseEntity<Collection<Turno>> listarTodos(){
    	return new ResponseEntity<Collection<Turno>>(turnoService.listarTodos(), HttpStatus.OK);
    }

}