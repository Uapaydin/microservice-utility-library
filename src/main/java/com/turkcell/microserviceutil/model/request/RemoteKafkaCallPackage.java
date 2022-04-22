package com.turkcell.microserviceutil.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 16:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RemoteKafkaCallPackage extends RemoteCallPackage {
    public Object kafkaMessage;
}
