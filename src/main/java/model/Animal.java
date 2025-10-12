package model;

public class Animal {
    private static int contadorIds = 1;

    private int id;
    private String nome;
    private Especie especie;
    private String raca;
    private int idade;
    private String sexo;
    private SituacaoAtual situacaoAtual;
    private SetorResponsavel setorResponsavel;

    // Construtor para uso na carga de dados
    public Animal() {}

    public Animal(String nome, Especie especie, String raca, int idade, String sexo, SituacaoAtual situacaoAtual, SetorResponsavel setorResponsavel) {
        this.id = contadorIds++;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        setIdade(idade);
        this.sexo = sexo;
        this.situacaoAtual = situacaoAtual;
        this.setorResponsavel = setorResponsavel;
    }

    // Adicione este método estático
    public static void setContadorIds(int valor) {
        contadorIds = valor;
    }

    // Adicione este setter
    public void setId(int id) {
        this.id = id;
    }

    // ... resto da classe ...
    public String toJson() {
        return String.format("    {\n" +
                "      \"id\": %d,\n" +
                "      \"nome\": \"%s\",\n" +
                "      \"especie\": \"%s\",\n" +
                "      \"raca\": \"%s\",\n" +
                "      \"idade\": %d,\n" +
                "      \"sexo\": \"%s\",\n" +
                "      \"situacaoAtual\": \"%s\",\n" +
                "      \"setorResponsavelId\": %d\n" +
                "    }", id, nome, especie.name(), raca, idade, sexo, situacaoAtual.name(), setorResponsavel != null ? setorResponsavel.getId() : 0);
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) {
        if (idade >= 0) { this.idade = idade; }
        else { throw new IllegalArgumentException("A idade não pode ser negativa."); }
    }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public SituacaoAtual getSituacaoAtual() { return situacaoAtual; }
    public void setSituacaoAtual(SituacaoAtual situacaoAtual) { this.situacaoAtual = situacaoAtual; }
    public SetorResponsavel getSetorResponsavel() { return setorResponsavel; }
    public void setSetorResponsavel(SetorResponsavel setorResponsavel) { this.setorResponsavel = setorResponsavel; }
    public void atualizar(Animal novoAnimal){
        this.nome= novoAnimal.getNome();
        this.especie = novoAnimal.getEspecie();
        this.raca = novoAnimal.getRaca();
        this.sexo = novoAnimal.getSexo();
        this.situacaoAtual = novoAnimal.getSituacaoAtual();
        this.setIdade(novoAnimal.getIdade());
        this.setorResponsavel = novoAnimal.getSetorResponsavel();
    }
}
