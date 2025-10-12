package model;

public class Endereco {
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;

    // Construtor para uso na carga de dados
    public Endereco() {}

    public Endereco(String rua, String bairro, String cep, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    // ... resto da classe (getters, setters, toJson) ...
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

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
