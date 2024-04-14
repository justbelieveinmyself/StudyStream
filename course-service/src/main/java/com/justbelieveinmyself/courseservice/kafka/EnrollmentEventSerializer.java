package com.justbelieveinmyself.courseservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.library.dto.EnrollmentEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class EnrollmentEventSerializer implements Serializer<EnrollmentEvent> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, EnrollmentEvent data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing..");
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing EnrollmentEvent to byte[]", e);
        }
    }

}
