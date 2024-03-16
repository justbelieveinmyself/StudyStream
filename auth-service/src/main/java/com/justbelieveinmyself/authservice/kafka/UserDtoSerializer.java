package com.justbelieveinmyself.authservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class UserDtoSerializer implements Serializer<UserDto> {
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public byte[] serialize(String topic, UserDto data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing..");
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing UserDto to byte[]", e);
        }
    }

}
