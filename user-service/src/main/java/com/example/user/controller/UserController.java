

@RestController
@RequestMapping("/users")
class UserController{
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Yes I'm Up - User Service");
    }
}