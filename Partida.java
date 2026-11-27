import java.util.ArrayList;
import java.util.List;

public class Partida {

    public static final int totalEncontros = 3;

    private Jogador jogador;
    private Inimigo inimigoAtual;
    private Inventario inventario;
    private int encontroAtual;
    private int turno;
    private int encontrosVencidos;
    private int pontuacao;
    private boolean encerrada;
    private boolean vitoria;
    private boolean aguardandoEncontro;
    private List<String> historico;

    public Partida(String nome, int classe) throws JogoException {
        validarNome(nome);
        String[] classes = {"Guerreiro", "Arqueiro", "Mago"};
        if (classe < 0 || classe >= classes.length) {
            throw new JogoException("Classe invalida. Escolha entre 0 (Guerreiro), 1 (Arqueiro) ou 2 (Mago).");
        }

        if (classe == 0) {
            this.jogador = new Guerreiro(nome);
        } else {
            if (classe == 1) {
                this.jogador = new Arqueiro(nome);
            } else {
                this.jogador = new Mago(nome);
            }
        }

        this.inventario = new Inventario(2, 35);
        this.encontroAtual = 1;
        this.turno = 0;
        this.encontrosVencidos = 0;
        this.pontuacao = 0;
        this.encerrada = false;
        this.vitoria = false;
        this.aguardandoEncontro = false;
        this.historico = new ArrayList<>();
        this.inimigoAtual = criarInimigo(this.encontroAtual);
        registrar("Partida iniciada para " + this.jogador.getNome() + " (" + this.jogador.getDescricaoClasse() + "). Encontro 1 iniciado contra " + this.inimigoAtual.getNome() + ".");
    }

    public static void validarNome(String nome) throws JogoException {
        if (nome == null || nome.isBlank()) {
            throw new JogoException("O nome do personagem é obrigatório.");
        }
    }

    private Inimigo criarInimigo(int numero) throws JogoException {
        if (numero == 1) {
            return new TrombadinhaFraco();
        } else {
            if (numero == 2) {
                return new OponenteComum();
            } else {
                if (numero == 3) {
                    return new BossFinal();
                } else {
                    throw new JogoException("Encontro invalido: " + numero);
                }
            }
        }
    }

    private void validarCombate() throws JogoException {
        if (encerrada) {
            throw new JogoException("A partida já foi encerrada.");
        }
        if (aguardandoEncontro) {
            throw new JogoException("Avance para o proximo encontro antes de realizar nova ação.");
        }
        if (jogador.isDerrotado()) {
            throw new JogoException("Personagem morto não tem para que agir.");
        }
        if (inimigoAtual == null || inimigoAtual.isDerrotado()) {
            throw new JogoException("O inimigo atual já foi derrotado.");
        }
    }

    public String atacar() throws JogoException {
        validarCombate();
        int danoBruto = jogador.calcularDanoAtaqueBasico(inimigoAtual);
        int danoCausado = inimigoAtual.receberDano(danoBruto);
        String resumo = jogador.getNome() + " atacou e causou " + danoCausado + " de dano em " + inimigoAtual.getNome() + ".";
        return fecharTurno(resumo);
    }

    public String defender() throws JogoException {
        validarCombate();
        jogador.prepararDefesa();
        String resumo = jogador.getNome() + " preparou defesa (reduz pela metade o proximo dano recebido).";
        return fecharTurno(resumo);
    }

    public String usarHabilidade() throws JogoException {
        validarCombate();
        String resumo = jogador.executarHabilidadeEspecial(inimigoAtual);
        return fecharTurno(resumo);
    }

    public String usarPocao() throws JogoException {
        validarCombate();
        int curado = inventario.usarPocao(jogador);
        String resumo = jogador.getNome() + " usou uma poção e recuperou " + curado + " de vida.";
        return fecharTurno(resumo);
    }

    public String pocaoEntreEncontros() throws JogoException {
        if (encerrada) {
            throw new JogoException("A partida já foi encerrada.");
        }
        if (!aguardandoEncontro) {
            throw new JogoException("O uso de poção fora do turno so é permitido entre encontros.");
        }
        int curado = inventario.usarPocao(jogador);
        String resumo = "< Entre encontros > " + jogador.getNome() + " usou uma poção e recuperou " + curado + " de vida.";
        registrar(resumo);
        return resumo;
    }

    public String proximoEncontro() throws JogoException {
        if (encerrada) {
            throw new JogoException("A partida foi encerrada.");
        }
        if (!aguardandoEncontro) {
            throw new JogoException("Ainda não é possível avancar para o proximo encontro.");
        }
        encontroAtual = encontroAtual + 1;
        aguardandoEncontro = false;
        inimigoAtual = criarInimigo(encontroAtual);
        jogador.restaurarRecurso(15);
        String resumo = "Encontro " + encontroAtual + " iniciado contra " + inimigoAtual.getNome() + " (15 de " + jogador.getNomeRecurso().toLowerCase() + " restaurado).";
        registrar(resumo);
        return resumo;
    }

    private String fecharTurno(String acaoJogador) throws JogoException {
        turno = turno + 1;
        String finalTurno = "Turno " + turno + ": " + acaoJogador;

        if (inimigoAtual.isDerrotado()) {
            encontrosVencidos = encontrosVencidos + 1;
            finalTurno = finalTurno + " " + inimigoAtual.getNome() + " perdeu. ";
            if (encontroAtual >= totalEncontros) {
                vitoria = true;
                encerrada = true;
                atualizarPontos();
                finalTurno = finalTurno + " A reliquia foi recuperada. \n Pontuacao final: " + pontuacao + ".";
            } else {
                aguardandoEncontro = true;
                atualizarPontos();
                finalTurno = finalTurno + " Avance para o encontro " + (encontroAtual + 1) + ".";
            }
            registrar(finalTurno);
            return finalTurno;
        }

        String acaoInimigo = inimigoAtual.agirAutomatico(jogador, turno);
        finalTurno = finalTurno + " " + acaoInimigo;

        if (jogador.isDerrotado()) {
            vitoria = false;
            encerrada = true;
            atualizarPontos();
            finalTurno = finalTurno + " " + jogador.getNome() + " perdeu. Fim de jogo. \n Pontuacao final: " + pontuacao + ".";
        } else {
            atualizarPontos();
        }

        registrar(finalTurno);
        return finalTurno;
    }

    public int atualizarPontos() {
        int bonus;
        if (vitoria) {
            bonus = 500;
        } else {
            bonus = 0;
        }
        int calculo = (encontrosVencidos * 300) + (jogador.getVidaAtual() * 5) - (turno * 10) + bonus;
        if (calculo < 0) {
            this.pontuacao = 0;
        } else {
            this.pontuacao = calculo;
        }
        return this.pontuacao;
    }
    
    private void registrar(String entrada) {
        historico.add(entrada);
    }
    public Jogador getJogador() {
        return jogador;
    }
    public Inimigo getInimigoAtual() {
        return inimigoAtual;
    }
    public Inventario getInventario() {
        return inventario;
    }
    public int getEncontroAtual() {
        return encontroAtual;
    }
    public int getTurno() {
        return turno;
    }
    public int getEncontrosVencidos() {
        return encontrosVencidos;
    }
    public int getPontuacao() {
        return pontuacao;
    }
    public boolean isEncerrada() {
        return encerrada;
    }
    public boolean isVitoria() {
        return vitoria;
    }
    public boolean isAguardandoEncontro() {
        return aguardandoEncontro;
    }
    public List<String> getHistorico() {
        return new ArrayList<>(historico);
    }
}