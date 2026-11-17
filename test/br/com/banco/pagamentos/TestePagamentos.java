package br.com.banco.pagamentos;

import java.math.BigDecimal;

/** Testes simples executáveis com a opção -ea da JVM. */
public final class TestePagamentos {
    private TestePagamentos() {
    }

    public static void main(String[] args) {
        deveAprovarPixValido();
        deveRecusarValorZero();
        deveRecusarValorNegativo();
        deveDebitarQuandoHouverSaldo();
        deveRecusarDebitoSemSaldo();
        deveConsumirLimiteDoCredito();
        deveRecusarCreditoSemLimite();
        naoDeveDebitarDuasVezes();
        naoDeveConsumirLimiteDuasVezes();
        System.out.println("Todos os testes passaram.");
    }

    private static void deveAprovarPixValido() {
        Pix pix = new Pix(new BigDecimal("10.00"), "chave@pix.com");
        pix.pagar();
        assert pix.getStatus() == StatusPagamento.APROVADO;
    }

    private static void deveRecusarValorZero() {
        Pix pix = new Pix(BigDecimal.ZERO, "chave@pix.com");
        pix.pagar();
        assert pix.getStatus() == StatusPagamento.RECUSADO;
    }

    private static void deveRecusarValorNegativo() {
        Pix pix = new Pix(new BigDecimal("-0.01"), "chave@pix.com");
        pix.pagar();
        assert pix.getStatus() == StatusPagamento.RECUSADO;
    }

    private static void deveDebitarQuandoHouverSaldo() {
        Debito debito = new Debito(new BigDecimal("30.00"), new BigDecimal("100.00"));
        debito.pagar();
        assert debito.getStatus() == StatusPagamento.APROVADO;
        assert debito.getSaldoDisponivel().compareTo(new BigDecimal("70.00")) == 0;
    }

    private static void deveRecusarDebitoSemSaldo() {
        Debito debito = new Debito(new BigDecimal("101.00"), new BigDecimal("100.00"));
        debito.pagar();
        assert debito.getStatus() == StatusPagamento.RECUSADO;
        assert debito.getSaldoDisponivel().compareTo(new BigDecimal("100.00")) == 0;
    }

    private static void deveConsumirLimiteDoCredito() {
        Credito credito = new Credito(
                new BigDecimal("250.00"), new BigDecimal("1000.00"), 5);
        credito.pagar();
        assert credito.getStatus() == StatusPagamento.APROVADO;
        assert credito.getLimiteDisponivel().compareTo(new BigDecimal("750.00")) == 0;
    }

    private static void deveRecusarCreditoSemLimite() {
        Credito credito = new Credito(
                new BigDecimal("1001.00"), new BigDecimal("1000.00"), 1);
        credito.pagar();
        assert credito.getStatus() == StatusPagamento.RECUSADO;
        assert credito.getLimiteDisponivel().compareTo(new BigDecimal("1000.00")) == 0;
    }

    private static void naoDeveDebitarDuasVezes() {
        Debito debito = new Debito(new BigDecimal("30.00"), new BigDecimal("100.00"));
        debito.pagar();
        debito.pagar();
        assert debito.getSaldoDisponivel().compareTo(new BigDecimal("70.00")) == 0;
    }

    private static void naoDeveConsumirLimiteDuasVezes() {
        Credito credito = new Credito(new BigDecimal("250.00"), new BigDecimal("1000.00"), 5);
        credito.pagar();
        credito.pagar();
        assert credito.getLimiteDisponivel().compareTo(new BigDecimal("750.00")) == 0;
    }
}
