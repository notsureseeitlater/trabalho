public class OponenteComum extends Inimigo {

    public OponenteComum() throws JogoException {
        super("Oponente Comum", 90, 30, 5);
    }

    public OponenteComum(
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
        return "Inimigo Medio (Encontro 2)";
    }

    @Override
    public String agirAutomatico(Personagem alvo, int turnoAtual) throws JogoException {
        if (alvo == null) {
            throw new JogoException("O alvo do ataque não pode ser null.");
        }
        if (isDerrotado()) {
            return getNome() + " foi derrotado.";
        }
        if (turnoAtual % 2 == 0) {
            prepararDefesa();
            return getNome() + " espera o proximo golpe,";
        } else {
            int danoBruto = calcularDanoAtaqueBasico(alvo);
            int danoCausado = alvo.receberDano(danoBruto);
            return getNome() + " atacou e causou " + danoCausado + " de dano em " + alvo.getNome();
        }
    }
}