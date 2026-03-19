package com.gabriel.pss.payload;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    private int id;
    private String name;
    private int stockQuantity;
}
