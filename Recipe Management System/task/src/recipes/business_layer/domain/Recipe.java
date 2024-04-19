package recipes.business_layer.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;
    private String name;
    private String category;
    private String description;
    private LocalDateTime date;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> directions;
}
