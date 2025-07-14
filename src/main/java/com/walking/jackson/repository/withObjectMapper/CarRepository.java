package com.walking.jackson.repository.withObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.jackson.model.Car;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;

public class CarRepository {
    private final Path path = Path.of("./src/main/resources/cars(withObjectMapper).json");
    private final ObjectMapper objectMapper;

    public CarRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void write(Collection<Car> cars) {
        try {
            objectMapper.writeValue(path.toFile(), cars);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла %s".formatted(path.getFileName()), e);
        }
    }

    public Collection<Car> read() {
        try {
            return objectMapper.readValue(path.toFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла %s".formatted(path.getFileName()), e);
        }
    }
}
