package br.com.banco.pagamentos;

import java.math.BigDecimal;
import java.util.Objects;

/** Pagamento que utiliza o limite de um cartão de crédito. */
public final class Credito extends FormaPagamento {
    private BigDecimal limiteDisponivel;
    private final int numeroParcelas;

    public Credito(BigDecimal valor, BigDecimal limiteDisponivel, int numeroParcelas) {
        super(valor);
        this.limiteDisponivel = Objects.requireNonNull(
                limiteDisponivel, "O limite disponível não pode ser nulo.");
        if (numeroParcelas <= 0) {
            throw new IllegalArgumentException("O número de parcelas deve ser maior que zero.");
        }
        this.numeroParcelas = numeroParcelas;
    }

    @Override
    public void pagar() {
        if (!validarParaProcessamento()) {
            return;
        }

        if (limiteDisponivel.compareTo(getValor()) < 0) {
            recusar();
            System.out.println("Pagamento no crédito recusado: limite insuficiente.");
            return;
        }

        limiteDisponivel = limiteDisponivel.subtract(getValor());
        aprovar();
        System.out.printf("Pagamento no crédito aprovado em %dx.%n", numeroParcelas);
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[CRÉDITO] Valor: %s | Parcelas: %dx | Limite restante: %s | Status: %s%n",
                valorFormatado(), numeroParcelas, formatarMoeda(limiteDisponivel), getStatus());
    }

    public BigDecimal getLimiteDisponivel() {
        return limiteDisponivel;
    }

}
