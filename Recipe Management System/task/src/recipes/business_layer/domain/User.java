package recipes.business_layer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id")
    private long userId;
    @Email
    private String email;
    @NotBlank
    @Size(min = 8)
    @ToString.Exclude
    private String password;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL) // , fetch = FetchType.EAGER
    private List<Recipe> recipes;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;
}
