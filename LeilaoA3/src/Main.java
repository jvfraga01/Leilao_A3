import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    private static ArrayList<Pessoa> pessoas = new ArrayList<>();
    private static Catalogo catalogo = new Catalogo();
    private static ArrayList<Leilao> leiloes = new ArrayList<>();
    private static Map<Licitante, Integer> passesConsecutivos = new HashMap<>();

    public static void main(String[] args) {
        exibirMenuPrincipal();
    }

    public static void exibirMenuPrincipal() {
        String[] options = {"Gerenciar Pessoas", "Gerenciar Catálogo", "Gerenciar Leilões", "Ver Arremates", "Sair"};
        int escolha = 0;
        while (escolha != 4 && escolha != -1) {
            escolha = JOptionPane.showOptionDialog(null, "Sistema de Leilões - Menu Principal", "Leilão",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (escolha == 0) {
                gerenciarPessoas();
            } else if (escolha == 1) {
                gerenciarCatalogo();
            } else if (escolha == 2) {
                gerenciarLeiloes();
            } else if (escolha == 3) {
                verArremates();
            }
        }
    }

    private static void verArremates() {
        StringBuilder sb = new StringBuilder("=== Arremates Realizados ===\n\n");
        boolean encontrouArremates = false;
        for (Leilao leilao : leiloes) {
            if (!leilao.getArremates().isEmpty()) {
                encontrouArremates = true;
                sb.append("Leilão: ").append(leilao.getTitulo()).append("\n");
                for (Arremate arremate : leilao.getArremates()) {
                    sb.append("  - ").append(arremate.toString().replace("\n", "\n    ")).append("\n");
                }
                sb.append("--------------------\n");
            }
        }

        if (!encontrouArremates) {
            sb.append("Nenhum arremate realizado.");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void gerenciarPessoas() {
        String[] options = {"Adicionar Pessoas", "Listar Pessoas", "Editar Pessoa", "Excluir Pessoa", "Voltar"};
        int escolha = 0;
        while (escolha != 4 && escolha != -1) {
            escolha = JOptionPane.showOptionDialog(null, "Menu de Pessoas", "Gerenciar Pessoas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if(escolha == 0) {
                adicionarPessoa();
            } else if (escolha == 1) {
                listarPessoas();
            } else if (escolha == 2) {
                editarPessoa();
            } else if (escolha == 3) {
                excluirPessoa();
            }
        }
    }

    private static void adicionarPessoa() {
        String[] tipos = {"Leiloeiro", "Licitante"};
        int tipo = JOptionPane.showOptionDialog(null, "Qual tipo de pessoa?", "Adicionar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipo == -1) return;

        String nome = JOptionPane.showInputDialog("Nome:");
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome é obrigatório. Operação cancelada.");
            return;
        }
        String email = JOptionPane.showInputDialog("Email:");
        if (email == null || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email é obrigatório. Operação cancelada.");
            return;
        }

        if(tipo == 0) { // Leiloeiro
            String registro = JOptionPane.showInputDialog("Número de Registro:");
            if (registro == null || registro.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O registro é obrigatório. Operação cancelada.");
                return;
            }
            pessoas.add(new Leiloeiro(nome, email, registro));
            JOptionPane.showMessageDialog(null, "Leiloeiro adicionado com sucesso!");
        } else { // Licitante
            String endereco = JOptionPane.showInputDialog("Endereço:");
            if (endereco == null || endereco.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O endereço é obrigatório. Operação cancelada.");
                return;
            }
            pessoas.add(new Licitante(nome, email, endereco));
            JOptionPane.showMessageDialog(null, "Licitante adicionado com sucesso!");
        }

    }

    private static void listarPessoas() {
        StringBuilder sb = new StringBuilder("=== Lista de Pessoas ===\n\n");
        if (pessoas.isEmpty()) {
            sb.append("Nenhuma pessoa cadastrada.");
        } else {
            for(int i = 0; i < pessoas.size(); i++) {
                Pessoa p = pessoas.get(i);
                sb.append(p.apresentarDados()).append("\n--------------------\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void editarPessoa() {
        Pessoa p = selecionarPessoa("editar");
        if (p == null) return;
        
        String novoNome = JOptionPane.showInputDialog("Novo nome:", p.getNome());
        if (novoNome != null) { // Se o usuário não clicou em "Cancelar"
            if (novoNome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo Nome não pode ser vazio. O valor anterior foi mantido.");
            } else {
                p.setNome(novoNome);
            }
        }

        String novoEmail = JOptionPane.showInputDialog("Novo email:", p.getEmail());
        if (novoEmail != null) {
            if (novoEmail.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo Email não pode ser vazio. O valor anterior foi mantido.");
            } else {
                p.setEmail(novoEmail);
            }
        }
        
        if(p instanceof Leiloeiro) {
            String novoRegistro = JOptionPane.showInputDialog("Novo registro:", ((Leiloeiro) p).getNumeroRegistro());
            if (novoRegistro != null) {
                if (novoRegistro.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O campo Registro não pode ser vazio. O valor anterior foi mantido.");
                } else {
                    ((Leiloeiro) p).setNumeroRegistro(novoRegistro);
                }
            }
        } else if (p instanceof Licitante) {
            String novoEndereco = JOptionPane.showInputDialog("Novo endereço:", ((Licitante) p).getEndereco());
            if (novoEndereco != null) {
                if (novoEndereco.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O campo Endereço não pode ser vazio. O valor anterior foi mantido.");
                } else {
                    ((Licitante) p).setEndereco(novoEndereco);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Pessoa atualizada!");
    }

    private static void excluirPessoa() {
        Pessoa p = selecionarPessoa("excluir");
        if (p != null) {
            pessoas.remove(p);
            JOptionPane.showMessageDialog(null, "Pessoa removida!");
        }
    }

    private static void gerenciarCatalogo() {
        if (!existeLeiloeiro()) {
            JOptionPane.showMessageDialog(null, "É necessário cadastrar um Leiloeiro antes de gerenciar o catálogo.", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] options = {"Adicionar Item/Lote", "Listar Itens do Catálogo", "Editar Item/Lote", "Excluir Item/Lote", "Voltar"};
        int escolha = 0;
        while (escolha != 4 && escolha != -1) {
            escolha = JOptionPane.showOptionDialog(null, "Menu do Catálogo", "Gerenciar Catálogo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if(escolha == 0) {
                adicionarItemCatalogo();
            } else if (escolha == 1) {
                JOptionPane.showMessageDialog(null, catalogo.listarItens());
            } else if (escolha == 2) {
                editarItemCatalogo();
            } else if (escolha == 3) {
                excluirItemCatalogo();
            }
        }
    }

    private static void adicionarItemCatalogo() {
        String[] tipos = {"Item Individual", "Lote de Itens"};
        int tipo = JOptionPane.showOptionDialog(null, "O que deseja adicionar ao catálogo?", "Adicionar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipo == -1) return;

        if (tipo == 0) { // Item
            cadastrarItemIndividual();
        } else { // Lote
            cadastrarLote();
        }
    }

    private static void cadastrarItemIndividual() {
        String nome = JOptionPane.showInputDialog("Nome do item:");
        if(nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome é obrigatório. Operação cancelada.");
            return;
        }
        String desc = JOptionPane.showInputDialog("Descrição do item:");
        if(desc == null || desc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descrição é obrigatória. Operação cancelada.");
            return;
        }
        String valorStr = JOptionPane.showInputDialog("Valor inicial do item:");
        if(valorStr == null || valorStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Valor é obrigatório. Operação cancelada.");
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);
            catalogo.adicionarItem(new ItemLeilao(nome, desc, valor));
            JOptionPane.showMessageDialog(null, "Item adicionado ao catálogo com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido. Apenas números são permitidos. Operação cancelada.");
        }
    }

    private static void cadastrarLote() {
        String nome = JOptionPane.showInputDialog("Nome do lote:");
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome do lote é obrigatório. Operação cancelada.");
            return;
        }
        String desc = JOptionPane.showInputDialog("Descrição do lote:");
        if (desc == null || desc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descrição do lote é obrigatória. Operação cancelada.");
            return;
        }
        Lote lote = new Lote(nome, desc);

        JOptionPane.showMessageDialog(null, "Um lote precisa de no mínimo 2 itens.\nVamos cadastrar o primeiro item.");
        ItemLeilao primeiroItem = criarItemParaLote(1);

        if (primeiroItem == null) {
            JOptionPane.showMessageDialog(null, "Criação do item cancelada. O lote não será criado.");
            return;
        }
        lote.adicionarItem(primeiroItem);

        JOptionPane.showMessageDialog(null, "Agora, cadastre o segundo item.");
        ItemLeilao segundoItem = criarItemParaLote(2);

        if (segundoItem == null) {
            JOptionPane.showMessageDialog(null, "Criação do segundo item cancelada. O lote não será criado.");
            return;
        }
        lote.adicionarItem(segundoItem);

        while (true) {
            String[] options = {"Sim", "Não"};
            int resp = JOptionPane.showOptionDialog(
                    null,
                    "Lote válido criado!\nDeseja adicionar mais um item (opcional)?",
                    "Adicionar mais itens",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (resp != 0) {
                break;
            }

            ItemLeilao itemAdicional = criarItemParaLote(lote.getItensNoLote().size() + 1);
            if (itemAdicional != null) {
                lote.adicionarItem(itemAdicional);
            } else {
                JOptionPane.showMessageDialog(null, "Cadastro de item adicional cancelado.");
            }
        }

        catalogo.adicionarItem(lote);
        JOptionPane.showMessageDialog(null, "Lote criado e adicionado ao catálogo com sucesso!");
    }

    private static ItemLeilao criarItemParaLote(int numeroItem) {
        String nome = JOptionPane.showInputDialog("Nome do " + numeroItem + "º item:");
        if(nome == null || nome.trim().isEmpty()) return null;

        String desc = JOptionPane.showInputDialog("Descrição do " + numeroItem + "º item:");
        if(desc == null || desc.trim().isEmpty()) return null;

        String valorStr = JOptionPane.showInputDialog("Valor inicial do " + numeroItem + "º item:");
        if(valorStr == null || valorStr.trim().isEmpty()) return null;

        try {
            double valor = Double.parseDouble(valorStr);
            return new ItemLeilao(nome, desc, valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido para o item. Tente novamente.");
            return null;
        }
    }


    private static void editarItemCatalogo() {
        Leiloavel item = selecionarLeiloavel(catalogo.getItensDisponiveis(), "editar");
        if (item == null) return;

        if (item instanceof ItemLeilao) {
            ItemLeilao i = (ItemLeilao) item;
            i.setNome(JOptionPane.showInputDialog("Novo nome:", i.getNome()));
            i.setDescricao(JOptionPane.showInputDialog("Nova descrição:", i.getDescricao()));
            try {
                String valorStr = JOptionPane.showInputDialog("Novo valor:", i.obterValorMinimo());
                if (valorStr != null) {
                    i.setValorInicial(Double.parseDouble(valorStr));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Formato de valor inválido. O valor não foi alterado.");
            }
            JOptionPane.showMessageDialog(null, "Item do catálogo atualizado!");

        } else if (item instanceof Lote) {
            gerenciarItensDoLote((Lote) item);
        }
    }

    private static void gerenciarItensDoLote(Lote lote) {
        String[] options = {"Listar Itens do Lote", "Editar Nome do Lote", "Adicionar Item ao Lote", "Remover Item do Lote", "Editar Item do Lote", "Voltar"};
        int escolha = -1;

        while (escolha != 5) {
            escolha = JOptionPane.showOptionDialog(null,
                    "Editando o Lote: " + lote.getNomeLote() + "\n" + lote.obterDescricao(),
                    "Gerenciar Lote",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (escolha == 0) {
                StringBuilder sb = new StringBuilder("=== Itens no Lote: " + lote.getNomeLote() + " ===\n\n");
                ArrayList<ItemLeilao> itens = lote.getItensNoLote();

                for (int i = 0; i < itens.size(); i++) {
                    ItemLeilao item = itens.get(i);
                    sb.append("Item ").append(i + 1).append(": ").append(item.getNome()).append("\n");
                    sb.append("  - Descrição: ").append(item.getDescricao()).append("\n");
                    sb.append("  - Valor Mínimo: R$").append(String.format("%.2f", item.obterValorMinimo())).append("\n\n");
                }
                JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea(sb.toString())));

            } else if (escolha == 1) {
                String novoNome = JOptionPane.showInputDialog("Novo nome do lote:", lote.getNomeLote());
                if (novoNome != null && !novoNome.trim().isEmpty()) {
                    lote.setNomeLote(novoNome);
                    JOptionPane.showMessageDialog(null, "Nome do lote atualizado!");
                }
            } else if (escolha == 2) {
                ItemLeilao novoItem = criarItemParaLote(lote.getItensNoLote().size() + 1);
                if (novoItem != null) {
                    lote.adicionarItem(novoItem);
                    JOptionPane.showMessageDialog(null, "Item adicionado ao lote com sucesso!");
                }
            } else if (escolha == 3) {
                if (lote.getItensNoLote().size() <= 2) {
                    JOptionPane.showMessageDialog(null, "Não é possível remover itens. O lote deve conter no mínimo 2 itens.", "Ação Inválida", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                ArrayList<Leiloavel> itensLeiloaveis = new ArrayList<>(lote.getItensNoLote());
                Leiloavel itemARemover = selecionarLeiloavel(itensLeiloaveis, "remover do lote");
                if (itemARemover != null) {
                    lote.getItensNoLote().remove((ItemLeilao) itemARemover);
                    JOptionPane.showMessageDialog(null, "Item removido do lote!");
                }
            } else if (escolha == 4) {
                ArrayList<Leiloavel> itensLeiloaveis = new ArrayList<>(lote.getItensNoLote());
                ItemLeilao itemAEditar = (ItemLeilao) selecionarLeiloavel(itensLeiloaveis, "editar no lote");
                if (itemAEditar != null) {
                    itemAEditar.setNome(JOptionPane.showInputDialog("Novo nome para o item:", itemAEditar.getNome()));
                    itemAEditar.setDescricao(JOptionPane.showInputDialog("Nova descrição para o item:", itemAEditar.getDescricao()));
                    try {
                        String valorStr = JOptionPane.showInputDialog("Novo valor para o item:", itemAEditar.obterValorMinimo());
                        if (valorStr != null) {
                            itemAEditar.setValorInicial(Double.parseDouble(valorStr));
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Formato de valor inválido. O valor do item não foi alterado.");
                    }
                    JOptionPane.showMessageDialog(null, "Item do lote atualizado!");
                }
            } else {
                break;
            }
        }
    }


    private static void excluirItemCatalogo() {
        Leiloavel item = selecionarLeiloavel(catalogo.getItensDisponiveis(), "excluir do catálogo");
        if (item != null) {
            catalogo.removerItem(item);
            JOptionPane.showMessageDialog(null, "Item removido do catálogo!");
        }
    }

    private static void gerenciarLeiloes() {

        String[] options = {"Criar Leilão", "Listar Leilões", "Gerenciar Leilão Específico", "Excluir Leilão", "Voltar"};
        int escolha = 0;

        while (escolha != 4 && escolha != -1) {
            escolha = JOptionPane.showOptionDialog(null, "Menu de Leilões", "Gerenciar Leilões",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if(escolha == 0) {
                criarLeilao();
            } else if (escolha == 1) {
                listarLeiloes();
            } else if (escolha == 2) {
                gerenciarLeilaoEspecifico();
            } else if (escolha == 3) {
                excluirLeilao();
            }
        }
    }

    private static void criarLeilao() {
        Leiloeiro resp = selecionarLeiloeiro();
        if (resp == null) return;
        String titulo = JOptionPane.showInputDialog("Título do leilão:");
        Leilao novoLeilao = new Leilao(titulo, resp);
        leiloes.add(novoLeilao);
        resp.getLeiloesCriados().add(novoLeilao);
        JOptionPane.showMessageDialog(null, "Leilão criado!");
    }

    private static void listarLeiloes() {
        StringBuilder sb = new StringBuilder("=== Lista de Leilões ===\n\n");
        if (leiloes.isEmpty()) {
            sb.append("Nenhum leilão cadastrado.");
        } else {
            for(Leilao l : leiloes) {
                sb.append(l.toString()).append("\n");
                if (!l.getArremates().isEmpty()) {
                    sb.append("  Arremates realizados:\n");
                    for(Arremate a : l.getArremates()) {
                        sb.append("    - ").append(a.toString().replace("\n", "\n      ")).append("\n");
                    }
                }
                sb.append("--------------------\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void excluirLeilao() {
        Leilao leilao = selecionarLeilao();
        if (leilao == null) {
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir o leilão '" + leilao.getTitulo() + "'?\n" +
                        "AVISO: Os itens deste leilão retornarão ao catálogo.",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacao == JOptionPane.YES_OPTION) {

            for (Leiloavel item : leilao.getItensDoLeilao()) {
                catalogo.adicionarItem(item);
            }

            Leiloeiro responsavel = leilao.getResponsavel();
            if (responsavel != null) {
                responsavel.getLeiloesCriados().remove(leilao);
            }

            leiloes.remove(leilao);

            JOptionPane.showMessageDialog(null, "Leilão excluído com sucesso!");
        }
    }

    private static void gerenciarLeilaoEspecifico() {
        Leilao leilao = selecionarLeilao();
        if (leilao == null) return;

        String[] options = {"Adicionar Item do Catálogo", "Inscrever Licitante", "Listar Licitantes", "Iniciar Leilão", "Ver Itens do Leilão", "Voltar"};
        int escolha = 0;

        while (escolha != 5 && escolha != -1) {
            escolha = JOptionPane.showOptionDialog(null, "Gerenciando: " + leilao.getTitulo() + " | Status: " + leilao.getStatus(), "Gerenciar Leilão",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if(escolha == 0) {
                adicionarItemAoLeilao(leilao);
            } else if (escolha == 1) {
                inscreverLicitante(leilao);
            } else if (escolha == 2) {
                StringBuilder sb = new StringBuilder("=== Licitantes Inscritos no Leilão: " + leilao.getTitulo() + " ===\n\n");
                ArrayList<Licitante> licitantes = leilao.getLicitantesInscritos();

                if (licitantes.isEmpty()) {
                    sb.append("Nenhum licitante inscrito neste leilão.");
                } else {
                    for (Licitante licitante : licitantes) {

                        sb.append(licitante.apresentarDados()).append("\n--------------------\n");
                    }
                }
                JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea(sb.toString())));

            } else if (escolha == 3) {
                iniciarLeilao(leilao);
            } else if (escolha == 4) {
                StringBuilder sb = new StringBuilder("Itens em " + leilao.getTitulo() + ":\n");
                for(Leiloavel item : leilao.getItensDoLeilao()) sb.append("- ").append(item.obterDescricao()).append("\n");
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        }
    }

    private static void adicionarItemAoLeilao(Leilao leilao) {
        Leiloavel item = selecionarLeiloavel(catalogo.getItensDisponiveis(), "adicionar ao leilão");
        if (item != null) {
            leilao.adicionarItem(item);
            catalogo.removerItem(item);
            JOptionPane.showMessageDialog(null, "Item adicionado ao leilão!");
        }
    }

    private static void inscreverLicitante(Leilao leilao) {
        Licitante licitante = selecionarLicitante();
        if(licitante != null) {
            leilao.inscreverLicitante(licitante);
            JOptionPane.showMessageDialog(null, "Licitante inscrito!");
        }
    }

    private static void iniciarLeilao(Leilao leilao) {
        if (leilao.getItensDoLeilao().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adicione itens ao leilão antes de iniciar.");
            return;
        }
        leilao.setStatus("Em Andamento");
        passesConsecutivos.clear();

        for (Leiloavel item : leilao.getItensDoLeilao()) {
            boolean jaArrematado = false;
            for (Arremate a : leilao.getArremates()) {
                if (a.getItemArrematado() == item) {
                    jaArrematado = true;
                    break;
                }
            }
            if(jaArrematado) {
                continue;
            }

            realizarRodadaDeLances(leilao, item);
        }
        leilao.setStatus("Finalizado");
        JOptionPane.showMessageDialog(null, "Todos os itens foram processados. Leilão finalizado!");
    }

    private static void realizarRodadaDeLances(Leilao leilao, Leiloavel item) {
        String[] options = {"Sim", "Não"};
        int resp = JOptionPane.showOptionDialog(
                null,
                "Iniciar lances para:\n" + item.obterDescricao() + "?",
                "Confirmar Início de Lances",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (resp != 0) {
            return;
        }

        if (leilao.getLicitantesInscritos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há licitantes inscritos para iniciar os lances neste item.");
            return;
        }

        ArrayList<Licitante> licitantesAtivos = new ArrayList<>(leilao.getLicitantesInscritos());

        while (true) {
            if (licitantesAtivos.size() <= 1) {
                break;
            }

            boolean alguemDeuLanceNaRodada = false;
            Iterator<Licitante> iterator = licitantesAtivos.iterator();

            while (iterator.hasNext()) {
                Licitante licitanteDaVez = iterator.next();

                double maiorValor = item.obterValorMinimo();
                for (Lance lance : item.getLancesRecebidos()) {
                    if (lance.getValor() > maiorValor) {
                        maiorValor = lance.getValor();
                    }
                }

                double lanceFixo = maiorValor * 1.05;

                String[] optionsLance = {
                        String.format("Lance Fixo (R$ %.2f)", lanceFixo),
                        "Lance Personalizado",
                        "Passar a Vez"
                };

                int escolha = JOptionPane.showOptionDialog(null,
                        "Vez de " + licitanteDaVez.getNome() + ".\nValor atual: R$" + String.format("%.2f", maiorValor),
                        "Leilão em Andamento",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsLance, optionsLance[0]);

                if (escolha == 0) {
                    Lance novoLance = new Lance(lanceFixo, licitanteDaVez, item);
                    item.adicionarLance(novoLance);
                    licitanteDaVez.adicionarLance(novoLance);
                    passesConsecutivos.put(licitanteDaVez, 0);
                    alguemDeuLanceNaRodada = true;
                    JOptionPane.showMessageDialog(null, "Lance de R$" + String.format("%.2f", lanceFixo) + " aceito!");

                } else if (escolha == 1) {
                    String lanceStr = JOptionPane.showInputDialog("Digite o valor do seu lance (maior que R$ " + String.format("%.2f", maiorValor) + "):");
                    if (lanceStr != null && !lanceStr.trim().isEmpty()) {
                        try {
                            double valorLance = Double.parseDouble(lanceStr);
                            if (valorLance > maiorValor) {
                                Lance novoLance = new Lance(valorLance, licitanteDaVez, item);
                                item.adicionarLance(novoLance);
                                licitanteDaVez.adicionarLance(novoLance);
                                passesConsecutivos.put(licitanteDaVez, 0);
                                alguemDeuLanceNaRodada = true;
                                JOptionPane.showMessageDialog(null, "Lance de R$" + String.format("%.2f", valorLance) + " aceito!");
                            } else {
                                JOptionPane.showMessageDialog(null, "O lance deve ser maior que o valor atual (R$ " + String.format("%.2f", maiorValor) + ")!");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Valor inválido.");
                        }
                    }
                } else {
                    int passes = passesConsecutivos.getOrDefault(licitanteDaVez, 0) + 1;
                    passesConsecutivos.put(licitanteDaVez, passes);
                    if (passes >= 2) {
                        iterator.remove();
                        JOptionPane.showMessageDialog(null, licitanteDaVez.getNome() + " passou 2 vezes e foi removido da rodada deste item.");
                    } else {
                        JOptionPane.showMessageDialog(null, licitanteDaVez.getNome() + " passou a vez.");
                    }
                }

                if (licitantesAtivos.size() <= 1) {
                    break;
                }
            }

            if (!alguemDeuLanceNaRodada) {
                break;
            }
        }


        Lance maiorLance = null;
        if (!item.getLancesRecebidos().isEmpty()) {
            maiorLance = item.getLancesRecebidos().get(0);
            for (int i = 1; i < item.getLancesRecebidos().size(); i++) {
                if (item.getLancesRecebidos().get(i).getValor() > maiorLance.getValor()) {
                    maiorLance = item.getLancesRecebidos().get(i);
                }
            }
        }

        if (maiorLance != null) {
            Arremate arremate = new Arremate(maiorLance.getValor(), maiorLance.getLicitante(), item);
            leilao.adicionarArremate(arremate);
            String msg = "Item arrematado!\n" + arremate;
            JOptionPane.showMessageDialog(null, msg);
            arremate.getVencedor().enviarNotificacao("Parabéns! Você arrematou: " + item.obterDescricao());
            leilao.getResponsavel().enviarNotificacao("Item vendido: " + item.obterDescricao());
        } else {
            JOptionPane.showMessageDialog(null, "O item não recebeu lances e não foi arrematado.");
        }
    }

    private static boolean existeLeiloeiro() {
        for (Pessoa p : pessoas) {
            if (p instanceof Leiloeiro) {
                return true;
            }
        }
        return false;
    }

    private static Pessoa selecionarPessoa(String acao) {
        if(pessoas.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhuma pessoa cadastrada."); return null; }

        String[] nomes = new String[pessoas.size()];
        for(int i = 0; i < pessoas.size(); i++) {
            nomes[i] = pessoas.get(i).getNome();
        }

        String sel = (String) JOptionPane.showInputDialog(null, "Selecione a pessoa para " + acao, "Seleção",
                JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
        if (sel == null) return null;

        for (Pessoa p : pessoas) {
            if (p.getNome().equals(sel)) {
                return p;
            }
        }
        return null;
    }

    private static Leiloeiro selecionarLeiloeiro() {
        ArrayList<Leiloeiro> leiloeiros = new ArrayList<>();
        for(Pessoa p : pessoas) {
            if (p instanceof Leiloeiro) {
                leiloeiros.add((Leiloeiro) p);
            }
        }
        if(leiloeiros.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum leiloeiro cadastrado."); return null; }

        String[] nomes = new String[leiloeiros.size()];
        for(int i = 0; i < leiloeiros.size(); i++) {
            nomes[i] = leiloeiros.get(i).getNome();
        }

        String sel = (String) JOptionPane.showInputDialog(null, "Selecione o leiloeiro", "Seleção",
                JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
        if (sel == null) return null;

        for (Leiloeiro leiloeiro : leiloeiros) {
            if(leiloeiro.getNome().equals(sel)) {
                return leiloeiro;
            }
        }
        return null;
    }

    private static Licitante selecionarLicitante() {
        ArrayList<Licitante> licitantes = new ArrayList<>();
        for(Pessoa p : pessoas) {
            if (p instanceof Licitante) {
                licitantes.add((Licitante) p);
            }
        }
        if(licitantes.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum licitante cadastrado."); return null; }
        return selecionarLicitante(licitantes);
    }

    private static Licitante selecionarLicitante(ArrayList<Licitante> lista) {
        if(lista.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum licitante na lista."); return null; }

        String[] nomes = new String[lista.size()];
        for (int i=0; i < lista.size(); i++) {
            nomes[i] = lista.get(i).getNome();
        }

        String sel = (String) JOptionPane.showInputDialog(null, "Selecione o licitante", "Seleção",
                JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
        if (sel == null) return null;

        for(Licitante l : lista) {
            if(l.getNome().equals(sel)) {
                return l;
            }
        }
        return null;
    }

    private static Leiloavel selecionarLeiloavel(ArrayList<Leiloavel> lista, String acao) {
        if(lista.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum item leiloável na lista."); return null; }

        String[] descricoes = new String[lista.size()];
        for (int i=0; i < lista.size(); i++) {
            descricoes[i] = lista.get(i).obterDescricao();
        }

        String sel = (String) JOptionPane.showInputDialog(null, "Selecione o item para " + acao, "Seleção",
                JOptionPane.QUESTION_MESSAGE, null, descricoes, descricoes[0]);
        if (sel == null) return null;

        for (Leiloavel item : lista) {
            if(item.obterDescricao().equals(sel)) {
                return item;
            }
        }
        return null;
    }

    private static Leilao selecionarLeilao() {
        if(leiloes.isEmpty()) { JOptionPane.showMessageDialog(null, "Nenhum leilão cadastrado."); return null; }

        String[] titulos = new String[leiloes.size()];
        for(int i=0; i < leiloes.size(); i++) {
            titulos[i] = leiloes.get(i).getTitulo();
        }

        String sel = (String) JOptionPane.showInputDialog(null, "Selecione o leilão", "Seleção",
                JOptionPane.QUESTION_MESSAGE, null, titulos, titulos[0]);
        if (sel == null) return null;

        for (Leilao leilao : leiloes) {
            if(leilao.getTitulo().equals(sel)) {
                return leilao;
            }
        }
        return null;
    }
}