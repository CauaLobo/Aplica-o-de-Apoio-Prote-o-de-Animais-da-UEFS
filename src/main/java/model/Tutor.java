package model;

public class Tutor {
    private static int contadorIds = 1;

    private int id; // Identificador único
    private String nome;
    private Endereco endereco;
    private String telefone;
    private String email;

    public Tutor(String nome, Endereco endereco, String telefone, String email) {
        this.id = contadorIds;
        this.nome = nome;
        this.endereco = endereco;
        setTelefone(telefone);
        setEmail(email);

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

    // Endereço
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Telefone (com validação simples)
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        if (telefone.matches("\\d{8,15}")) {
            this.telefone = telefone;
        } else {
            throw new IllegalArgumentException("Telefone inválido! Use apenas números (8 a 15 dígitos).");
        }
    }

    // Email (com validação simples)
    public String getEmail() {
        return email;
    }
    // FEITO PELO CHAT *ENTENDER*
    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("E-mail inválido!");
        }
    }
    public void atualizarTutor(Tutor novoTutor){
        this.nome = novoTutor.getNome();
        this.endereco = novoTutor.getEndereco();
        this.email = novoTutor.getEmail();
        this.telefone = novoTutor.getTelefone();
    }
}
