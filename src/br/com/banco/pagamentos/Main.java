package br.com.banco.pagamentos;

import java.math.BigDecimal;
import java.util.List;

/** Ponto de entrada e demonstração do sistema. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        List<FormaPagamento> pagamentos = List.of(
                new Pix(new BigDecimal("150.00"), "cliente@exemplo.com"),
                new Debito(new BigDecimal("80.00"), new BigDecimal("200.00")),
                new Credito(new BigDecimal("1200.00"), new BigDecimal("3000.00"), 6),
                new Pix(BigDecimal.ZERO, "teste@exemplo.com")
        );

        for (FormaPagamento pagamento : pagamentos) {
            pagamento.pagar();
            pagamento.exibirDetalhes();
            System.out.println("--------------------------------------------------");
        }
    }
}
