package com.CS6510.microservice.dto;

import com.CS6510.microservice.enums.ResultEnum;

import java.util.List;

public class Execution<T> {
    private ResultEnum result;
    private int count;
    private T object;
    private List<T> objects;

    // default no-argument constructor
    public Execution() {
    }

    // constructor when execution fails
    public Execution(ResultEnum result) {
        this.result = result;
    }

    // constructor when execution for single result successes
    public Execution(ResultEnum result, T object) {
        this.result = result;
        this.object = object;
    }

    // constructor when execution for multiple results successes
    public Execution(ResultEnum result, List<T> objects) {
        this.result = result;
        this.objects = objects;
    }

    // constructor when execution for queries like insert/update/delete
    public Execution(ResultEnum result, int count) {
        this.result = result;
        this.count = count;
    }

    public ResultEnum getResult() {
        return result;
    }

    public void setResult(ResultEnum result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }
}
