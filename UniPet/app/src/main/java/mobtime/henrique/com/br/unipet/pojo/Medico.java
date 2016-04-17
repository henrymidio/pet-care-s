package mobtime.henrique.com.br.unipet.pojo;

/**
 * Created by Henrique on 16/04/2016.
 */
public class Medico {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    private String registro;
    private int foto;

    public Medico(String nome, String registro, int foto) {
        this.nome = nome;
        this.registro = registro;
        this.foto = foto;
    }

    public Medico(){

    }
}
