package javacourse.sock_warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Repository  extends JpaRepository<Socks, Integer> {

    public Socks findByColorAndCotton(String color, int cotton);

    public List<Socks> findAllByColor(String color);
}
