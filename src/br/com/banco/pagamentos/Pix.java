package br.com.banco.pagamentos;

import java.math.BigDecimal;
import java.util.Objects;

/** Pagamento instantâneo realizado por meio de uma chave Pix. */
public final class Pix extends FormaPagamento {
    private final String chaveDestino;

    public Pix(BigDecimal valor, String chaveDestino) {
        super(valor);
        this.chaveDestino = Objects.requireNonNull(chaveDestino, "A chave Pix não pode ser nula.");
    }

    @Override
    public void pagar() {
        if (!validarParaProcessamento()) {
            return;
        }

        aprovar();
        System.out.println("Pix enviado instantaneamente para " + chaveDestino + ".");
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[PIX] Valor: %s | Chave: %s | Status: %s%n",
                valorFormatado(), chaveDestino, getStatus());
    }
}

