package model;

/**
 * Representa um tutor responsável por animais no sistema.
 * Contém informações pessoais e de contato, além do endereço.
 */
public class Tutor {
    /**
     * Contador estático para geração automática de IDs.
     */
    private static int contadorIds = 1;

    /** Identificador único do tutor. */
    private int id;
    /** Nome do tutor. */
    private String nome;
    /** Endereço do tutor. */
    private Endereco endereco;
    /** Telefone do tutor. */
    private String telefone;
    /** E-mail do tutor. */
    private String email;

    /**
     * Construtor padrão para uso em carga de dados.
     */
    public Tutor() {}

    /**
     * Construtor completo para criação de um novo tutor.
     * @param nome Nome do tutor
     * @param endereco Endereço do tutor
     * @param telefone Telefone do tutor
     * @param email E-mail do tutor
     */
    public Tutor(String nome, Endereco endereco, String telefone, String email) {
        this.id = contadorIds++;
        this.nome = nome;
        this.endereco = endereco;
        setTelefone(telefone);
        setEmail(email);
    }

    /**
     * Define o valor do contador de IDs (usado na carga de dados).
     * @param valor Novo valor do contador
     */
    public static void setContadorIds(int valor) {
        contadorIds = valor;
    }

    /**
     * Define o ID do tutor (usado na carga de dados).
     * @param id Novo ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Retorna o ID do tutor. */
    public int getId() { return id; }
    /** Retorna o nome do tutor. */
    public String getNome() { return nome; }
    /** Define um novo nome para o tutor. */
    public void setNome(String nome) { this.nome = nome; }
    /** Retorna o endereço do tutor. */
    public Endereco getEndereco() { return endereco; }
    /** Define um novo endereço para o tutor. */
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    /** Retorna o telefone do tutor. */
    public String getTelefone() { return telefone; }
    /** Define um novo telefone para o tutor. */
    public void setTelefone(String telefone) {
        if (telefone != null && telefone.matches("\\d{8,15}")) { this.telefone = telefone; }
        else { throw new IllegalArgumentException("Telefone inválido!"); }
    }
    /** Retorna o e-mail do tutor. */
    public String getEmail() { return email; }
    /** Define um novo e-mail para o tutor. */
    public void setEmail(String email) {
        if (email != null && email.contains("@")) { this.email = email; }
        else { throw new IllegalArgumentException("E-mail inválido!"); }
    }
    /**
     * Atualiza os dados do tutor com base em outro objeto Tutor.
     * @param novoTutor Objeto Tutor com os novos dados
     */
    public void atualizarTutor(Tutor novoTutor){
        this.nome = novoTutor.getNome();
        this.endereco = novoTutor.getEndereco();
        this.email = novoTutor.getEmail();
        this.telefone = novoTutor.getTelefone();
    }
    /**
     * Converte os dados do tutor para o formato JSON.
     * @return String em formato JSON com os dados do tutor
     */
    public String toJson() {
        return String.format("    {\n" +
                "      \"id\": %d,\n" +
                "      \"nome\": \"%s\",\n" +
                "      \"telefone\": \"%s\",\n" +
                "      \"email\": \"%s\",\n" +
                "      \"endereco\": %s\n" +
                "    }", id, nome, telefone, email, endereco.toJson());
    }
}
