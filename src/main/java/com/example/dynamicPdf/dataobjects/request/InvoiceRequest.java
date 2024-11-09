package com.example.dynamicPdf.dataobjects.request;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class InvoiceRequest {

    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;

    @Getter
    @ToString
    public static class Item{
        private String name;
        private String quantity;
        private Double rate;
        private Double amount;

        @Override
        public int hashCode() {
            return Objects.hash(name, quantity, rate, amount);
        }
    }

    @Override
    public int hashCode() {
        ArrayList<Object> hashObjects = new ArrayList<>();
        hashObjects.addAll(Arrays.asList(seller, sellerGstin, sellerAddress, buyer, buyerGstin, buyerAddress));
        hashObjects.addAll(items);

        return Objects.hash(hashObjects);
    }
}
