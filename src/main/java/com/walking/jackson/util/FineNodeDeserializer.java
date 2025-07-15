package com.walking.jackson.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.walking.jackson.model.Fine;

import java.io.IOException;

public class FineNodeDeserializer extends JsonDeserializer<Fine> {
    @Override
    public Fine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode rootNode = ctxt.readTree(p);

        var fine = new Fine();

        /*При использовании кастомного десериализатора следует проверять отсутствие полей и null-значения.
        Для этого сериализатора проверки опущу. Написал его, для проверки разных вариантов размещения логики
        (де-) сериализации сложных классов*/
        fine.setId(rootNode.get("id").textValue());
        fine.setPaid(rootNode.get("paid").asBoolean());

        JsonNode someNullFieldNode = rootNode.get("someNullField");
        if (!someNullFieldNode.isNull()) {
            throw new IllegalArgumentException("Illegal value: %s".formatted(someNullFieldNode.asText()));
        }

        return fine;
    }
}
