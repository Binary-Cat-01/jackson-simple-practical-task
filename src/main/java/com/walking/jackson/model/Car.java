package com.walking.jackson.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.walking.jackson.util.FineNodeDeserializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Car {
    @JsonProperty("id")
    @JsonSetter(nulls = Nulls.FAIL)
    private String id;

    @JsonProperty("year")
    @JsonSetter(nulls = Nulls.FAIL)
    private int year;

    @JsonProperty("color")
    @JsonSetter(nulls = Nulls.FAIL)
    private Color color;

    @JsonAlias({"actualTechnicalInspection", "isActualTechnicalInspection"})
    @JsonSetter(nulls = Nulls.FAIL)
    private boolean isActualTechnicalInspection;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastTechnicalInspection;

    /*Для десериализации элементов коллекции используем кастомный десериализатор
    * и null-значения десериализуем как пустую коллекцию*/
    @JsonProperty("unpaidFines")
    @JsonSetter(nulls = Nulls.SKIP)
    @JsonDeserialize(contentUsing = FineNodeDeserializer.class)
    private List<Fine> unpaidFines = new ArrayList<>();

    /*Технические поля, которые мы не хотим сериализовать*/
    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime updated;

    public Car() {
    }

    public Car(String id, int year, Color color, boolean isActualTechnicalInspection,
            LocalDateTime lastTechnicalInspection, List<Fine> unpaidFines) {
        this.id = id;
        this.year = year;
        this.color = color;
        this.isActualTechnicalInspection = isActualTechnicalInspection;
        this.lastTechnicalInspection = lastTechnicalInspection;
        this.unpaidFines = unpaidFines;
    }

    public String getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public Color getColor() {
        return color;
    }

    public boolean isActualTechnicalInspection() {
        return isActualTechnicalInspection;
    }

    public LocalDateTime getLastTechnicalInspection() {
        return lastTechnicalInspection;
    }

    public List<Fine> getUnpaidFines() {
        return unpaidFines;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setActualTechnicalInspection(boolean actualTechnicalInspection) {
        isActualTechnicalInspection = actualTechnicalInspection;
    }

    public void setLastTechnicalInspection(LocalDateTime lastTechnicalInspection) {
        this.lastTechnicalInspection = lastTechnicalInspection;
    }

    public void setUnpaidFines(List<Fine> unpaidFines) {
        this.unpaidFines = unpaidFines;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Car car)) {
            return false;
        }

        return year == car.year && isActualTechnicalInspection == car.isActualTechnicalInspection
                && Objects.equals(id, car.id) && color == car.color && Objects.equals(
                lastTechnicalInspection, car.lastTechnicalInspection) && Objects.equals(unpaidFines,
                car.unpaidFines);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + year;
        result = 31 * result + color.hashCode();
        result = 31 * result + Boolean.hashCode(isActualTechnicalInspection);
        result = 31 * result + Objects.hashCode(lastTechnicalInspection);
        result = 31 * result + Objects.hashCode(unpaidFines);
        return result;
    }

    @Override
    public String toString() {
        return "Car{" + "id='" + id + '\'' + ", year=" + year + ", color=" + color
                + ", isActualTechnicalInspection=" + isActualTechnicalInspection
                + ", lastTechnicalInspection=" + lastTechnicalInspection + ", unpaidFines="
                + unpaidFines + '}';
    }
}
