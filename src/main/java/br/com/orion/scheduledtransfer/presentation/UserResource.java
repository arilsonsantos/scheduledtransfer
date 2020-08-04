package br.com.orion.scheduledtransfer.presentation;


import br.com.orion.scheduledtransfer.application.dto.UserDto;
import br.com.orion.scheduledtransfer.domain.interfaces.IUserService;
import br.com.orion.scheduledtransfer.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/public/users")
public class UserResource {

    private IUserService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserDto> create(@Validated @RequestBody UserDto userDto) {
        User user = userDto.toUser(userDto);
        user = service.create(user);
        userDto = new UserDto(user);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
    }

}
