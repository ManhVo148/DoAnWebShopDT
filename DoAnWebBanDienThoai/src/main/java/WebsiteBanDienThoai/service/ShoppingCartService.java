package WebsiteBanDienThoai.service;

import WebsiteBanDienThoai.entity.CartItem;

import java.util.Collection;

public interface ShoppingCartService {
    void add (CartItem newItem);
    void remove (int id);
    CartItem update (int productID, int quantity);
    void clear();
    double getAmount ();
    int getCount();
    Collection<CartItem> getAllItems();

}
