package javacourse.sock_warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@org.springframework.stereotype.Service
public class Service {

    private final Repository repository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Service(Repository repository) {
        this.repository = repository;
    }

    /**
     * add socks to DB
     *
     * @param socksDTO socks data json  {@link SocksDTO}
     * @return 200 - success, 400 - invalid parameters, 500 - server error
     */
    public ResponseEntity<?> addSocks(SocksDTO socksDTO){
        log.info("addSocks: " + socksDTO);
        if (socksDTO == null || socksDTO.getColor() == null || socksDTO.getColor().trim().isEmpty() || socksDTO.getCottonPart() < 0 || socksDTO.getCottonPart() > 100 || socksDTO.getQuantity() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid parameter");
        }
        String color = socksDTO.getColor().trim().toLowerCase();
        Socks storedSocks = repository.findByColorAndCotton(color, socksDTO.getCottonPart());
        if (storedSocks == null) {
            log.info("addSocks: save new socks ");
            Socks socks = new Socks(color, socksDTO.getCottonPart(), socksDTO.getQuantity());
            repository.save(socks);
            return ResponseEntity.ok().build();
        } else {
            long quantity = storedSocks.getQuantity() + socksDTO.getQuantity();
            log.info("addSocks save quantity " + quantity);
            storedSocks.setQuantity(quantity);
            repository.save(storedSocks);
            return ResponseEntity.ok().build();
        }
    }

    /**
     * remove socks from DB
     *
     * @param socksDTO socks data json  {@link SocksDTO}
     * @return 200 - success, 400 - invalid parameters, 500 - server error
     */
    public ResponseEntity<?> removeSocks(SocksDTO socksDTO) {
        log.info("removeSocks: " + socksDTO);
        if (socksDTO == null || socksDTO.getColor() == null || socksDTO.getColor().trim().isEmpty() || socksDTO.getCottonPart() < 0 || socksDTO.getCottonPart() > 100 || socksDTO.getQuantity() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid parameter");
        }
        String color = socksDTO.getColor().trim().toLowerCase();
        Socks storedSocks = repository.findByColorAndCotton(color, socksDTO.getCottonPart());
        if (storedSocks == null) {
            log.error("removeSocks: socks not in DB");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("socks not in DB");
        } else {
            if (storedSocks.getQuantity() == 0){
                log.error("removeSocks: count 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity already 0");
            }
            long quantity = storedSocks.getQuantity() - socksDTO.getQuantity();
            if (quantity < 0 ){
                log.error("removeSocks: " + socksDTO + " negative count ");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cant subtract " + socksDTO.getQuantity() + " from " + storedSocks.getQuantity());
            }
            log.info("removeSocks save quantity " + quantity);
            storedSocks.setQuantity(quantity);
            repository.save(storedSocks);
            return ResponseEntity.ok().build();
        }
    }

    /**
     * get socks count
     *
     * @param color: socks color string
     * @param operation: cotton % operator string. Accepts values: "equal", "moreThan", "lessThan"
     * @param cottonPart: cotton % int between 0 and 100
     * @return 200 - success (socks count in ResponseEntity body), 400 - invalid parameters, 500 - server error
     */
    public ResponseEntity<?> getSocksCount(String color, String operation, int cottonPart) {
        log.info("getSocksCount: color: " + color + " operation: " + operation + " cottonPart " + cottonPart);
        if (color == null || operation == null || cottonPart < 0 || cottonPart > 100) {
            log.error("getSocksCount: invalid parameter");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid parameter");
        }
        color = color.trim().toLowerCase();
        operation = operation.trim();
        if (color.isEmpty() || operation.isEmpty()) {
            log.error("getSocksCount: empty parameter string");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("empty parameter string");
        }
        List<Socks> storedSocksList = repository.findAllByColor(color);
        if (storedSocksList.size() == 0) {
            log.info("getSocksCount: no socks of color " + color + " in DB");
            return ResponseEntity.ok("0");
        }
        if (operation.equals("equal")) {
            for (Socks s : storedSocksList) {
                if (s.getCotton() == cottonPart) {
                    log.info("getSocksCount: found == " + s);
                    return ResponseEntity.ok(String.valueOf(s.getQuantity()));
                }
            }
            log.info("getSocksCount: no socks with cotton % " + cottonPart);
            return ResponseEntity.ok("0");
        } else if (operation.equals("moreThan")) {
            int count = 0;
            for (Socks s : storedSocksList) {
                if (s.getCotton() > cottonPart)
                    count += s.getQuantity();
            }
            log.info("getSocksCount: found " + count + " socks with cotton % > " + cottonPart);
            return ResponseEntity.ok(String.valueOf(count));
        } else if (operation.equals("lessThan")) {
            int count = 0;
            for (Socks s : storedSocksList) {
                if (s.getCotton() < cottonPart)
                    count += s.getQuantity();
            }
            log.info("getSocksCount: found " + count + " socks with cotton % < " + cottonPart);
            return ResponseEntity.ok(String.valueOf(count));
        } else {
            log.error("getSocksCount: invalid operation string");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid operation string");
        }

    }
}
