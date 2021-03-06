package io.pivotal.examples.b2b.payments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PaymentProcessingScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessingScheduler.class);

    private static final long NR_OF_TRANSACTIONS = 10;
    private static final long TEN_SECONDS = 10 * 1000;

    private PaymentProcessor paymentProcessor;

    public PaymentProcessingScheduler(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @Scheduled(initialDelay = TEN_SECONDS, fixedRate = TEN_SECONDS)
    public void processPayments() throws IOException, InterruptedException {
        LOGGER.info("Processing Payments...");
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < NR_OF_TRANSACTIONS; i++) {
            String paymentId = "" + UUID.randomUUID();
            String originAccount = "" + random.nextInt(1000);
            String destinationAccount = "" + random.nextInt(1000);
            String amount = "€" + BigDecimal.valueOf(random.nextDouble(10)).setScale(2, RoundingMode.HALF_UP);

            Payment payment = new Payment(null, paymentId, originAccount, destinationAccount, amount, PaymentStatus.NOT_CONFIRMED);

            this.paymentProcessor.process(payment);
        }

        LOGGER.info("Done!");
    }
}
