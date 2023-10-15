package com.example.recordapplication.ViewRecordFiles;

public class OrderDetailsModel {

    private long OrderId;
    // Customer details
    private String customerName;
    private long phNumber;
    private int cakePrice;
    private String DeliveryDate;
    private String DeliveryTime;
    private String bakeryType;
    // Cake details
    private String cakeFlavour;
    private int cakeWeight;
    private String cakeMsg;
    private Boolean isThemeCake;
    private String themeCakeDescription;

    public OrderDetailsModel(long orderId, String customerName, long phNumber, int cakePrice, String deliveryDate, String deliveryTime, String cakeFlavour, int cakeWeight, String cakeMsg, Boolean isThemeCake, String themeCakeDescription, String bakeryType) {
        this.OrderId = orderId;
        this.customerName = customerName;
        this.phNumber = phNumber;
        this.cakePrice = cakePrice;
        this.DeliveryDate = deliveryDate;
        this.DeliveryTime = deliveryTime;
        this.cakeFlavour = cakeFlavour;
        this.cakeWeight = cakeWeight;
        this.cakeMsg = cakeMsg;
        this.isThemeCake = isThemeCake;
        this.themeCakeDescription = themeCakeDescription;
        this.bakeryType = bakeryType;
    }

    @Override
    public String toString() {
        return "OrderDetailsModel{" +
                "OrderId=" + OrderId +
                ", customerName='" + customerName + '\'' +
                ", phNumber=" + phNumber +
                ", cakePrice=" + cakePrice +
                ", DeliveryDate='" + DeliveryDate + '\'' +
                ", DeliveryTime='" + DeliveryTime + '\'' +
                ", bakeryType='" + bakeryType + '\'' +
                ", cakeFlavour='" + cakeFlavour + '\'' +
                ", cakeWeight=" + cakeWeight +
                ", cakeMsg='" + cakeMsg + '\'' +
                ", isThemeCake=" + isThemeCake +
                ", themeCakeDescription='" + themeCakeDescription + '\'' +
                '}';
    }

    public String getBakeryType() {
        return bakeryType;
    }

    public void setBakeryType(String bakeryType) {
        this.bakeryType = bakeryType;
    }

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(long phNumber) {
        this.phNumber = phNumber;
    }

    public int getCakePrice() {
        return cakePrice;
    }

    public void setCakePrice(int cakePrice) {
        this.cakePrice = cakePrice;
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

    public String getCakeFlavour() {
        return cakeFlavour;
    }

    public void setCakeFlavour(String cakeFlavour) {
        this.cakeFlavour = cakeFlavour;
    }

    public int getCakeWeight() {
        return cakeWeight;
    }

    public void setCakeWeight(int cakeWeight) {
        this.cakeWeight = cakeWeight;
    }

    public String getCakeMsg() {
        return cakeMsg;
    }

    public void setCakeMsg(String cakeMsg) {
        this.cakeMsg = cakeMsg;
    }

    public Boolean getThemeCake() {
        return isThemeCake;
    }

    public void setThemeCake(Boolean themeCake) {
        isThemeCake = themeCake;
    }

    public String getThemeCakeDescription() {
        return themeCakeDescription;
    }

    public void setThemeCakeDescription(String themeCakeDescription) {
        this.themeCakeDescription = themeCakeDescription;
    }
}