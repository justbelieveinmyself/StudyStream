package com.justbelieveinmyself.courseservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.courseservice.domains.dtos.UserDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class UserDtoDeserializer implements Deserializer<UserDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserDto deserialize(String s, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), UserDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to UserDto");
        }
    }
}