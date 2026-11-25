public class TrombadinhaFraco extends Inimigo {

    public TrombadinhaFraco() throws JogoException {
        super("Trombadinha", 55, 15, 3);
    }

    public TrombadinhaFraco(
            String nome,
            int vidaMax,
            int vidaAtual,
            int ataqueBase,
            int defesaBase,
            boolean defendendo
    ) throws JogoException {
        super(nome, vidaMax, vidaAtual, ataqueBase, defesaBase, defendendo);
    }

    @Override
    public String getDescricaoClasse() {
        return "Inimigo Fraco (Encontro 1)";
    }

    @Override
    public String agirAutomatico(Personagem alvo, int turnoAtual) throws JogoException {
        if (alvo == null) {
            throw new JogoException("O alvo do ataque não pode ser null.");
        }
        if (isDerrotado()) {
            return getNome() + " foi derrotado.";
        }
        int danoBruto = calcularDanoAtaqueBasico(alvo);
        int danoCausado = alvo.receberDano(danoBruto);
        return getNome() + " realizou ataque basico e causou " + danoCausado + " de dano em " + alvo.getNome();
    }
}