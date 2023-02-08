package Order.DispatchService;

import Merchandising.MerchandiseService.Manga;
import User.AccountService.EndUser;

import java.util.Objects;

public class OrderRow {

    public OrderRow()
    {
        this.order = new Order();
        this.manga = new Manga(0);
        this.user = new EndUser();

    }

    public OrderRow(Order order, EndUser user, Manga manga, int quantity)
    {
        this.order = order;
        this.user = user;
        this.manga = manga;
        this.quantity = quantity;
    }

    public long getOrderID()
    {
        return this.order.getId();
    }

    public void setOrderId(long newOrderId)
    {
        this.order.setId(newOrderId);
    }

    public long getUserId()
    {
        return this.user.getId();
    }

    public void setUserId(long newUserId)
    {
        this.user.setId((int)newUserId);
    }

    public String getMangaName()
    {
        return this.manga.getName();
    }

    public void setMangaName(String newMangaName)
    {
        this.manga.setName(newMangaName);
    }

    public double getMangaPrice()
    {
        return this.manga.getPrice();
    }

    public void setMangaPrice(double newMangaPrice)
    {
        this.manga.setPrice(newMangaPrice);
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(int newQuantity)
    {
        this.quantity = newQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRow orderRow = (OrderRow) o;
        return quantity == orderRow.quantity && order.getId() == orderRow.order.getId()
                && user.getId() == orderRow.user.getId() && manga.getName().equals(orderRow.manga.getName()) &&
                manga.getPrice() == orderRow.manga.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, user, manga, quantity);
    }

    private Order order;
    private EndUser user;
    private Manga manga;
    private int quantity;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setUser(EndUser user) {
        this.user = user;
    }

    public boolean validateCreation()
    {
        if(this.order.getId() <= 0 || this.user.getId() <= 0 || this.manga.getName() == null ||
                this.manga.getName().trim().equals("") || this.manga.getPrice() <= 0 || this.quantity <= 0)
            return false;
        else
            return true;
    }
}
