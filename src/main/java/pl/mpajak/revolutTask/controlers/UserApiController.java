package pl.mpajak.revolutTask.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mpajak.revolutTask.models.Services.UserService;

@RestController
@RequestMapping("/api")
public class UserApiController {

    final UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity getUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }


}
