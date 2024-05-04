package recipes.business_layer.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;
}
