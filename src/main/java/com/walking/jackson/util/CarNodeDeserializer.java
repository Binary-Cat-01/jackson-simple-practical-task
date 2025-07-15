package com.walking.jackson.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.jackson.model.Car;
import com.walking.jackson.model.Color;
import com.walking.jackson.model.Fine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarNodeDeserializer extends JsonDeserializer<Car> {

    /*Действия при null-значении JSON:
     lastTechnicalInspection - считаем null допустимым значением,
     unpaidFines - возвращаем пустую коллекцию,
     остальные поля - выбрасываем IllegalArgumentException*/
    @Override
    public Car deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode rootNode = ctxt.readTree(p);

        var car = new Car();

        car.setId(getIdFrom(rootNode, "id"));
        car.setYear(getYearFrom(rootNode, "year"));
        car.setColor(getColorFrom(rootNode, "color"));
        car.setActualTechnicalInspection(getActualTechnicalInspection(rootNode, "isActualTechnicalInspection"));

        ObjectMapper mapper = (ObjectMapper) p.getCodec();

        car.setLastTechnicalInspection(getLastTechnicalInspectionFrom(rootNode, "lastTechnicalInspection", mapper));
        car.setUnpaidFines(getUnpaidFinesFrom(rootNode, "unpaidFines", mapper));

        return car;
    }

    private String getIdFrom(JsonNode rootNode, String fieldName) {
        var idNode = rootNode.get(fieldName);

        if (idNode == null || idNode.isNull()) {
            throw new IllegalArgumentException("Non null value expected for field '%s'".formatted(fieldName));
        }

        return idNode.asText();
    }

    private int getYearFrom(JsonNode rootNode, String fieldName) {
        var yearNode = rootNode.get(fieldName);

        if (yearNode == null || yearNode.isNull()) {
            throw new IllegalArgumentException("Non null value expected for field '%s'".formatted(fieldName));
        }

        return yearNode.asInt();
    }

    private Color getColorFrom(JsonNode rootNode, String fieldName) {
        var colorNode = rootNode.get(fieldName);

        if (colorNode == null || colorNode.isNull()) {
            throw new IllegalArgumentException("Non null value expected for field '%s'".formatted(fieldName));
        }

        return Color.valueOf(colorNode.asText());
    }

    private boolean getActualTechnicalInspection(JsonNode rootNode, String fieldName) {
        var actualTechnicalInspectionNode = rootNode.get(fieldName);

        if (actualTechnicalInspectionNode == null || actualTechnicalInspectionNode.isNull()) {
            throw new IllegalArgumentException("Non null value expected for field '%s'".formatted(fieldName));
        }

        return actualTechnicalInspectionNode.asBoolean();
    }

    private LocalDateTime getLastTechnicalInspectionFrom(JsonNode rootNode, String fieldName, ObjectMapper mapper) {
        var lastTechnicalInspectionNode = rootNode.get(fieldName);

        return (lastTechnicalInspectionNode == null || lastTechnicalInspectionNode.isNull())
               ? null
               : mapper.convertValue(lastTechnicalInspectionNode, new TypeReference<>() {
               });
    }

    private List<Fine> getUnpaidFinesFrom(JsonNode rootNode, String fieldName, ObjectMapper mapper) {
        var unpaidFinesNode = rootNode.get(fieldName);

        return (unpaidFinesNode == null || unpaidFinesNode.isNull())
               ? new ArrayList<>()
               : mapper.convertValue(unpaidFinesNode, new TypeReference<>() {
                });
    }
}
