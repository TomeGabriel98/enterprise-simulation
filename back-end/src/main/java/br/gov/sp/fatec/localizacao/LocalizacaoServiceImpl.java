package br.gov.sp.fatec.localizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.autorizacao.Autorizacao;
import br.gov.sp.fatec.tipo.TipoLocalizacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocalizacaoServiceImpl implements LocalizacaoService{

    @Autowired
    private LocalizacaoRepository localizacaoRepository;
    

    @Override
    public Localizacao salvar(Integer tipoLocalizacao) {
    	Localizacao localizacao = new Localizacao();
    	TipoLocalizacao tipoLoc;
    	switch (tipoLocalizacao) {
		case 1:
			tipoLoc = TipoLocalizacao.Norte;
			localizacao.setNome(tipoLoc.getNome());
			localizacao.setOpera(true);
			localizacao.setMatriz(true);
			break;
			
		case 2:
			tipoLoc = TipoLocalizacao.Nordeste;
			localizacao.setNome(tipoLoc.getNome());
			localizacao.setOpera(true);
			localizacao.setMatriz(true);
			break;
			
		case 3:
			tipoLoc = TipoLocalizacao.SulSudeste;
			localizacao.setNome(tipoLoc.getNome());
			localizacao.setOpera(true);
			localizacao.setMatriz(true);
			break;

		}
        return localizacaoRepository.save(localizacao);
    }

    @Override
    public Localizacao excluir(Localizacao localizacao) {
        localizacaoRepository.delete(localizacao);

        return localizacao;
    }

    @Override
    public Localizacao editar(Localizacao localizacao) {
        if (localizacaoRepository.findById(localizacao.getId()) != null) {
            return localizacaoRepository.save(localizacao);
        }
        return null;
    }

    @Override
    public Optional<Localizacao> buscarPorId(Integer id) {
        return localizacaoRepository.findById(id);
    }
    
    @Override
    public Localizacao buscarPorNome(String nome) {
        return localizacaoRepository.findByNome(nome);
    }
    
    public List<Localizacao> buscarTodos(){
    	return localizacaoRepository.findAll();
    }

	public ArrayList<Localizacao> criaLocaLizacaoList() {
		TipoLocalizacao tipoNorte = TipoLocalizacao.Norte;
		TipoLocalizacao tipoNordeste = TipoLocalizacao.Nordeste;
		TipoLocalizacao tipoSul = TipoLocalizacao.SulSudeste;
		ArrayList<Localizacao> lista = new ArrayList<Localizacao>();
		Localizacao norte = new Localizacao();
		norte.setNome(tipoNorte.getNome());
		localizacaoRepository.save(norte);
		Localizacao nordeste = new Localizacao();
		nordeste.setNome(tipoNordeste.getNome());
		localizacaoRepository.save(nordeste);
		Localizacao sul = new Localizacao();
		sul.setNome(tipoSul.getNome());
		localizacaoRepository.save(sul);
		lista.add(norte);
		lista.add(nordeste);
		lista.add(sul);
		return lista;
	}
}