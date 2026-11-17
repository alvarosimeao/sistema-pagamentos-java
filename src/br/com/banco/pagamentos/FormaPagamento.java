package br.com.banco.pagamentos;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Classe-base das formas de pagamento.
 * Por ser abstrata, não pode ser instanciada diretamente.
 */
public abstract class FormaPagamento {
    private final BigDecimal valor;
    private StatusPagamento status;

    protected FormaPagamento(BigDecimal valor) {
        this.valor = Objects.requireNonNull(valor, "O valor não pode ser nulo.");
        this.status = StatusPagamento.PENDENTE;
    }

    /** Processa o pagamento conforme as regras específicas de cada tipo. */
    public abstract void pagar();

    /** Exibe no console os detalhes específicos da forma de pagamento. */
    public abstract void exibirDetalhes();

    /** Impede repetição e valida o valor antes do processamento. */
    protected final boolean validarParaProcessamento() {
        if (status != StatusPagamento.PENDENTE) {
            return false;
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            recusar();
            System.out.println("Pagamento recusado: o valor deve ser maior que zero.");
            return false;
        }
        return true;
    }

    protected final void aprovar() {
        status = StatusPagamento.APROVADO;
    }

    protected final void recusar() {
        status = StatusPagamento.RECUSADO;
    }

    public final BigDecimal getValor() {
        return valor;
    }

    public final StatusPagamento getStatus() {
        return status;
    }

    protected final String valorFormatado() {
        return formatarMoeda(valor);
    }

    protected static final String formatarMoeda(BigDecimal quantia) {
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        return moeda.format(quantia);
    }
}
