package product.pipeline.single;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import product.pipeline.common.ProductEvent;

import java.io.IOException;

public class SingleEventLambda {
    private final ObjectMapper objectMapper =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void handler(SNSEvent event) {
        event.getRecords().forEach(this::processSNSRecord);
    }

    private void processSNSRecord(SNSEvent.SNSRecord snsRecord) {
        try {
            final ProductEvent productEvent = objectMapper.readValue(
                    snsRecord.getSNS().getMessage(),
                    ProductEvent.class);
            System.out.println("Received product event:");
            System.out.println(productEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
