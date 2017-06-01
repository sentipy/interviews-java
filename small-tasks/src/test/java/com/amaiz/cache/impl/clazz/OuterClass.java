package com.amaiz.cache.impl.clazz;

import java.util.Map;

public class OuterClass {

    private InnerClass innerClass;
    private Integer integer;
    private String string;
    private Map<Integer, InnerClass> mapIntegerInnerClass;
    private Map<String, InnerClass> mapStringInnerClass;

    public InnerClass getInnerClass() {
        return innerClass;
    }

    public void setInnerClass(InnerClass innerClass) {
        this.innerClass = innerClass;
    }

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

    public Map<Integer, InnerClass> getMapIntegerInnerClass() {
        return mapIntegerInnerClass;
    }

    public void setMapIntegerInnerClass(Map<Integer, InnerClass> mapIntegerInnerClass) {
        this.mapIntegerInnerClass = mapIntegerInnerClass;
    }

    public Map<String, InnerClass> getMapStringInnerClass() {
        return mapStringInnerClass;
    }

    public void setMapStringInnerClass(Map<String, InnerClass> mapStringInnerClass) {
        this.mapStringInnerClass = mapStringInnerClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OuterClass that = (OuterClass) o;

        if (innerClass != null ? !innerClass.equals(that.innerClass) : that.innerClass != null) return false;
        if (integer != null ? !integer.equals(that.integer) : that.integer != null) return false;
        if (string != null ? !string.equals(that.string) : that.string != null) return false;
        if (mapIntegerInnerClass != null ? !mapIntegerInnerClass.equals(that.mapIntegerInnerClass) : that.mapIntegerInnerClass != null)
            return false;
        return mapStringInnerClass != null ? mapStringInnerClass.equals(that.mapStringInnerClass) : that.mapStringInnerClass == null;
    }
}
