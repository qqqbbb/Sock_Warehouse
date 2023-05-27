package javacourse.sock_warehouse;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity()
public final class Socks {

    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * color string
     */
    private String color;
    /**
     * cotton %
     */
    private int cotton;
    /**
     * quantity
     */
    private long quantity;

    public Socks(String color, int cotton, long quantity) {
        this.color = color;
        this.cotton = cotton;
        this.quantity = quantity;
    }

    public Socks() {

    }

    public String getColor() {
        return color;
    }

    public int getCotton() {
        return cotton;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return id == socks.id && getCotton() == socks.getCotton() && Objects.equals(getColor(), socks.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getColor(), getCotton());
    }

    @Override
    public String toString() {
        return "Socks {" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", cotton=" + cotton +
                ", quantity=" + quantity +
                '}';
    }
}
