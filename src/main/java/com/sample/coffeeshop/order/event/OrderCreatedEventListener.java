package com.sample.coffeeshop.order.event;

import com.sample.coffeeshop.common.KafkaMessagePublisher;
import com.sample.coffeeshop.common.KafkaTopic;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderCreatedEventListener {

    KafkaMessagePublisher kafkaMessagePublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishCreatedOrder(OrderCreatedEvent event) {
        kafkaMessagePublisher.publish(
                KafkaTopic.ORDER_CREATED,
                event.getOrder().getOrderId().toString(),
                event.getOrder()
        );
    }
}
