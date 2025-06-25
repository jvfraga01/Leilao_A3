import java.util.ArrayList;

public class Lote implements Leiloavel {
    private String nomeLote;
    private String descricao;
    private ArrayList<ItemLeilao> itensNoLote;
    private ArrayList<Lance> lancesRecebidos;

    public Lote(String nomeLote, String descricao) {
        this.nomeLote = nomeLote;
        this.descricao = descricao;
        this.itensNoLote = new ArrayList<>();
        this.lancesRecebidos = new ArrayList<>();
    }

    public String getNomeLote() { return nomeLote; }
    public void setNomeLote(String nomeLote) { this.nomeLote = nomeLote; }
    public ArrayList<ItemLeilao> getItensNoLote() { return itensNoLote; }

    public void adicionarItem(ItemLeilao item) {
        this.itensNoLote.add(item);
    }

    @Override
    public String obterDescricao() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itensNoLote.size(); i++) {
            sb.append(itensNoLote.get(i).getNome());
            if (i < itensNoLote.size() - 1) {
                sb.append(", ");
            }
        }
        return "[Lote] " + nomeLote + ": " + descricao + "\n  (Contém: " + sb.toString() + ")";
    }

    @Override
    public double obterValorMinimo() {

        double valorTotal = 0.0;
        for (ItemLeilao item : itensNoLote) {
            valorTotal += item.obterValorMinimo();
        }
        return valorTotal;
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