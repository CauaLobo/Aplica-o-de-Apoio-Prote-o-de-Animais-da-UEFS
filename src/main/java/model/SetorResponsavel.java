package model;

public class SetorResponsavel {
    private static int contadorIds = 1;

    private int id;
    private String nome;
    private String endereco;
    private Tutor tutorResponsavel;

    public SetorResponsavel(String nome,String endereco, Tutor tutorResponsavel){
        this.id = contadorIds;
        this.nome = nome;
        this.endereco = endereco;
        this.tutorResponsavel = tutorResponsavel;
        contadorIds++;
    }

    public int getId() {
        return id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getEndereco(){
        return endereco;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public void atualizarSetor(SetorResponsavel novoSetor){
        this.nome = novoSetor.getNome();
        this.endereco = novoSetor.getEndereco();
        this.tutorResponsavel = novoSetor.getTutorResponsavel();
    }

    public Tutor getTutorResponsavel() {
        return tutorResponsavel;
    }

    public void setTutorResponsavel(Tutor tutorResponsavel) {
        this.tutorResponsavel = tutorResponsavel;
    }
}
