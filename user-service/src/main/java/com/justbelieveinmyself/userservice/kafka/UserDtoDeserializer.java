package com.justbelieveinmyself.userservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

@Log4j2
public class UserDtoDeserializer implements Deserializer<UserDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserDto deserialize(String s, byte[] data) {
        try {
            if (data == null) {
                log.info("Null received at deserializing");
                return null;
            }
            log.info("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), UserDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to UserDto");
        }
    }

}
