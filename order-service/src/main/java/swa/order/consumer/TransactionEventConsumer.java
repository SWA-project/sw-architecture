package swa.order.consumer;

import static swa.order.enums.OrderStatus.APPROVED;
import static swa.order.enums.OrderStatus.DECLINED;
import static swa.order.enums.TransactionStatus.SUCCESSFUL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import swa.order.model.CreditOrder;
import swa.order.model.TransactionEvent;
import swa.order.repository.CreditOrderRepository;

@Component
public class TransactionEventConsumer implements EventConsumer<TransactionEvent> {

    private final CreditOrderRepository creditOrderRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public TransactionEventConsumer(
            CreditOrderRepository creditOrderRepository,
            Scheduler jdbcScheduler) {
        this.creditOrderRepository = creditOrderRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    public void consumeEvent(TransactionEvent event) {
        Mono.fromRunnable(
                () -> creditOrderRepository.findById(event.getOrderId())
                        .ifPresent(order -> {
                            setStatus(event, order);
                            creditOrderRepository.save(order);
                        }))
                .subscribeOn(jdbcScheduler)
                .subscribe();
    }

    private void setStatus(TransactionEvent transactionEvent, CreditOrder order) {
        order.setStatus(SUCCESSFUL.equals(transactionEvent.getStatus())
                ? APPROVED
                : DECLINED);
    }

}
