package my.sheshenya.springbootfeatures.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

//    @Id
//    private Long userId;

    @Id
    private String userName;

    @NotNull
    @Size(max = 50)
    @NotBlank
    private String userFullName;
}