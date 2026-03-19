package com.gabriel.pss.payload;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
    private String itemName;
    private int quantity;
    private int price;
    private String uom;
}
