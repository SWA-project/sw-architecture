package swa.credit.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.util.function.Tuple2;
import swa.credit.model.Credit;
import swa.credit.repository.CreditRepository;

@Service
public class CreditService {

    private final CreditRepository creditRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public CreditService(CreditRepository transactionRepository,
                              Scheduler jdbcScheduler) {
        this.creditRepository = transactionRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    public Flux<Credit> getAll() {
        return Flux.defer(
                () -> Flux.fromIterable(creditRepository.findAll()))
                .subscribeOn(jdbcScheduler);
    }

    public Flux<List<Credit>> reactiveGetAll() {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(2000));
        Flux<List<Credit>> transactionFlux = Flux.fromStream(
                Stream.generate(creditRepository::findAll));
        return Flux.zip(interval, transactionFlux)
                .map(Tuple2::getT2);
    }

    public Mono<Credit> getOrderById(Integer id) {
        return Mono.fromCallable(
                () -> creditRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Credit id: " + id + " does not exist")))
                .subscribeOn(jdbcScheduler);
    }

}
