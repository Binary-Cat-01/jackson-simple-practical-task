package com.walking.jackson.baseWay;

import com.fasterxml.jackson.core.JsonFactory;
import com.walking.jackson.baseWay.model.Car;
import com.walking.jackson.baseWay.model.Color;
import com.walking.jackson.baseWay.model.Fine;
import com.walking.jackson.baseWay.repository.CarRepository;
import com.walking.jackson.baseWay.util.JsonCarSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JsonFactory jsonFactory = new JsonFactory();
        JsonCarSerializer jsonCarSerializer = new JsonCarSerializer(jsonFactory);
        CarRepository carRepository = new CarRepository(jsonCarSerializer);

        carRepository.save(createCars());
    }

    private static List<Car> createCars() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("1", 2000, Color.BLACK, true, LocalDateTime.now(),
                List.of(new Fine("1", false), new Fine("2", false))));

        cars.add(new Car("2", 2020, Color.WHITE, false, LocalDateTime.now(),
                List.of(new Fine("2", false))));

        cars.add(new Car(null, 1990, null, true, null,
                null));

        return cars;
    }
}
