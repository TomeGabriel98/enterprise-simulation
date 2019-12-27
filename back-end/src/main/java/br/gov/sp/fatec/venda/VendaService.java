package br.gov.sp.fatec.venda;

import br.gov.sp.fatec.empresa.Empresa;

public interface VendaService {
    public Venda salvar(Empresa empresa, String produto, Integer quantidade, Double valor);

}
