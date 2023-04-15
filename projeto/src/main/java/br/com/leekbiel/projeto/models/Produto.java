package br.com.leekbiel.projeto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produtos")
public class Produto {
    
    //attributes
    @Id // primary key
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(name = "name", nullable = false, length = 400)
    private String name;

    @Column(name = "details", nullable = true, length = 400)
    private String details;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    //constructors
    public Produto(){}

    public Produto(String name, String details, Double price, Integer quantity){
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
    }


    
    //getters and setters
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    //toString
    @Override
    public String toString() {
        return "Produtos [id=" + id + ", name=" + name + ", details=" + details + ", price=" + price + ", quantity="
                + quantity + "]";
    }
}
