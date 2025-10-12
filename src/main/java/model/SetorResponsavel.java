package model;

public class SetorResponsavel {
    private static int contadorIds = 1;

    private int id;
    private String nome;
    private String endereco;
    private Tutor tutorResponsavel;

    // Construtor para uso na carga de dados
    public SetorResponsavel() {}

    public SetorResponsavel(String nome,String endereco, Tutor tutorResponsavel){
        this.id = contadorIds++;
        this.nome = nome;
        this.endereco = endereco;
        this.tutorResponsavel = tutorResponsavel;
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
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Tutor getTutorResponsavel() { return tutorResponsavel; }
    public void setTutorResponsavel(Tutor tutorResponsavel) { this.tutorResponsavel = tutorResponsavel; }
    public void atualizarSetor(SetorResponsavel novoSetor){
        this.nome = novoSetor.getNome();
        this.endereco = novoSetor.getEndereco();
        this.tutorResponsavel = novoSetor.getTutorResponsavel();
    }
    public String toJson() {
        return String.format("    {\n" +
                "      \"id\": %d,\n" +
                "      \"nome\": \"%s\",\n" +
                "      \"endereco\": \"%s\",\n" +
                "      \"tutorResponsavelId\": %d\n" +
                "    }", id, nome, endereco, tutorResponsavel != null ? tutorResponsavel.getId() : 0);
    }
}
