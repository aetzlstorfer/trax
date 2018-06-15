package at.aetzlstorfer.traxbackend.facades.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public interface Converter<S, T> extends org.springframework.core.convert.converter.Converter<S, T> {

    default T convert(Optional<S> sourceOptional) {
        return sourceOptional.map(this::convert).orElse(null);
    }

    default List<T> convertIterable(Iterable<S> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(this::convert)
                .collect(Collectors.toList());
    }

}
