package br.com.orion.scheduledtransfer.application.dto;

import br.com.orion.scheduledtransfer.domain.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    @NotEmpty(message = "Username must be not null.")
    @Size(min = 5, max = 20, message = "Username must have between 5 and 20 characters.")
    private String username;
    private String password;

    public UserDto(User user){
        id = user.getId();
        username = user.getUsername();
    }

    public User toUser(UserDto dto){
        return User.builder().id(dto.getId()).username(dto.getUsername()).password(dto.password).build();
    }
}
