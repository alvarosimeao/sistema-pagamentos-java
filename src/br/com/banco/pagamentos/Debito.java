package br.com.banco.pagamentos;

import java.math.BigDecimal;
import java.util.Objects;

/** Pagamento debitado diretamente do saldo da conta. */
public final class Debito extends FormaPagamento {
    private BigDecimal saldoDisponivel;

    public Debito(BigDecimal valor, BigDecimal saldoDisponivel) {
        super(valor);
        this.saldoDisponivel = Objects.requireNonNull(
                saldoDisponivel, "O saldo disponível não pode ser nulo.");
    }

    @Override
    public void pagar() {
        if (!validarParaProcessamento()) {
            return;
        }

        if (saldoDisponivel.compareTo(getValor()) < 0) {
            recusar();
            System.out.println("Pagamento no débito recusado: saldo insuficiente.");
            return;
        }

        saldoDisponivel = saldoDisponivel.subtract(getValor());
        aprovar();
        System.out.println("Pagamento no débito aprovado e descontado do saldo.");
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[DÉBITO] Valor: %s | Saldo restante: %s | Status: %s%n",
                valorFormatado(), formatarMoeda(saldoDisponivel), getStatus());
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

}
