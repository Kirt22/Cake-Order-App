package com.example.recordapplication.ViewRecordFiles;

public class subRvModelClass {
    String customerName, cakeFlavour, DeliveryDate, DeliveryTime, BakeryType;
    long OrderId;

    public subRvModelClass(String customerName, String cakeFlavour, String deliveryDate, String deliveryTime, long orderId, String BakeryType) {
        this.customerName = customerName;
        this.cakeFlavour = cakeFlavour;
        DeliveryDate = deliveryDate;
        DeliveryTime = deliveryTime;
        OrderId = orderId;
        this.BakeryType = BakeryType;
    }

    public String getBakeryType() {
        return BakeryType;
    }

    public void setBakeryType(String bakeryType) {
        BakeryType = bakeryType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCakeFlavour() {
        return cakeFlavour;
    }

    public void setCakeFlavour(String cakeFlavour) {
        this.cakeFlavour = cakeFlavour;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }
}
