package br.gov.sp.fatec.movimentacao;

import java.util.Optional;

public interface MovimentacaoService {

    public Movimentacao salvar(Movimentacao movimentacao);
    public Optional<Movimentacao> buscaPorEstoqueId(Integer id);

}
