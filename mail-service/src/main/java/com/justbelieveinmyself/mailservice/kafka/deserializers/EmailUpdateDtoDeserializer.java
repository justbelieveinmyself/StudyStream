package com.justbelieveinmyself.mailservice.kafka.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class EmailUpdateDtoDeserializer implements Deserializer<EmailUpdateDto> { //TODO: listener
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EmailUpdateDto deserialize(String s, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), EmailUpdateDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to EmailUpdateDto");
        }
    }

}