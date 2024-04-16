package com.justbelieveinmyself.authservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

@Log4j2
public class EmailUpdateDtoSerializer implements Serializer<EmailUpdateDto> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, EmailUpdateDto data) {
        try {
            if (data == null) {
                log.info("Null received at serializing");
                return null;
            }
            log.info("Serializing..");
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing EmailUpdateDto to byte[]", e);
        }
    }

}
