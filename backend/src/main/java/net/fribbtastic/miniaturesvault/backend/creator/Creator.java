package net.fribbtastic.miniaturesvault.backend.creator;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Frederic Eßer
 */
@Entity
@Table(name = "CREATORS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String name;

    /**
     * Create a new Creator with the name as parameter
     *
     * @param name - the name of the Creator
     */
    public Creator(@NonNull String name) {
        this.name = name;
    }
}