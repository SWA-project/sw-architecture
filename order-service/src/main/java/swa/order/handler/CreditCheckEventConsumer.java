package swa.order.handler;

import static swa.order.enums.OrderStatus.APPROVED;
import static swa.order.enums.OrderStatus.DECLINED;
import static swa.order.enums.CreditCheckStatus.SUCCESSFUL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import swa.order.dto.CreditCheckEvent;
import swa.order.model.CreditOrder;
import swa.order.repository.CreditOrderRepository;

@Component
public class CreditCheckEventConsumer implements EventConsumer<CreditCheckEvent> {

    private final CreditOrderRepository creditOrderRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public CreditCheckEventConsumer(
            CreditOrderRepository creditOrderRepository,
            Scheduler jdbcScheduler) {
        this.creditOrderRepository = creditOrderRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    public void consumeEvent(CreditCheckEvent event) {
        Mono.fromRunnable(
                () -> creditOrderRepository.findById(event.getOrderId())
                        .ifPresent(order -> {
                            setStatus(event, order);
                            creditOrderRepository.save(order);
                        }))
                .subscribeOn(jdbcScheduler)
                .subscribe();
    }

    private void setStatus(CreditCheckEvent transactionEvent, CreditOrder order) {
        order.setStatus(SUCCESSFUL.equals(transactionEvent.getStatus())
                ? APPROVED
                : DECLINED);
    }

}
