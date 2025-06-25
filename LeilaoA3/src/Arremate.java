import java.time.LocalDateTime;

public class Arremate {
    private double valorFinal;
    private LocalDateTime dataArremate;
    private Licitante vencedor;
    private Leiloavel itemArrematado;

    public Arremate(double valorFinal, Licitante vencedor, Leiloavel itemArrematado) {
        this.valorFinal = valorFinal;
        this.vencedor = vencedor;
        this.itemArrematado = itemArrematado;
        this.dataArremate = LocalDateTime.now();
    }

    public Licitante getVencedor() { return vencedor; }
    public Leiloavel getItemArrematado() { return itemArrematado; }

    @Override
    public String toString() {
        return "ARREMATE: " + itemArrematado.obterDescricao() +
                "\nVencedor: " + vencedor.getNome() +
                "\nValor Final: R$" + String.format("%.2f", valorFinal);
    }
}