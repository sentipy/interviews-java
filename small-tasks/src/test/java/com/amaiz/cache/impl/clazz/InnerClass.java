package com.amaiz.cache.impl.clazz;

public class InnerClass {

    private Integer integer;
    private String string;

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InnerClass that = (InnerClass) o;

        if (integer != null ? !integer.equals(that.integer) : that.integer != null) return false;
        return string != null ? string.equals(that.string) : that.string == null;
    }
}
