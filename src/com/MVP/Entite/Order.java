/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MVP.Entite;

/**
 *
 * @author Imén
 */
public class Order {
    private int id;
    private String payment_id;
    private String status;
    private float total;
    


public Order() {}

public Order(int id,float total,String status,String payment_id) {
        this.id = id;
        this.payment_id = payment_id;
        this.status = status;
        this.total = total;
        
    }



public Order(float total,String status,String payment_id) {
       
        this.payment_id = payment_id;
        this.status = status;
        this.total = total;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }



@Override
    public String toString() {
        return "Order{" + "id=" + id + ",payment_id=" +payment_id +  ", status=" +status +
", total=" + total + '}';
    }



}