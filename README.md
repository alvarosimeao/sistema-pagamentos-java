# Simulador de pagamentos em Java

Projeto de estudo que simula o processamento de pagamentos por Pix, débito e crédito no console. O objetivo é mostrar como regras comuns e específicas podem ser organizadas com orientação a objetos. **Nenhuma transferência ou cobrança real é realizada.**

## O que implementei

- Uma classe abstrata para valor e status, com comportamentos específicos para cada forma de pagamento.
- Valores monetários com `BigDecimal`, evitando cálculos com ponto flutuante.
- Regras para recusar valores não positivos, saldo insuficiente e limite insuficiente.
- Proteção para que a mesma instância de pagamento não seja processada duas vezes.
- Testes executáveis para aprovações, recusas e processamento repetido.

O programa de demonstração cria pagamentos fictícios e mostra o status e o saldo ou limite restante no console. O projeto não possui persistência, integração bancária, autenticação nem processamento concorrente.

## Como executar

Requer **JDK 21 ou superior**. Na raiz do repositório, execute:

### PowerShell

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 --release 21 -d out (Get-ChildItem -Recurse src,test -Filter *.java).FullName
java -cp out br.com.banco.pagamentos.Main
```

### Linux ou macOS

```bash
mkdir -p out
javac -encoding UTF-8 --release 21 -d out $(find src test -name '*.java')
java -cp out br.com.banco.pagamentos.Main
```

## Como testar

Depois da compilação acima, execute os testes com asserções habilitadas:

```bash
java -ea -cp out br.com.banco.pagamentos.TestePagamentos
```

Os testes usam apenas o JDK. Uma execução bem-sucedida termina com `Todos os testes passaram.`

## Organização

```text
src/br/com/banco/pagamentos/   Classes e programa de demonstração
test/br/com/banco/pagamentos/  Testes executáveis
```
