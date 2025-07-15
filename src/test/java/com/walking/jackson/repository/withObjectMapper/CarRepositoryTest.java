package com.walking.jackson.repository.withObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.walking.jackson.model.Car;
import com.walking.jackson.model.Color;
import com.walking.jackson.model.Fine;
import com.walking.jackson.util.CarNodeDeserializer;
import com.walking.jackson.util.CarNodeSerializer;
import com.walking.jackson.util.FineNodeDeserializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    private static ObjectMapper objectMapper;
    private final CarRepository carRepository = new CarRepository(objectMapper);

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();

        var carNodeSerializer = new CarNodeSerializer(JsonNodeFactory.instance);
        var carNodeDeserializer = new CarNodeDeserializer();
        var fineNodeDeserializer = new FineNodeDeserializer();

        var carNodeModule = new SimpleModule().addSerializer(Car.class, carNodeSerializer)
                                              .addDeserializer(Car.class, carNodeDeserializer)
                                              .addDeserializer(Fine.class, fineNodeDeserializer);

        objectMapper.registerModule(carNodeModule)
                    .registerModule(new JavaTimeModule())
                    .enable(SerializationFeature.INDENT_OUTPUT);
    }

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

        cars.add(new Car("3", 1990, Color.BLACK, true, null,
                Collections.emptyList()));

        return cars;
    }
}
