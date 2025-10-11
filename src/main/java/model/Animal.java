package model;

public class Animal {
    private int id; // Identificador único
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String sexo;
    private SituacaoAtual situacaoAtual;
    private int SetorResponsavelId; // ID do setor responsável

    public Animal(int id, String nome, String especie, String raca, int idade, String sexo, SituacaoAtual situacaoAtual, int setorResponsavelId) {
        this.SetorResponsavelId = setorResponsavelId;
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        setIdade(idade); // validação no setter
        this.sexo = sexo;
        this.situacaoAtual = situacaoAtual;
    }

    // ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Espécie
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
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

    }
    public int getSetorResponsavelId() {
        return SetorResponsavelId;
    }

    public void setSetorResponsavelId(int setorResponsavelId) {
        SetorResponsavelId = setorResponsavelId;
    }
}
