package swa.creditservice.controller;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import swa.creditservice.commands.LoanGranted;
import swa.creditservice.commands.LoanNotGranted;
import swa.creditservice.commands.ReserveCreditCommand;
import swa.creditservice.service.CreditCalculationService;
import swa.creditservice.service.LoanNotGrantedException;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;

import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class CreditCommandHandler {
    private CreditCalculationService creditService;

    public CreditCommandHandler(CreditCalculationService creditservice) {
        this.creditService = creditservice;
    }

    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel("creditService")
                .onMessage(ReserveCreditCommand.class, this::reserveCredit)
                .build();
    }

    public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {
        ReserveCreditCommand cmd = cm.getCommand();
        try {
          creditService.reserveCredit(cmd.getCustomerId(), cmd.getCustomerMoney(), cmd.getCreditAmount());
          return withSuccess(new LoanGranted());
        } catch (LoanNotGrantedException e) {
          return withFailure(new LoanNotGranted());
        }
      }


  
}
