package com.walking.jackson.baseWay.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Car {
    private String id;

    private int year;
    private Color color;
    private boolean isActualTechnicalInspection;
    private LocalDateTime lastTechnicalInspection;

    private List<Fine> unpaidFine;

    private LocalDateTime created;
    private LocalDateTime updated;

    public Car(String id, int year, Color color, boolean isActualTechnicalInspection,
            LocalDateTime lastTechnicalInspection, List<Fine> unpaidFine) {
        this.id = id;
        this.year = year;
        this.color = color;
        this.isActualTechnicalInspection = isActualTechnicalInspection;
        this.lastTechnicalInspection = lastTechnicalInspection;
        this.unpaidFine = unpaidFine;
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

    public List<Fine> getUnpaidFine() {
        return unpaidFine;
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

    public void setUnpaidFine(List<Fine> unpaidFine) {
        this.unpaidFine = unpaidFine;
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
                lastTechnicalInspection, car.lastTechnicalInspection) && Objects.equals(unpaidFine,
                car.unpaidFine);
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
                + unpaidFine + '}';
    }
}
