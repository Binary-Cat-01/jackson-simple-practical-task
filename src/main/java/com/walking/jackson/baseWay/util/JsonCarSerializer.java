package com.walking.jackson.baseWay.util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.walking.jackson.baseWay.model.Car;
import com.walking.jackson.baseWay.model.Color;
import com.walking.jackson.baseWay.model.Fine;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class JsonCarSerializer {
    private final JsonFactory jsonFactory;

    public JsonCarSerializer(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public void serialize(Collection<Car> cars, OutputStream outputStream) {
        try (var jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8)) {
            /*используем "человекочитаемое" форматирование, чтобы полюбоваться получившимся json*/
            jsonGenerator.useDefaultPrettyPrinter();

            jsonGenerator.writeStartArray();

            for (Car car : cars) {
                serialize(car, jsonGenerator);
            }

            jsonGenerator.writeEndArray();
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
    }

    private void serialize(Car car, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", car.getId());
        jsonGenerator.writeNumberField("year", car.getYear());

        serializeColor(car.getColor(), jsonGenerator);

        jsonGenerator.writeBooleanField("isActualTechnicalInspection",
                car.isActualTechnicalInspection());

        serializeLastTechnicalInspection(car.getLastTechnicalInspection(), jsonGenerator);

        serializeUnpaidFines(car.getUnpaidFine(), jsonGenerator);

        jsonGenerator.writeEndObject();
    }

    /*Если вместо кастомной логики сериализации нужна стандартная(подходящая для нескольких объектов),
    * можно вынести ее в статический метод утилитарного класса*/
    private void serializeColor(Enum<Color> colorEnum, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeFieldName("color");

        if (colorEnum == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(colorEnum.name());
        }
    }

    private void serializeLastTechnicalInspection(LocalDateTime localDateTime,
            JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeFieldName("lastTechnicalInspection");

        if (localDateTime == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    /*Если предполагается использование сущности Fine не только как составной части Car, то
    * логику ее сериализации стоит вынести в отдельный класс JsonFineSerializer*/
    private void serializeUnpaidFines(Collection<Fine> fines, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeFieldName("unpaidFine");

        if (fines == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeStartArray();

            for (Fine fine : fines) {
                serializeFine(fine, jsonGenerator);
            }

            jsonGenerator.writeEndArray();
        }

    }

    private void serializeFine(Fine fine, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", fine.getId());
        jsonGenerator.writeBooleanField("isPaid", fine.isPaid());
        jsonGenerator.writeNullField("someNullField");

        jsonGenerator.writeEndObject();
    }
}
