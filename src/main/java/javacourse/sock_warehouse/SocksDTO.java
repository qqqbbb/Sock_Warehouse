package javacourse.sock_warehouse;


public class SocksDTO {

    /**
     * color string
     */
    private String color;
    /**
     * cotton %
     */
    private int cottonPart;
    /**
     * quantity
     */
    private long quantity;

    public SocksDTO() {

    }

    public SocksDTO(String color, int cottonPart, long quantity) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(int cottonPart) {
        this.cottonPart = cottonPart;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SocksDTO {" +
                "color='" + color + '\'' +
                ", cottonPart=" + cottonPart +
                ", quantity=" + quantity +
                '}';
    }
}
