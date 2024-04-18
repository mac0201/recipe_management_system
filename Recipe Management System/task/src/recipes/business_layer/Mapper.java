package recipes.business_layer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Arrays.stream;

// A generic mapper class used for object-to-object mapping.
@Component
public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper() { this.modelMapper = new ModelMapper(); }

    /**
     * Maps a source object to a destination class.
     *
     * This method utilizes the internal {@link ModelMapper} to perform the mapping
     * between the provided source object and the specified destination class.
     *
     * @param source The source object to be mapped.
     * @param destinationClass The class representing the desired type for the mapped object.
     * @return A new instance of the destination class containing the mapped data.
     */
    public <S, D> D map(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    public <S, D> List<D> mapAll(Iterable<S> source, Class<D> destinationClass) {
        return StreamSupport.stream(source.spliterator(), false)
                .map(obj -> modelMapper.map(obj, destinationClass))
                .toList();
    }
}
