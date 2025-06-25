import java.util.ArrayList;

public class ItemLeilao implements Leiloavel {
    private String nome;
    private String descricao;
    private double valorInicial;
    private ArrayList<Lance> lancesRecebidos;

    public ItemLeilao(String nome, String descricao, double valorInicial) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorInicial = valorInicial;
        this.lancesRecebidos = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValorInicial(double valorInicial) { this.valorInicial = valorInicial; }

    @Override
    public String obterDescricao() {
        return "[Item] " + nome + ": " + descricao;
    }

    @Override
    public double obterValorMinimo() {
        return valorInicial;
    }

    @Override
    public void adicionarLance(Lance lance) {
        this.lancesRecebidos.add(lance);
    }

    @Override
    public ArrayList<Lance> getLancesRecebidos() {
        return lancesRecebidos;
    }
}