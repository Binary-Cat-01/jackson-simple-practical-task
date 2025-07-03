package com.walking.jackson.baseWay.repository;

import com.walking.jackson.baseWay.model.Car;
import com.walking.jackson.baseWay.util.JsonCarSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class CarRepository {
    private final Path path = Path.of("./src/main/resources/cars.json");
    private final JsonCarSerializer serializer;

    public CarRepository(JsonCarSerializer serializer) {
        this.serializer = serializer;
    }

    public void write(Collection<Car> cars) {
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path))) {

            serializer.serialize(cars, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла %s".formatted(path.getFileName()));
        }
    }

    public Collection<Car> read() {
        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(path))) {

            return serializer.deserialize(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла %s".formatted(path.getFileName()));
        }
    }
}
