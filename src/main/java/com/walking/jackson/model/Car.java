package com.walking.jackson.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Car {
    private String id;

    private int year;
    private Color color;
    private boolean isActualTechnicalInspection;
    private LocalDateTime lastTechnicalInspection;

    private List<Fine> unpaidFines;

    /*Технические поля, которые мы не хотим сериализовать*/
    private LocalDateTime created;
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
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Car{" + "id='" + id + '\'' + ", year=" + year + ", color=" + color
                + ", isActualTechnicalInspection=" + isActualTechnicalInspection
                + ", lastTechnicalInspection=" + lastTechnicalInspection + ", unpaidFine="
                + unpaidFines + '}';
    }
}
