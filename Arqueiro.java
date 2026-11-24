public class Arqueiro extends Jogador {

    public Arqueiro(String nome) throws JogoException {
        super(nome, 110, 50, 35, 5);
    }

    public Arqueiro(
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
        return "Arqueiro";
    }

    @Override
    public String getNomeRecurso() {
        return "Energia";
    }

    @Override
    public String getNomeHabilidadeEspecial() {
        return "Tiro do arqueiro Y";
    }

    @Override
    public int getCustoHabilidadeEspecial() {
        return 20;
    }

    @Override
    public String executarHabilidadeEspecial(Personagem alvo) throws JogoException {
        if (alvo == null) {
            throw new JogoException("O alvo do ataque não pode ser null.");
        }
        if (isDerrotado()) {
            throw new JogoException("Personagem morto não tem para que usar habilidade.");
        }
        consumirRecurso(getCustoHabilidadeEspecial());
        int defesaReduzida = alvo.getDefesaBase() / 2;
        int danoBruto = Math.max(1, (getAtaqueBase() + 6) - defesaReduzida);
        int danoCausado = alvo.receberDano(danoBruto);
        return getNome() + " usou " + getNomeHabilidadeEspecial() + " e causou " + danoCausado + " de dano no alvo " + alvo.getNome() + ".";
    }
}