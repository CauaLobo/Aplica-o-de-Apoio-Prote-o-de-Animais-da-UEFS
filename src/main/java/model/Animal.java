package model;

/**
 * Representa um animal cadastrado no sistema.
 * Contém informações como nome, espécie, raça, idade, sexo, situação atual e setor responsável.
 */
public class Animal {
    /**
     * Contador estático para geração automática de IDs.
     */
    private static int contadorIds = 1;

    /** Identificador único do animal. */
    private int id;
    /** Nome do animal. */
    private String nome;
    /** Espécie do animal. */
    private Especie especie;
    /** Raça do animal. */
    private String raca;
    /** Idade do animal. */
    private int idade;
    /** Sexo do animal. */
    private String sexo;
    /** Situação atual do animal. */
    private SituacaoAtual situacaoAtual;
    /** Setor responsável pelo animal. */
    private SetorResponsavel setorResponsavel;

    /**
     * Construtor padrão para uso em carga de dados.
     */
    public Animal() {}

    /**
     * Construtor completo para criação de um novo animal.
     * @param nome Nome do animal
     * @param especie Espécie do animal
     * @param raca Raça do animal
     * @param idade Idade do animal
     * @param sexo Sexo do animal
     * @param situacaoAtual Situação atual do animal
     * @param setorResponsavel Setor responsável pelo animal
     */
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

    /**
     * Define o valor do contador de IDs (usado na carga de dados).
     * @param valor Novo valor do contador
     */
    public static void setContadorIds(int valor) {
        contadorIds = valor;
    }

    /**
     * Define o ID do animal (usado na carga de dados).
     * @param id Novo ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Converte os dados do animal para o formato JSON.
     * @return String no formato JSON com os dados do animal
     */
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
