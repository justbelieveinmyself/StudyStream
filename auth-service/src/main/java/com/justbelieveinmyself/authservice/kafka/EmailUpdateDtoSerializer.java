package com.justbelieveinmyself.authservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.authservice.domains.dtos.EmailUpdateDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class EmailUpdateDtoSerializer implements Serializer<EmailUpdateDto> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, EmailUpdateDto data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing..");
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing EmailUpdateDto to byte[]", e);
        }
    }

}
