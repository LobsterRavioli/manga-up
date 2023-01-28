package Order.DispatchService;

import Merchandising.MerchandiseService.Manga;
import User.AccountService.EndUser;

import java.util.Objects;

public class OrderRow {

    public OrderRow()
    {
        ;
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
        return quantity == orderRow.quantity && Objects.equals(order, orderRow.order) && Objects.equals(user, orderRow.user) && Objects.equals(manga, orderRow.manga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, user, manga, quantity);
    }

    private Order order;
    private EndUser user;
    private Manga manga;
    private int quantity;
}
