package product.pipeline.bulk;

import product.pipeline.common.ProductEvent;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BulkEventsLambda {
    private final ObjectMapper objectMapper =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                            false);


    private final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private final String snsTopic = System.getenv("FAN_OUT_TOPIC");

    public void handler(S3Event event) {
        event.getRecords().forEach(this::processS3EventRecord);
    }

    private void processS3EventRecord(S3EventNotification.S3EventNotificationRecord record) {
        final List<ProductEvent> productEventsEvents = readProductEventsFromS3(
                record.getS3().getBucket().getName(),
                record.getS3().getObject().getKey());

        productEventsEvents.stream()
                .map(this::productEventToSnsMessage)
                .forEach(message -> sns.publish(snsTopic, message));

        System.out.println("Published " + productEventsEvents.size() + " product events to SNS");
    }

    private List<ProductEvent> readProductEventsFromS3(String bucket, String key) {
        try {
            final S3ObjectInputStream s3is = s3.getObject(bucket, key).getObjectContent();
            final ProductEvent[] productEvents =
                    objectMapper.readValue(s3is, ProductEvent[].class);
            s3is.close();
            return Arrays.asList(productEvents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String productEventToSnsMessage(ProductEvent productEvent) {
        try {
            return objectMapper.writeValueAsString(productEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
