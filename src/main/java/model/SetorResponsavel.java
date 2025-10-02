package model;

public class SetorResponsavel {
    private int id;
    private String nome;
    private String endereco;

    public SetorResponsavel(int id,String nome,String endereco){
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(){
        this.nome = nome;
    }
    public String getEndereco(){
        return endereco;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
}
