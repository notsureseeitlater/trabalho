public class BossFinal extends Inimigo {

    public BossFinal() throws JogoException {
        super("Boss Final", 135, 30, 15);
    }

    public BossFinal(
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
        return "Chefão Final (Encontro 3)";
    }

    @Override
    public String agirAutomatico(Personagem alvo, int turnoAtual) throws JogoException {
        if (alvo == null) {
            throw new JogoException("O alvo do ataque não pode ser null.");
        }
        if (isDerrotado()) {
            return getNome() + " foi derrotado.";
        }
        double percentualVida = (double) getVidaAtual() / (double) getVidaMax();
        if (percentualVida <= 0.40) {
            int danoPesado = Math.max(1, getAtaqueBase() + 8 - alvo.getDefesaBase());
            int danoCausado = alvo.receberDano(danoPesado);
            return getNome() + " atacou com carinho e causou " + danoCausado + " de dano em " + alvo.getNome();
        } else {
            if (turnoAtual % 3 == 0) {
                prepararDefesa();
                return getNome() + " se defendeu."; //desvio e te mato*
            } else {
                int danoBruto = calcularDanoAtaqueBasico(alvo);
                int danoCausado = alvo.receberDano(danoBruto);
                return getNome() + " atacou e causou " + danoCausado + " de dano em " + alvo.getNome();
            }
        }
    }
}