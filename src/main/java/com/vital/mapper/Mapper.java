package com.vital.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
