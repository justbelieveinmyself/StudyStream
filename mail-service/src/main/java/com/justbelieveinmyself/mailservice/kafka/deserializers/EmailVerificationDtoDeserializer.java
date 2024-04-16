package com.justbelieveinmyself.mailservice.kafka.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.mailservice.domains.dto.EmailVerificationDto;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

@Log4j2
public class EmailVerificationDtoDeserializer implements Deserializer<EmailVerificationDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EmailVerificationDto deserialize(String s, byte[] data) {
        try {
            if (data == null){
                log.info("Null received at deserializing");
                return null;
            }
            log.info("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), EmailVerificationDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to EmailVerificationDto");
        }
    }

}
