import java.util.ArrayList;

public interface Leiloavel {
    String obterDescricao();
    double obterValorMinimo();
    void adicionarLance(Lance lance);
    ArrayList<Lance> getLancesRecebidos();
}