package br.gov.sp.fatec.localizacao;

import java.util.List;
import java.util.Optional;

public interface LocalizacaoService {
    public Localizacao salvar(Integer localizacao);
    public Localizacao excluir(Localizacao localizacao);
    public Localizacao editar(Localizacao localizacao);
    public Optional<Localizacao> buscarPorId(Integer id);
    public Localizacao buscarPorNome(String nome);
    public List<Localizacao> buscarTodos();
}
