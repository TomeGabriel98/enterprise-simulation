package br.gov.sp.fatec.armazemLevel;

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
@RequestMapping(value="/armazemLevel")
public class ArmazemLevelController {
	
	@Autowired
	public ArmazemLevelServiceImpl armazemServiceImpl;
	
	
	@RequestMapping(value="/getById/{id}", method=RequestMethod.GET)
	public ResponseEntity<ArmazemLevel> getArmazemLevelById(@PathVariable("id") String id){
        ArrayList<ArmazemLevel> armazemLevel = new ArrayList<ArmazemLevel>(armazemServiceImpl.buscaArmazemLevel(Integer.parseInt(id)));
        System.out.println(armazemLevel);
        if(armazemLevel == null)
            return new ResponseEntity<ArmazemLevel>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<ArmazemLevel>(armazemLevel.get(0), HttpStatus.OK);
    }

    @PostMapping(value="/salvar")
    public ResponseEntity<ArmazemLevel> salvar(@RequestBody ArmazemLevel armazemLevel){
        try {
        	ArmazemLevel e = armazemServiceImpl.salvar(armazemLevel);
            if (e == null)
                return new ResponseEntity<ArmazemLevel>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<ArmazemLevel>(e, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
