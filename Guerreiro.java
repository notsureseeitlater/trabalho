public class Guerreiro extends Jogador {

    public Guerreiro(String nome) throws JogoException {
        super(nome, 150, 50, 25, 10);
    }

    public Guerreiro(
            String nome,
            int vidaMax,
            int vidaAtual,
            int recursoMax,
            int recursoAtual,
            int ataqueBase,
            int defesaBase,
            boolean defendendo
    ) throws JogoException {
        super(nome, vidaMax, vidaAtual, recursoMax, recursoAtual, ataqueBase, defesaBase, defendendo);
    }

    @Override
    public String getDescricaoClasse() {
        return "Guerreiro";
    }

    @Override
    public String getNomeRecurso() {
        return "Energia";
    }

    @Override
    public String getNomeHabilidadeEspecial() {
        return "Golpe do guerreiro X";
    }

    @Override
    public int getCustoHabilidadeEspecial() {
        return 20; }

    @Override
    public String executarHabilidadeEspecial(Personagem alvo) throws JogoException {
        if (alvo == null) {
            throw new JogoException("O alvo do ataque não pode ser null.");
        }
        if (isDerrotado()) {
            throw new JogoException("Personagem morto não tem para que usar habilidade.");
        }
        consumirRecurso(getCustoHabilidadeEspecial());
        int danoBruto = Math.max(1, (getAtaqueBase() * 2) - alvo.getDefesaBase());
        int danoCausado = alvo.receberDano(danoBruto);
        return getNome() + " usou " + getNomeHabilidadeEspecial() + " e causou " + danoCausado + " de dano em " + alvo.getNome() + ".";
    }
}