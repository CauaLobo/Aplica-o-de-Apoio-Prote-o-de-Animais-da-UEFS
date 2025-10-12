package model;

public class Tutor {
    private static int contadorIds = 1;

    private int id;
    private String nome;
    private Endereco endereco;
    private String telefone;
    private String email;

    // Construtor para uso na carga de dados
    public Tutor() {}

    public Tutor(String nome, Endereco endereco, String telefone, String email) {
        this.id = contadorIds++;
        this.nome = nome;
        this.endereco = endereco;
        setTelefone(telefone);
        setEmail(email);
    }

    // Adicione este método estático
    public static void setContadorIds(int valor) {
        contadorIds = valor;
    }

    // Adicione este setter
    public void setId(int id) {
        this.id = id;
    }

    // ... resto da classe (getters, setters, toJson, etc.) ...
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) {
        if (telefone != null && telefone.matches("\\d{8,15}")) { this.telefone = telefone; }
        else { throw new IllegalArgumentException("Telefone inválido!"); }
    }
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null && email.contains("@")) { this.email = email; }
        else { throw new IllegalArgumentException("E-mail inválido!"); }
    }
    public void atualizarTutor(Tutor novoTutor){
        this.nome = novoTutor.getNome();
        this.endereco = novoTutor.getEndereco();
        this.email = novoTutor.getEmail();
        this.telefone = novoTutor.getTelefone();
    }
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
