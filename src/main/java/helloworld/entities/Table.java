package helloworld.entities;

public class Table extends BaseEntity {

    private String name;

    private Integer seatCount;

    private Boolean orderableElectronically = Boolean.TRUE;

    public Table(String name, int seatCount) {
        this.name = name;
        this.seatCount = seatCount;
        this.orderableElectronically = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Boolean getOrderableElectronically() {
        return orderableElectronically;
    }

    public String getFormattedOrderableElectronically() {
        return orderableElectronically ? "ja" : "nein";
    }

    public void setOrderableElectronically(Boolean orderableElectronically) {
        this.orderableElectronically = orderableElectronically;
    }
}