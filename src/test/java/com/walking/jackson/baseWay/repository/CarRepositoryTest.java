package com.walking.jackson.baseWay.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.walking.jackson.baseWay.model.Car;
import com.walking.jackson.baseWay.model.Color;
import com.walking.jackson.baseWay.model.Fine;
import com.walking.jackson.baseWay.util.JsonCarSerializer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    private final CarRepository carRepository = new CarRepository(new JsonCarSerializer(new JsonFactory()));

    /**
     * Это видимо уже ближе к интеграционному тесту?*/
    @Test
    void cars_after_read_equals_cars_before_write() {
//        given:
        var expected = createCars();

//        when:
        carRepository.write(expected);
        var actual = carRepository.read();

//        then:
        assertIterableEquals(expected, actual);
    }

    private Collection<Car> createCars() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("1", 2000, Color.BLACK, true, LocalDateTime.now(),
                List.of(new Fine("1", false), new Fine("2", false))));

        cars.add(new Car("2", 2020, Color.WHITE, false, LocalDateTime.now(),
                List.of(new Fine("2", false))));

        cars.add(new Car(null, 1990, Color.BLACK, true, null,
                null));

        return cars;
    }
}
