package com.walking.jackson.baseWay.util;

import com.fasterxml.jackson.core.*;
import com.walking.jackson.baseWay.model.Car;
import com.walking.jackson.baseWay.model.Color;
import com.walking.jackson.baseWay.model.Fine;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonCarSerializer {
    private final JsonFactory jsonFactory;

    public JsonCarSerializer(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public void serialize(Collection<Car> cars, OutputStream outputStream) {
        try (var jsonGenerator = jsonFactory.createGenerator(outputStream)) {
            /*используем "человекочитаемое" форматирование, чтобы полюбоваться получившимся json*/
            jsonGenerator.useDefaultPrettyPrinter();

            jsonGenerator.writeStartArray();

            for (Car car : cars) {
                serialize(car, jsonGenerator);
            }

            jsonGenerator.writeEndArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сериализации %s".formatted(cars), e);
        }
    }

    public Collection<Car> deserialize(InputStream inputStream) {
        Collection<Car> cars = new ArrayList<>();

        try (var jsonParser = jsonFactory.createParser(inputStream)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("Unexpected token");
            }

            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                cars.add(parseCar(jsonParser));
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при десериализации", e);
        }

        return cars;
    }

    private void serialize(Car car, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", car.getId());
        jsonGenerator.writeNumberField("year", car.getYear());

        generateColor(car.getColor(), jsonGenerator);

        jsonGenerator.writeBooleanField("isActualTechnicalInspection",
                car.isActualTechnicalInspection());

        generateLastTechnicalInspection(car.getLastTechnicalInspection(), jsonGenerator);

        generateUnpaidFines(car.getUnpaidFine(), jsonGenerator);

        jsonGenerator.writeEndObject();
    }

    /*Если вместо кастомной логики сериализации нужна стандартная(например, для служебных классов java),
     * можно вынести ее в статические методы утилитарного класса*/
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

    private void generateUnpaidFines(Collection<Fine> fines, JsonGenerator jsonGenerator) throws
            IOException {
        jsonGenerator.writeFieldName("unpaidFine");

        if (fines== null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeStartArray();

            for (Fine fine : fines) {
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
        var value = Optional.ofNullable(jsonParser.getValueAsString());

        return Color.valueOf(
                value.orElseThrow(() -> new IllegalArgumentException("Color could not be null"))
                     .toUpperCase());
    }

    private List<Fine> parseUnpaidFines(JsonParser jsonParser) throws IOException {
        if (jsonParser.currentToken() == JsonToken.VALUE_NULL) {
            return null;
        }

        if (jsonParser.currentToken() != JsonToken.START_ARRAY) {
            throw new IOException("Unexpected token");
        }

        List<Fine> fines = new ArrayList<>();

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

        return value == null ?
                null :
                LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
