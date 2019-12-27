package br.gov.sp.fatec.movimentacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MovimentacaoServiceImpl implements MovimentacaoService{

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Override
    public Movimentacao salvar(Movimentacao movimentacao) {
        return movimentacaoRepository.save(movimentacao);
    }

    @Override
    public Optional<Movimentacao> buscaPorEstoqueId(Integer id) {
        return movimentacaoRepository.findByEstoqueId(id);
    }
}
