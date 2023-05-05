package stacs.travel.cs5031p2code.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.Objects;

/**
 * The class for representing the stop class.
 *
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class Stops {

    /**
     * The name of stop.
     */
    private String stopName;

    /**
     * The method for checking whether this object are equaled.
     * If they have the same stop name, will be equaled.
     *
     * @param o the object.
     * @return return if equaled.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stops stops = (Stops) o;
        return Objects.equals(stopName, stops.stopName);
    }

    /**
     * Overriding hashCode protocol.
     *
     * @return the hash of the stopName.
     */
    @Override
    public int hashCode() {
        return Objects.hash(stopName);
    }
}
