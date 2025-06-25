import java.time.LocalDateTime;

public class Lance {
    private double valor;
    private LocalDateTime dataHoraLance;
    private Licitante licitante;
    private Leiloavel itemLeiloado;

    public Lance(double valor, Licitante licitante, Leiloavel itemLeiloado) {
        this.valor = valor;
        this.licitante = licitante;
        this.itemLeiloado = itemLeiloado;
        this.dataHoraLance = LocalDateTime.now();
    }

    public double getValor() { return valor; }
    public Licitante getLicitante() { return licitante; }
    public Leiloavel getItemLeiloado() { return itemLeiloado; }

    @Override
    public String toString() {
        return "Lance de R$" + String.format("%.2f", valor) + " por " + licitante.getNome();
    }
}