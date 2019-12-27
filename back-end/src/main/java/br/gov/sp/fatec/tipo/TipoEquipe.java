package br.gov.sp.fatec.tipo;

public enum TipoEquipe {

    Logistica(1, "Logisitica", 12000, 58500, 21000, 7200),
    Comercial(2, "Comercial", 5800, 28725, 22330, 7656),
    Infraestrutura(3, "Infraestrutura", 14800, 72150, 20720, 7104);

    int id;
    String nome;
    int contratacao;
    int salario;
    int desligamento;
    int treinamento;

    public String getNome(){
        return this.nome;
    }

    public int getContratacao(){
        return this.contratacao;
    }

    public int getSalario(){
        return this.salario;
    }

    public int getDesligamento(){
        return this.desligamento;
    }

    public int getTreinamento(){
        return this.treinamento;
    }

    TipoEquipe(int id, String nome, int contratacao, int salario, int desligamento, int treinamento){
        this.id = id;
        this.nome = nome;
        this.contratacao = contratacao;
        this.salario = salario;
        this.desligamento = desligamento;
        this.treinamento = treinamento;
    }

}