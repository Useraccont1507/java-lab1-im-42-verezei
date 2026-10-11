# Лабораторна робота 1 - Data-Oriented Programming в Java

Верезей Ілля, ІМ-42

## Опис

Система управління замовленнями. Статуси замовлення змодельовані незмінними даними
(`sealed interface` + `record`), логіка винесена в окремий клас `OrderProcessor`,
який обробляє статус через `switch` з pattern matching і повертає Text Block.

Усі класи в пакеті `ua.kpi.comsys.dop`:

```
OrderStatus (sealed interface)
|--- Pending
|--- Paid(String paymentId, BigDecimal amount)
|--- Shipped(String trackingCode, LocalDate dispatchDate)
|--- Cancelled(String reason)

OrderProcessor.process(OrderStatus) -> String
```

Некоректні дані в конструкторах record та `process(null)` кидають `IllegalArgumentException`.

## Варіант

N = 9 (номер у списку групи)

V = N mod 3 = 9 mod 3 = 0 - "Система управління замовленнями"

## Запуск тестів

Потрібна JDK 21+. Maven встановлювати не обовʼязково, є wrapper:

```bash
./mvnw test
```

Windows: `mvnw.cmd test`

Звіт покриття JaCoCo: `target/site/jacoco/index.html`
