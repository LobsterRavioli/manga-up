package Order.DispatchService.beans;

import java.util.Objects;

public class Courier {

    private long id;
    private String name;

    public Courier()
    {
        ;
    }

    public Courier(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return id == courier.id && Objects.equals(name, courier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
