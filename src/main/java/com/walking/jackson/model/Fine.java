package com.walking.jackson.baseWay.model;

import java.util.Objects;

public class Fine {
    private String id;

    private boolean isPaid;
    private Object someNullField;

    public Fine() {
    }

    public Fine(String id, boolean isPaid) {
        this.id = id;
        this.isPaid = isPaid;
    }

    public String getId() {
        return id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public Object getSomeNullField() {
        return someNullField;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setSomeNullField(Object someNullField) {
        this.someNullField = someNullField;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Fine fine)) {
            return false;
        }

        return isPaid == fine.isPaid && id.equals(fine.id) && Objects.equals(someNullField,
                fine.someNullField);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Fine{" + "id='" + id + '\'' + ", isPaid=" + isPaid + ", someNullField="
                + someNullField + '}';
    }
}
