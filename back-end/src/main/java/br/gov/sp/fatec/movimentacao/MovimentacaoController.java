package br.gov.sp.fatec.movimentacao;

import br.gov.sp.fatec.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping(value="/movimentacao")
public class MovimentacaoController {

    @Autowired
    public MovimentacaoService movimentacaoService;

    @CrossOrigin
    @RequestMapping(value="/getById/{id}", method= RequestMethod.GET)
    @JsonView(View.Movimentacao.class)
    public ResponseEntity<Movimentacao> getEstoqueaById(@PathVariable("id") String id){
        Optional<Movimentacao> movimentacao = movimentacaoService.buscaPorEstoqueId(Integer.parseInt(id));
        System.out.println(movimentacao);
        return movimentacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value="/save")
    @JsonView(View.Movimentacao.class)
    public ResponseEntity<Movimentacao> salvar(@RequestBody Movimentacao movimentacao, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        movimentacao = movimentacaoService.salvar(movimentacao);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponentsBuilder.path("/getById/" + movimentacao.getId()).build().toUri());

        return new ResponseEntity<Movimentacao>(movimentacao, responseHeaders, HttpStatus.CREATED);
    }


}
