package io.bayrktlihn.component;


import io.bayrktlihn.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class MyConfig {

    private String firstName;
    private String lastName;
    private Gender gender;

}
