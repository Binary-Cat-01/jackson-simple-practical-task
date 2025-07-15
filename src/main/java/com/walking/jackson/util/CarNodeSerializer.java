package com.walking.jackson.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.*;
import com.walking.jackson.model.Car;
import com.walking.jackson.model.Color;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CarNodeSerializer extends JsonSerializer<Car> {
    private final JsonNodeFactory factory;

    public CarNodeSerializer(JsonNodeFactory factory) {
        this.factory = factory;
    }

    @Override
    public void serialize(Car car, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var rootNode = factory.objectNode()
                              .put("id", car.getId())
                              .put("year", car.getYear())
                              .put("color", getColorValue(car.getColor()))
                              .put("isActualTechnicalInspection", car.isActualTechnicalInspection())
                              .put("lastTechnicalInspection",
                                      getLastTechnicalInspectionValue(car.getLastTechnicalInspection()))
                              /*для этого поля используем логику сериализации по-умолчанию или логику из
                              * кастомного сериализатора, если мы зарегистрировали модуль для данного типа*/
                              .putPOJO("unpaidFines", car.getUnpaidFines());

        gen.writeTree(rootNode);
    }

    private String getColorValue(Color color) {
        return color == null ? null : color.name();
    }

    /*для этого поля используем кастомную логику сериализации*/
    private String getLastTechnicalInspectionValue(LocalDateTime lastTechnicalInspection) {
        return lastTechnicalInspection == null ? null : lastTechnicalInspection.format(
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
