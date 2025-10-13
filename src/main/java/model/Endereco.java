package model;

/**
 * Representa um endereço associado a um tutor no sistema.
 * Contém informações de rua, bairro, CEP, cidade e estado.
 */
public class Endereco {
    /** Rua do endereço. */
    private String rua;
    /** Bairro do endereço. */
    private String bairro;
    /** CEP do endereço. */
    private String cep;
    /** Cidade do endereço. */
    private String cidade;
    /** Estado do endereço. */
    private String estado;

    /**
     * Construtor padrão para uso em carga de dados.
     */
    public Endereco() {}

    /**
     * Construtor completo para criação de um novo endereço.
     * @param rua Rua
     * @param bairro Bairro
     * @param cep CEP
     * @param cidade Cidade
     * @param estado Estado
     */
    public Endereco(String rua, String bairro, String cep, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    /** Retorna a rua do endereço. */
    public String getRua() { return rua; }
    /** Define a rua do endereço. */
    public void setRua(String rua) { this.rua = rua; }
    /** Retorna o bairro do endereço. */
    public String getBairro() { return bairro; }
    /** Define o bairro do endereço. */
    public void setBairro(String bairro) { this.bairro = bairro; }
    /** Retorna o CEP do endereço. */
    public String getCep() { return cep; }
    /** Define o CEP do endereço. */
    public void setCep(String cep) { this.cep = cep; }
    /** Retorna a cidade do endereço. */
    public String getCidade() { return cidade; }
    /** Define a cidade do endereço. */
    public void setCidade(String cidade) { this.cidade = cidade; }
    /** Retorna o estado do endereço. */
    public String getEstado() { return estado; }
    /** Define o estado do endereço. */
    public void setEstado(String estado) { this.estado = estado; }

    /**
     * Converte os dados do endereço para o formato JSON.
     * @return String em formato JSON com os dados do endereço
     */
    public String toJson() {
        return String.format("{\n" +
                "      \"rua\": \"%s\",\n" +
                "      \"bairro\": \"%s\",\n" +
                "      \"cep\": \"%s\",\n" +
                "      \"cidade\": \"%s\",\n" +
                "      \"estado\": \"%s\"\n" +
                "    }", rua, bairro, cep, cidade, estado);
    }
}
