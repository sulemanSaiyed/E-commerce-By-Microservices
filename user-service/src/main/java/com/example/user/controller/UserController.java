package com.example.user.controller;
import com.example.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
class UserController{
    private final UserService userService;
    private ResponseEntity<UserResponse> registerUser(RegistrationRequest request, UserRole role) {
        UserResponse response = userService.registerUser(request, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Yes I'm Up - User Service");
    }
    @PostMapping("/register/seller")
    public ResponseEntity<UserResponse> registerSeller(@RequestBody RegistrationRequest request) {
        return registerUser(request, UserRole.SELLER);
    }
    @PostMapping("/register/customer")
    public ResponseEntity<UserResponse> registerCustomer(@RequestBody RegistrationRequest request) {
        return registerUser(request, UserRole.CUSTOMER);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        UserResponse response = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

}
