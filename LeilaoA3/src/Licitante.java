import java.util.ArrayList;

public class Licitante extends Pessoa {
    private String endereco;
    private ArrayList<Lance> lancesFeitos;

    public Licitante(String nome, String email, String endereco) {
        super(nome, email);
        this.endereco = endereco;
        this.lancesFeitos = new ArrayList<>();
    }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public ArrayList<Lance> getLancesFeitos() { return lancesFeitos; }

    @Override
    public String apresentarDados() {
        return "Licitante: " + nome + "\nEmail: " + email + "\nEndereço: " + endereco;
    }

    public void adicionarLance(Lance lance) {
        this.lancesFeitos.add(lance);
    }
}