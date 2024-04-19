package recipes.business_layer;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import java.util.List;

/** A generic mapper class used for object-to-object mapping. */
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

    /**
     * Converts a stream of source objects into a list of destination objects using a mapping mechanism.
     * The mapping is performed by the internal {@link ModelMapper} instance, which transforms objects from source
     *      type (S) to the specified destination type (D).
     * @param source The Streamable source collection containing objects to be mapped
     * @param destinationClass The class representing the desired type for the mapped objects in the list
     * @param <S> The type of elements in the source stream
     * @param <D> The type of elements in the resulting list
     * @return A new List containing the mapped objects of type D.
     */
    public <S, D> List<D> mapAll(Streamable<S> source, Class<D> destinationClass) {
        return source.map(obj -> modelMapper.map(obj, destinationClass)).stream().toList();
    }
}
