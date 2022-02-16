package swa.credit.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import swa.credit.dto.CreditVerdictEvent;
import swa.credit.dto.CreditVerdictDoneEvent;
import swa.credit.model.Transaction;
import swa.credit.repository.TransactionRepository;

import static swa.credit.enums.VerdictStatus.APPROVED;
import static swa.credit.enums.VerdictStatus.DECLINED;

import javax.transaction.Transactional;

@Component
public class VerdictEventHandler implements EventHandler<CreditVerdictEvent, CreditVerdictDoneEvent> {

    private final TransactionRepository transactionRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public VerdictEventHandler(
            TransactionRepository transactionRepository,
            Scheduler jdbcScheduler) {
        this.transactionRepository = transactionRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Transactional
    public CreditVerdictDoneEvent handleEvent(CreditVerdictEvent event) {
    	System.out.println("Returning credit verdict to order service for order " + event.getOrderId() + " and status " + event.getStatus());
        Mono.fromRunnable(() -> transactionRepository.save(
                new Transaction()
                        .setOrderId(event.getOrderId())))
                .subscribeOn(jdbcScheduler)
                .subscribe();

        return new CreditVerdictDoneEvent()
                .orderId(event.getOrderId())
                .status(APPROVED.equals(event.getStatus())
                        ? APPROVED
                        : DECLINED);

    }
}
