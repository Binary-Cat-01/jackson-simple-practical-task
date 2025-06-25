package com.walking.jackson.baseWay.repository;

import com.walking.jackson.baseWay.model.Car;
import com.walking.jackson.baseWay.util.JsonCarSerializer;

import java.io.*;
import java.util.Collection;

public class CarRepository {
    private final File file = new File("./src/main/resources/cars.json");
    private final JsonCarSerializer serializer;

    public CarRepository(JsonCarSerializer serializer) {
        this.serializer = serializer;
    }

    public void save(Collection<Car> cars) {
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            serializer.serialize(cars, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла %s".formatted(file.getPath()));
        }
    }

//    public Collection<Car> load() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String json = reader.readLine();
//
//        } catch (IOException e) {
//            throw new RuntimeException("Ошибка при чтении файла %s".formatted(file.getPath()));
//        }
//    }
}
