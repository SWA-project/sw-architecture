package swa.credit.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import swa.credit.dto.CreditVerdictEvent;
import swa.credit.dto.TransactionEvent;
import swa.credit.model.Transaction;
import swa.credit.repository.TransactionRepository;

import static swa.credit.enums.TransactionStatus.FAILED;
import static swa.credit.enums.TransactionStatus.SUCCESSFUL;
import static swa.credit.enums.VerdictStatus.APPROVED;

import javax.transaction.Transactional;

@Component
public class VerdictEventHandler implements EventHandler<CreditVerdictEvent, TransactionEvent> {

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
    public TransactionEvent handleEvent(CreditVerdictEvent event) {
        Mono.fromRunnable(() -> transactionRepository.save(
                new Transaction()
                        .setOrderId(event.getOrderId())))
                .subscribeOn(jdbcScheduler)
                .subscribe();

        return new TransactionEvent()
                .orderId(event.getOrderId())
                .status(APPROVED.equals(event.getStatus())
                        ? SUCCESSFUL
                        : FAILED);

    }
}
