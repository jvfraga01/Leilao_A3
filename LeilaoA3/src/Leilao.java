import java.time.LocalDateTime;
import java.util.ArrayList;

public class Leilao {
    private String titulo;
    private LocalDateTime dataInicio;
    private String status;
    private ArrayList<Leiloavel> itensDoLeilao;
    private ArrayList<Licitante> licitantesInscritos;
    private Leiloeiro responsavel;
    private ArrayList<Arremate> arremates;

    public Leilao(String titulo, Leiloeiro responsavel) {
        this.titulo = titulo;
        this.responsavel = responsavel;
        this.dataInicio = LocalDateTime.now();
        this.status = "Agendado";
        this.itensDoLeilao = new ArrayList<>();
        this.licitantesInscritos = new ArrayList<>();
        this.arremates = new ArrayList<>();
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Leiloeiro getResponsavel() { return responsavel; }
    public ArrayList<Leiloavel> getItensDoLeilao() { return itensDoLeilao; }
    public ArrayList<Licitante> getLicitantesInscritos() { return licitantesInscritos; }
    public ArrayList<Arremate> getArremates() { return arremates; }

    public void adicionarItem(Leiloavel item) { this.itensDoLeilao.add(item); }
    public void removerItem(Leiloavel item) { this.itensDoLeilao.remove(item); }
    public void inscreverLicitante(Licitante licitante) { this.licitantesInscritos.add(licitante); }
    public void adicionarArremate(Arremate arremate) { this.arremates.add(arremate); }

    @Override
    public String toString() {
        return "Leilão: " + titulo + " | Status: " + status + " | Itens: " + itensDoLeilao.size() + " | Licitantes: " + licitantesInscritos.size();
    }
}