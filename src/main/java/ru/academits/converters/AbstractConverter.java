package ru.academits.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<S, D> implements Converter<S, D> {
    @Override
    public List<D> convert(List<S> source) {
        return source.stream().map(this::convert).collect(Collectors.toList());
    }
}