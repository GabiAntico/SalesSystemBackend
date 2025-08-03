package com.sales.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Join;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int cuantity;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
}
