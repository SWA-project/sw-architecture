package payment.saga.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import payment.saga.order.enums.OrderStatus;
import payment.saga.order.model.OrderRequest;
import payment.saga.order.publisher.CreditOrderPublisher;
import payment.saga.order.model.CreditOrder;
import payment.saga.order.repository.CreditOrderRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

@Service
public class OrderService {

    private final CreditOrderRepository creditOrderRepository;
    private final CreditOrderPublisher creditOrderPublisher;
    private final Scheduler jdbcScheduler;

    @Autowired
    public OrderService(
            CreditOrderRepository creditOrderRepository,
            CreditOrderPublisher creditOrderPublisher,
            Scheduler jdbcScheduler) {
        this.creditOrderRepository = creditOrderRepository;
        this.creditOrderPublisher = creditOrderPublisher;
        this.jdbcScheduler = jdbcScheduler;
    }

    public Mono<CreditOrder> createOrder(OrderRequest order) {
        CreditOrder creditOrder = getCreditOrder(order);
        CreditOrder savedCreditOrder = creditOrderRepository.save(creditOrder);
        creditOrderPublisher.process(creditOrder);
        return Mono.just(savedCreditOrder);
    }

    public Flux<CreditOrder> getAll() {
        return Flux.defer(
                () -> Flux.fromIterable(creditOrderRepository.findAll()))
                .subscribeOn(jdbcScheduler);
    }

    public Flux<List<CreditOrder>> reactiveGetAll() {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(2000));
        Flux<List<CreditOrder>> orderPurchaseFlux = Flux.fromStream(
                Stream.generate(creditOrderRepository::findAll));
        return Flux.zip(interval, orderPurchaseFlux)
                .map(Tuple2::getT2);
    }

    public Mono<CreditOrder> getOrderById(Integer id) {
        return Mono.fromCallable(
                () -> creditOrderRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Order id: " + id + " does not exist")))
                .subscribeOn(jdbcScheduler);
    }

    private CreditOrder getCreditOrder(final OrderRequest order) {
        return new CreditOrder()
                .setCustomerId(order.getCustomerId())
                .setCreditAmount(order.getCreditAmount())
                .setStatus(OrderStatus.PENDING);
    }

}
