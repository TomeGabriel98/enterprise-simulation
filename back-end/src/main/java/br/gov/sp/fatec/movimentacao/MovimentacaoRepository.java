package br.gov.sp.fatec.movimentacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {

    public Optional<Movimentacao> findByEstoqueId(Integer id);
}
