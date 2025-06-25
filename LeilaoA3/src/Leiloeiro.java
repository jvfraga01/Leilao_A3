import java.util.ArrayList;

public class Leiloeiro extends Pessoa {
    private String numeroRegistro;
    private ArrayList<Leilao> leiloesCriados;

    public Leiloeiro(String nome, String email, String numeroRegistro) {
        super(nome, email);
        this.numeroRegistro = numeroRegistro;
        this.leiloesCriados = new ArrayList<>();
    }

    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }
    public ArrayList<Leilao> getLeiloesCriados() { return leiloesCriados; }

    @Override
    public String apresentarDados() {
        return "Leiloeiro: " + nome + "\nEmail: " + email + "\nRegistro: " + numeroRegistro;
    }
}