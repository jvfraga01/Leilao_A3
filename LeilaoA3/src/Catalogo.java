import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Catalogo {
    private LocalDateTime ultimaAtualizacao;
    private ArrayList<Leiloavel> itensDisponiveis;

    public Catalogo() {
        this.ultimaAtualizacao = LocalDateTime.now();
        this.itensDisponiveis = new ArrayList<>();
    }

    public ArrayList<Leiloavel> getItensDisponiveis() { return itensDisponiveis; }

    public void adicionarItem(Leiloavel item) {
        this.itensDisponiveis.add(item);
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public void removerItem(Leiloavel item) {
        this.itensDisponiveis.remove(item);
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public String listarItens() {
        if (itensDisponiveis.isEmpty()) {
            return "O catálogo está vazio.";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder("Itens no Catálogo (Atualizado em: " + ultimaAtualizacao.format(formatter) + "):\n\n");
        for (int i = 0; i < itensDisponiveis.size(); i++) {
            Leiloavel item = itensDisponiveis.get(i);
            sb.append(i).append(": ").append(item.obterDescricao())
                    .append("\n  Valor Mínimo: R$").append(String.format("%.2f", item.obterValorMinimo()))
                    .append("\n---------------------------------\n");
        }
        return sb.toString();
    }
}