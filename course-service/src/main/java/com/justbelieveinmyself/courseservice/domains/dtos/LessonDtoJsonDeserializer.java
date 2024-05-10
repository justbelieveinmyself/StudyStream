package com.justbelieveinmyself.courseservice.domains.dtos;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
@JsonComponent
public class LessonDtoJsonDeserializer extends JsonDeserializer<LessonDto> {

    @Override
    public LessonDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectNode node = jsonParser.readValueAsTree();
        if (node.has("lessonType")) {
            String lessonType = node.get("lessonType").asText().toUpperCase();
            if ("PRACTICE".equals(lessonType)) {
                return jsonParser.getCodec().treeToValue(node, PracticeLessonDto.class);
            } else if ("TEST".equals(lessonType)) {
                return jsonParser.getCodec().treeToValue(node, TestLessonDto.class);
            }
        }
        throw new IllegalArgumentException("Unknown type of lesson in JSON: " + node);
    }

}
