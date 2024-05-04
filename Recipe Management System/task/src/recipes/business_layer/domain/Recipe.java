package recipes.business_layer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes")
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Recipe {
    @Id
    @SequenceGenerator(name = "recipe_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    @Column(name = "recipe_id", nullable = false)
    private long recipeId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @JsonIgnore
//    private User user;

    @Column(name = "user_email")
    private String userEmail;

    private String name;
    private String category;
    private String description;
    private LocalDateTime date;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> directions;

}
