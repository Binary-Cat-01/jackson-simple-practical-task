package com.walking.jackson.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.walking.jackson.model.Car;
import com.walking.jackson.model.Color;
import com.walking.jackson.model.Fine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonCarImperativeDeserializer {
    public Collection<Car> deserialize(JsonParser jsonParser) throws IOException {
        Collection<Car> cars = new ArrayList<>();

        if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
            throw new IOException("Unexpected token");
        }

        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            cars.add(parseCar(jsonParser));
        }

        return cars;
    }

    private Car parseCar(JsonParser jsonParser) throws IOException {
        if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
            throw new IOException("Unexpected token");
        }

        var car = new Car();

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.currentName();
            jsonParser.nextToken();

            switch (fieldName) {
                case "id" -> car.setId(jsonParser.getValueAsString());
                case "year" -> car.setYear(jsonParser.getIntValue());
                case "color" -> car.setColor(parseColor(jsonParser));
                case "isActualTechnicalInspection" ->
                        car.setActualTechnicalInspection(jsonParser.getBooleanValue());
                case "unpaidFine" -> car.setUnpaidFine(parseUnpaidFines(jsonParser));
                case "lastTechnicalInspection" ->
                        car.setLastTechnicalInspection(parseLastTechnicalInspection(jsonParser));

                default -> throw new IOException(
                        "Unknown token: %s, for field: %s".formatted(jsonParser.currentToken(),
                                fieldName));
            }
        }

        return car;
    }

    private Color parseColor(JsonParser jsonParser) throws IOException {
        return Color.valueOf(jsonParser.getValueAsString());
    }

    private List<Fine> parseUnpaidFines(JsonParser jsonParser) throws IOException {
        List<Fine> fines = new ArrayList<>();

        if (jsonParser.currentToken() == JsonToken.VALUE_NULL) {
            return fines;
        }

        if (jsonParser.currentToken() != JsonToken.START_ARRAY) {
            throw new IOException("Unexpected token");
        }

        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            fines.add(parseFine(jsonParser));
        }

        return fines;
    }

    private Fine parseFine(JsonParser jsonParser) throws IOException {
        if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
            throw new IOException("Unexpected token");
        }

        var fine = new Fine();

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.currentName();
            jsonParser.nextToken();

            switch (fieldName) {
                case "id" -> fine.setId(jsonParser.getValueAsString());
                case "isPaid" -> fine.setPaid(jsonParser.getBooleanValue());
                case "someNullField" -> parseSomeNullField(jsonParser);

                default -> throw new IOException(
                        "Unknown token: %s, for field: %s".formatted(jsonParser.currentToken(),
                                fieldName));
            }
        }

        return fine;
    }

    private void parseSomeNullField(JsonParser jsonParser) throws IOException {
        var value = jsonParser.getValueAsString();

        if (value != null) {
            throw new RuntimeException("Illegal value: %s".formatted(value));
        }
    }

    private LocalDateTime parseLastTechnicalInspection(JsonParser jsonParser) throws IOException {
        var value = jsonParser.getValueAsString();

        return value == null
                ? null
                : LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
