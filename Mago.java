public class Mago extends Jogador {

    public Mago(String nome) throws JogoException {
        super(nome, 95, 80, 40, 5);
    }

    public Mago(
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
        return "Mago";
    }

    @Override
    public String getNomeRecurso() {
        return "Mana";
    }

    @Override
    public String getNomeHabilidadeEspecial() {
        return "Magia do mago Z"; //Z de Zatanna (é fake, foi só pq n tinha ideia mesmo)
    }

    @Override
    public int getCustoHabilidadeEspecial() {
        return 25;
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
        int danoBruto = Math.max(1, (getAtaqueBase() * 2) + 4);
        int danoCausado = alvo.receberDano(danoBruto);
        return getNome() + " usou " + getNomeHabilidadeEspecial() + " e causou " + danoCausado + " de dano no op " + alvo.getNome() + ".";
    }
}