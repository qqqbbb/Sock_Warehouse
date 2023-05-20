package javacourse.sock_warehouse;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/socks")
public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    /**
     * remove socks from DB
     *
     * @param socksDTO socks data json  {@link SocksDTO}
     * @return 200 - success, 400 - invalid parameters, 500 - server error
     */
    @PostMapping("/outcome")
    public ResponseEntity<?> removeSocks(@RequestBody SocksDTO socksDTO){
        return service.removeSocks(socksDTO);
    }

    /**
     * add socks to DB
     *
     * @param socksDTO socks data json  {@link SocksDTO}
     * @return 200 - success, 400 - invalid parameters, 500 - server error
     */
    @PostMapping("/income")
    public ResponseEntity<?> addSocks(@RequestBody SocksDTO socksDTO){
        return service.addSocks(socksDTO);
    }

    /**
     * get socks count
     *
     * @param color: socks color string
     * @param operation: cotton % operator string: "equal" or "moreThan" or "lessThan"
     * @param cottonPart: cotton % int between 0 and 100
     * @return 200 - success (socks count in ResponseEntity body), 400 - invalid parameters, 500 - server error
     */
    @GetMapping
    public ResponseEntity<?> getSocksCount(@RequestParam("color") String color, @RequestParam("operation") String operation, @RequestParam("cottonPart") int cottonPart){
        return service.getSocksCount(color, operation, cottonPart);
    }

}
