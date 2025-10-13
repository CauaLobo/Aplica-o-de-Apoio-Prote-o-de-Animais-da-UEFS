package model;

/**
 * Representa um setor responsável pelo cuidado de animais no sistema.
 * Cada setor possui um nome, endereço e um tutor responsável.
 */
public class SetorResponsavel {
    /**
     * Contador estático para geração automática de IDs.
     */
    private static int contadorIds = 1;

    /** Identificador único do setor. */
    private int id;
    /** Nome do setor responsável. */
    private String nome;
    /** Endereço do setor responsável. */
    private String endereco;
    /** Tutor responsável pelo setor. */
    private Tutor tutorResponsavel;

    /**
     * Construtor padrão para uso em carga de dados.
     */
    public SetorResponsavel() {}

    /**
     * Construtor completo para criação de um novo setor responsável.
     * @param nome Nome do setor
     * @param endereco Endereço do setor
     * @param tutorResponsavel Tutor responsável pelo setor
     */
    public SetorResponsavel(String nome,String endereco, Tutor tutorResponsavel){
        this.id = contadorIds++;
        this.nome = nome;
        this.endereco = endereco;
        this.tutorResponsavel = tutorResponsavel;
    }

    /**
     * Define o valor do contador de IDs (usado na carga de dados).
     * @param valor Novo valor do contador
     */
    public static void setContadorIds(int valor) {
        contadorIds = valor;
    }

    /**
     * Define o ID do setor (usado na carga de dados).
     * @param id Novo ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Retorna o ID do setor. */
    public int getId() { return id; }
    /** Retorna o nome do setor. */
    public String getNome() { return nome; }
    /** Define um novo nome para o setor. */
    public void setNome(String nome) { this.nome = nome; }
    /** Retorna o endereço do setor. */
    public String getEndereco() { return endereco; }
    /** Define um novo endereço para o setor. */
    public void setEndereco(String endereco) { this.endereco = endereco; }
    /** Retorna o tutor responsável pelo setor. */
    public Tutor getTutorResponsavel() { return tutorResponsavel; }
    /** Define o tutor responsável pelo setor. */
    public void setTutorResponsavel(Tutor tutorResponsavel) { this.tutorResponsavel = tutorResponsavel; }
    /**
     * Atualiza os dados do setor com base em outro objeto SetorResponsavel.
     * @param novoSetor Objeto SetorResponsavel com os novos dados
     */
    public void atualizarSetor(SetorResponsavel novoSetor){
        this.nome = novoSetor.getNome();
        this.endereco = novoSetor.getEndereco();
        this.tutorResponsavel = novoSetor.getTutorResponsavel();
    }
    /**
     * Converte os dados do setor para o formato JSON.
     * @return String em formato JSON com os dados do setor
     */
    public String toJson() {
        return String.format("    {\n" +
                "      \"id\": %d,\n" +
                "      \"nome\": \"%s\",\n" +
                "      \"endereco\": \"%s\",\n" +
                "      \"tutorResponsavelId\": %d\n" +
                "    }", id, nome, endereco, tutorResponsavel != null ? tutorResponsavel.getId() : 0);
    }
}
