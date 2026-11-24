//add package
public abstract class Jogador extends Personagem {

    private int recursoMax;
    private int recursoAtual;

    public Jogador(
            String nome,
            int vidaMax,
            int recursoMax,
            int ataqueBase,
            int defesaBase) {
                super(nome, vidaMax, ataqueBase, defesaBase);
                if (recursoMax < 0) {
                    throw new IllegalArgumentException("Recurso maximo não pode ser negativo.");
                }
                this.recursoMax = recursoMax;
                this.recursoAtual = recursoMax;
            }

    public Jogador(
            String nome,
            int vidaMax,
            int vidaAtual,
            int recursoMax,
            int recursoAtual,
            int ataqueBase,
            int defesaBase,
            boolean defendendo) {
                super(nome, vidaMax, vidaAtual, ataqueBase, defesaBase, defendendo);
                if (recursoMax < 0) {
                    throw new IllegalArgumentException("Recurso maximo não pode ser negativo.");
                }
                if (recursoAtual < 0 || recursoAtual > recursoMax) {
                    throw new IllegalArgumentException("Recurso atual deve ser entre zero e o máximo.");
                }
                this.recursoMax = recursoMax;
                this.recursoAtual = recursoAtual;
            }

    public int getRecursoMax() {
        return recursoMax;
    }
    public int getRecursoAtual() {
        return recursoAtual;
    }
    void consumirRecurso(int custo) {
        if (custo < 0) {
            throw new IllegalArgumentException("Custo não pode ser negativo.");
        }
        if (this.recursoAtual < custo) {
            throw new IllegalStateException("Recurso insuficiente para ação.");
        }
        this.recursoAtual -= custo;
    }
    void restaurarRecurso(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Recurso não pode ser negativo.");
        }
        this.recursoAtual = Math.min(this.recursoMax, this.recursoAtual + quantidade);
    }

    public abstract String getNomeRecurso();
    public abstract String getNomeHabilidadeEspecial();
    public abstract int getCustoHabilidadeEspecial();
    public abstract String executarHabilidadeEspecial(Personagem alvo);
}