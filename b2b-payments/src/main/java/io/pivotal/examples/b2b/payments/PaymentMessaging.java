package io.pivotal.examples.b2b.payments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class PaymentMessaging {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMessaging.class);

    private final PaymentChannels paymentChannels;

    public PaymentMessaging(PaymentChannels paymentChannels) {
        this.paymentChannels = paymentChannels;
    }

    @Async
    public void sendPaymentMessage(@NotNull final Payment payment) {
        LOGGER.info("Sending Payment Message for [{}]", payment.getPaymentId());
        this.paymentChannels.payments().send(new GenericMessage<>(payment));
    }
}
