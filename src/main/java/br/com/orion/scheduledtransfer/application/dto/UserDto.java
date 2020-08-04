package br.com.orion.scheduledtransfer.application.dto;

import br.com.orion.scheduledtransfer.domain.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    @NotEmpty
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
