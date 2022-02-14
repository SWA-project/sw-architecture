package payment.saga.order.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.order.model.CreditOrder;
import payment.saga.order.model.TransactionEvent;
import payment.saga.order.repository.CreditOrderRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import static payment.saga.order.enums.OrderStatus.APPROVED;
import static payment.saga.order.enums.OrderStatus.DECLINED;
import static payment.saga.order.enums.TransactionStatus.SUCCESSFUL;

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
