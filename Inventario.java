import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<String> pocoes;
    private int curaPorPocao;

    public Inventario() throws JogoException {
        this(2, 35);
    }

    public Inventario(int quantidadeInicial, int curaPorPocao) throws JogoException {
        if (quantidadeInicial < 2) {
            throw new JogoException("O inventario deve iniciar com 2 poções (mínimo).");
        }
        if (curaPorPocao <= 0) {
            throw new JogoException("Cura não pode ser negativa ou zero.");
        }
        this.curaPorPocao = curaPorPocao;
        this.pocoes = new ArrayList<>();
        for (int i = 0; i < quantidadeInicial; i++) {
            this.pocoes.add("Poção de Cura");
        }
    }

    public Inventario(List<String> pocoesSalvas, int curaPorPocao) throws JogoException {
        if (pocoesSalvas == null) {
            throw new JogoException("A lista de poções não pode ser null.");
        }
        if (curaPorPocao <= 0) {
            throw new JogoException("Cura não pode ser negativa ou zero.");
        }
        this.curaPorPocao = curaPorPocao;
        this.pocoes = new ArrayList<>(pocoesSalvas);
    }

    public int getQuantidadePocoes() {
        return pocoes.size();
    }

    public int getCuraPorPocao() {
        return curaPorPocao;
    }

    public void adicionarPocao() {
        pocoes.add("Poção de Cura");
    }

    public int usarPocao(Jogador jogador) throws JogoException {
        if (jogador == null) {
            throw new JogoException("O jogador não pode ser null.");
        }
        if (jogador.isDerrotado()) {
            throw new JogoException("Personagem morto não tem para que usar pocao.");
        }
        if (pocoes.isEmpty()) {
            throw new JogoException("Estoque vazio.");
        }
        if (jogador.getVidaAtual() >= jogador.getVidaMax()) {
            throw new JogoException("A vida do jogador está cheia.");
        }
        int curado = jogador.curar(curaPorPocao);
        pocoes.remove(pocoes.size() - 1);
        return curado;
    }
}