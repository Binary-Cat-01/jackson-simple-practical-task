package com.walking.jackson.repository.imperative;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.walking.jackson.model.Car;
import com.walking.jackson.util.CarCollectionImperativeDeserializer;
import com.walking.jackson.util.CarCollectionImperativeSerializer;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;

public class CarRepository {
    private final Path path = Path.of("./src/main/resources/cars(imperative).json");
    private final JsonFactory jsonFactory;
    private final CarCollectionImperativeSerializer serializer;
    private final CarCollectionImperativeDeserializer deserializer;

    public CarRepository(JsonFactory factory, CarCollectionImperativeSerializer serializer,
            CarCollectionImperativeDeserializer deserializer) {
        this.jsonFactory = factory;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    public void write(Collection<Car> cars) {
        try (var generator = jsonFactory.createGenerator(path.toFile(), JsonEncoding.UTF8)) {
            /*используем "человекочитаемое" форматирование, чтобы полюбоваться получившимся json*/
            generator.useDefaultPrettyPrinter();

            serializer.serialize(cars, generator);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла %s".formatted(path.getFileName()), e);
        }
    }

    public Collection<Car> read() {
        try (var parser = jsonFactory.createParser(path.toFile())) {
            return deserializer.deserialize(parser);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла %s".formatted(path.getFileName()), e);
        }
    }
}
