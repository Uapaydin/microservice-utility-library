package com.turkcell.microserviceutil.service;

import com.turkcell.microserviceutil.model.request.RemoteCallRequest;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 13:27
 */
@Component
@Data
@RequestScope
public class SagaTransactionHandler {

    public SagaTransactionHandler(){
        this.transactionHistory = new ArrayList<>();
    }
    List<RemoteCallRequest> transactionHistory;
}
