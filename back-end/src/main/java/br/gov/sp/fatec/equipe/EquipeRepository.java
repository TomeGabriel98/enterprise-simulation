package br.gov.sp.fatec.equipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Integer> {
    //public List<Equipe> findByEmpresaId(Integer id);
    public Equipe findByTipoAndRegiao(String equipeTipo, String regiao);
}
