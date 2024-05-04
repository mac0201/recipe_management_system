package recipes.business_layer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "user_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id")
    private long userId;
    @Email
    private String email;
    @NotBlank
    @Size(min = 8)
    @ToString.Exclude
    private String password;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @Fetch(FetchMode.JOIN)
//    private List<Recipe> recipes;

    @OneToMany(mappedBy = "userEmail", cascade = CascadeType.ALL) // , fetch = FetchType.EAGER
    @JsonIgnore
//    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private List<Recipe> recipes;

    @ElementCollection //, fetch = FetchType.EAGER
    private List<String> authorities;
//
//    @Override
//    public String toString() {
//        return "User[id=" + userId + ", email=" + email + "]";
//    }
}
