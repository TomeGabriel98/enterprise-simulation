package br.gov.sp.fatec.localizacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer> {
    public Optional<Localizacao> findById (Integer id);
    public Localizacao findByNome(String nome);
    public List<Localizacao> findAll();
}
