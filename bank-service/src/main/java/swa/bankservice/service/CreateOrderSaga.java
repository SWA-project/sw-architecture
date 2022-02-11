package swa.bankservice.service;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import swa.bankservice.dto.CreateOrderSagaData;
import swa.bankservice.dto.CustomerCreditLimitExceeded;
import swa.bankservice.dto.CustomerNotFound;
import swa.bankservice.dto.LoanNotGranted;
import swa.bankservice.dto.ValidateUserCommand;
import swa.bankservice.model.Order;
import swa.bankservice.model.RejectionReason;

public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

  private OrderService orderService;

  public CreateOrderSaga(OrderService orderService) {
    this.orderService = orderService;
  }

  private SagaDefinition<CreateOrderSagaData> sagaDefinition =
          step()
            .invokeLocal(this::create)
            .withCompensation(this::reject)
          .step()
            .invokeParticipant(this::reserveCredit)
            .onReply(LoanNotGranted.class, this::handleLoanNotGranted)
          .step()
            .invokeLocal(this::approve)
          .build();

  private void handleCustomerNotFound(CreateOrderSagaData data, CustomerNotFound reply) {
    data.setRejectionReason(RejectionReason.UNKNOWN_CUSTOMER);
  }

  private void handleLoanNotGranted(CreateOrderSagaData data, LoanNotGranted reply) {
    data.setRejectionReason(RejectionReason.INSUFFICIENT_CREDIT);
  }

  @Override
  public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
    return this.sagaDefinition;
  }

  public void create(CreateOrderSagaData data) {
	  Order order = orderService.createOrder(data.getOrderDetails());
	  data.setOrderId(order.getId());	
  }

  private CommandWithDestination reserveCredit(CreateOrderSagaData data) {
    Long orderId = data.getOrderId();
    Long customerId = data.getOrderDetails().getCustomerId();
    int creditAmount = data.getOrderDetails().getCreditAmount();
    int customerMoney = data.getOrderDetails().getCustomerMoney();
    return send(new ValidateUserCommand(customerId, orderId, creditAmount, customerMoney))
            .to("creditService")
            .build();
  }

  private void approve(CreateOrderSagaData data) {
    orderService.approveOrder(data.getOrderId());
  }

  private void reject(CreateOrderSagaData data) {
    orderService.rejectOrder(data.getOrderId(), data.getRejectionReason());
  }
}