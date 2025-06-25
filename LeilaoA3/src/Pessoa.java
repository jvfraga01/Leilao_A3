import javax.swing.JOptionPane;

public abstract class Pessoa implements Notificavel {
    protected String nome;
    protected String email;

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public abstract String apresentarDados();

    @Override
    public void enviarNotificacao(String mensagem) {
        JOptionPane.showMessageDialog(null, "Notificação para " + this.nome + ":\n" + mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}