package br.gov.sp.fatec.estoque;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value="/estoque")
public class EstoqueController {
	
	@Autowired
	public EstoqueServiceImpl estoqueServiceImpl;
	
	@RequestMapping(value="/getById/{id}", method=RequestMethod.GET)
	public ResponseEntity<Estoque> getEstoqueById(@PathVariable("id") String id){
        ArrayList<Estoque> estoque = new ArrayList<Estoque>(estoqueServiceImpl.buscaEstoque(Integer.parseInt(id)));
        System.out.println(estoque);
        if(estoque == null)
            return new ResponseEntity<Estoque>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<Estoque>(estoque.get(0), HttpStatus.OK);
    }

    @PostMapping(value="/salvar")
    public ResponseEntity<Estoque> salvar(@RequestBody Estoque estoque){
        try {
        	Estoque e = estoqueServiceImpl.salvar(estoque);
            if (e == null)
                return new ResponseEntity<Estoque>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<Estoque>(e, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
