package com.walking.jackson.util;

import com.fasterxml.jackson.core.*;
import com.walking.jackson.model.Car;
import com.walking.jackson.model.Color;
import com.walking.jackson.model.Fine;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CarCollectionImperativeSerializer {
    public void serialize(Collection<Car> cars, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartArray();

        for (Car car : cars) {
            serialize(car, jsonGenerator);
        }

        jsonGenerator.writeEndArray();
    }

    private void serialize(Car car, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", car.getId());
        jsonGenerator.writeNumberField("year", car.getYear());

        generateColor(car.getColor(), jsonGenerator);

        jsonGenerator.writeBooleanField("isActualTechnicalInspection",
                car.isActualTechnicalInspection());

        generateLastTechnicalInspection(car.getLastTechnicalInspection(), jsonGenerator);

        generateUnpaidFines(car.getUnpaidFines(), jsonGenerator);

        jsonGenerator.writeEndObject();
    }

    private void generateColor(Enum<Color> colorEnum, JsonGenerator jsonGenerator) throws
            IOException {
        jsonGenerator.writeFieldName("color");

        if (colorEnum == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(colorEnum.name());
        }
    }

    private void generateLastTechnicalInspection(LocalDateTime localDateTime,
            JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeFieldName("lastTechnicalInspection");

        if (localDateTime == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    private void generateUnpaidFines(Collection<Fine> unpaidFines, JsonGenerator jsonGenerator) throws
            IOException {
        jsonGenerator.writeFieldName("unpaidFines");

        if (unpaidFines == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeStartArray();

            for (Fine fine : unpaidFines) {
                generateFine(fine, jsonGenerator);
            }

            jsonGenerator.writeEndArray();
        }
    }

    private void generateFine(Fine fine, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", fine.getId());
        jsonGenerator.writeBooleanField("isPaid", fine.isPaid());
        jsonGenerator.writeNullField("someNullField");

        jsonGenerator.writeEndObject();
    }
}
