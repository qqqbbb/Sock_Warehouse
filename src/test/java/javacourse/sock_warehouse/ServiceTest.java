package javacourse.sock_warehouse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private Service out;

    @Mock
    private Repository repository;

    @Test
    void testAddSocks() {
        SocksDTO socksDTO = new SocksDTO("red", 11, 1);
//        Socks expectedSocks = new Socks("red", 11, 1);
//        when(repository.save(any(Socks.class))).thenReturn(expectedSocks);
        ResponseEntity<?> responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        socksDTO = new SocksDTO("red", 22, 1);
        responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        socksDTO = new SocksDTO("green", 22, 1);
        responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testAddSocksWithInvalidParameters() {
        SocksDTO socksDTO = new SocksDTO(null, 1, 1);
        ResponseEntity<?> responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", -1, 1);
        responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", 101, 1);
        responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", 1, 0);
        responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", 1, -1);
        responseEntity = out.addSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testRemoveSocks() {
        Socks storedSocks = new Socks("red", 11, 5);
        SocksDTO socksDTO = new SocksDTO("red", 11, 1);
        when(repository.findByColorAndCotton(anyString(),  anyInt())).thenReturn(storedSocks);
        ResponseEntity<?> responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testRemoveSocksWithInvalidParameters() {
        SocksDTO socksDTO = new SocksDTO("", 11, 11);
//        Socks storedSocks = new Socks("red", 11, 11);
//        when(repository.findByColorAndCotton(anyString(), anyInt())).thenReturn(storedSocks);
        ResponseEntity<?> responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO(null, 11, 111);
        responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", 11, 111);
        responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("redd", 11, 11);
        responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", -1, 11);
        responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", 111, 11);
        responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", 11, 0);
        responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        socksDTO = new SocksDTO("red", 11, -1);
        responseEntity = out.removeSocks(socksDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetSocksCount() {
        String color = "red";
        String operator = "equal";
        int cotton = 11;
        int count = 5;
        Socks storedSocks = new Socks(color, cotton, count);
        List<Socks> storedSocksList = List.of(storedSocks);
        when(repository.findAllByColor(anyString())).thenReturn(storedSocksList);
        ResponseEntity<?> responseEntity = out.getSocksCount(color, operator, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(String.valueOf(count));

        when(repository.findAllByColor(anyString())).thenReturn(storedSocksList);
        responseEntity = out.getSocksCount( "  RED  ", operator, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(String.valueOf(count));

        when(repository.findAllByColor(anyString())).thenReturn(new ArrayList<>());
        responseEntity = out.getSocksCount("R", operator, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(String.valueOf(0));

        operator = "moreThan";
        cotton = 1;
        when(repository.findAllByColor(anyString())).thenReturn(storedSocksList);
        responseEntity = out.getSocksCount(color, operator, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(String.valueOf(count));

        operator = "lessThan";
        cotton = 22;
        when(repository.findAllByColor(anyString())).thenReturn(storedSocksList);
        responseEntity = out.getSocksCount(color, operator, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(String.valueOf(count));
    }

    @Test
    void testGetSocksCountWithInvalidParameters() {
        String color = "red";
        String operator = "equal";
        int cotton = 11;
        int count = 5;
        Socks storedSocks = new Socks(color, cotton, count);
        List<Socks> storedSocksList = List.of(storedSocks);

        ResponseEntity<?> responseEntity = out.getSocksCount(null, operator, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        responseEntity = out.getSocksCount("", operator, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        responseEntity = out.getSocksCount(color, null, cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        responseEntity = out.getSocksCount(color, "", cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        responseEntity = out.getSocksCount(color, operator, -1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        responseEntity = out.getSocksCount(color, operator, 111);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        when(repository.findAllByColor(anyString())).thenReturn(storedSocksList);
        responseEntity = out.getSocksCount(color, "EQUAL", cotton);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

}
