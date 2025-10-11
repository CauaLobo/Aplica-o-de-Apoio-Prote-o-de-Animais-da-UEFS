package model;

public class Animal {
    private static int contadorIds = 1;

    private int id; // Identificador único
    private String nome;
    private Especie especie;
    private String raca;
    private int idade;
    private String sexo;
    private SituacaoAtual situacaoAtual;
    private SetorResponsavel setorResponsavel;

    public Animal(String nome, Especie especie, String raca, int idade, String sexo, SituacaoAtual situacaoAtual, SetorResponsavel setorResponsavel) {
        this.id = contadorIds;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        setIdade(idade); // validação no setter
        this.sexo = sexo;
        this.situacaoAtual = situacaoAtual;
        this.setorResponsavel = setorResponsavel;
        contadorIds++;
    }

    // ID
    public int getId() {
        return id;
    }

    // Nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Espécie
    public Especie getEspecie() {
        return especie;
    }
    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    // Raça
    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        this.raca = raca;
    }

    // Idade (com validação) FEITO PELO CHAT *ENTENDER*
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        if (idade >= 0) {
            this.idade = idade;
        } else {
            throw new IllegalArgumentException("A idade não pode ser negativa.");
        }
    }

    // Sexo
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    // Situação Atual
    public SituacaoAtual getSituacaoAtual() {
        return situacaoAtual;
    }
    public void setSituacaoAtual(SituacaoAtual situacaoAtual) {
        this.situacaoAtual = situacaoAtual;
    }

    public void atualizar(Animal novoAnimal){
        this.nome= novoAnimal.getNome();
        this.especie = novoAnimal.getEspecie();
        this.raca = novoAnimal.getRaca();
        this.sexo = novoAnimal.getSexo();
        this.situacaoAtual = novoAnimal.getSituacaoAtual();
        this.setIdade(novoAnimal.getIdade());
        this.setorResponsavel = novoAnimal.getSetorResponsavel();

    }

    // Setor Responsável
    public SetorResponsavel getSetorResponsavel() {
        return setorResponsavel;
    }
}
