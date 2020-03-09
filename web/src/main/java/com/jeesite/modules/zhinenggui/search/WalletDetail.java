package com.jeesite.modules.zhinenggui.search;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WalletDetail {
    private String userId;
    private String driverId;
    private double moneyGte;
    private double moneyLte;
    private int detailType;
    private Date createTimeGte;
    private Date createTimeLte;
    private String companyId;
    private String byUser;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public double getMoneyGte() {
        return moneyGte;
    }

    public void setMoneyGte(double moneyGte) {
        this.moneyGte = moneyGte;
    }

    public double getMoneyLte() {
        return moneyLte;
    }

    public void setMoneyLte(double moneyLte) {
        this.moneyLte = moneyLte;
    }

    public int getDetailType() {
        return detailType;
    }

    public void setDetailType(int detailType) {
        this.detailType = detailType;
    }

    public Date getCreateTimeGte() {
        return createTimeGte;
    }

    public void setCreateTimeGte(Date createTimeGte) {
        this.createTimeGte = createTimeGte;
    }

    public Date getCreateTimeLte() {
        return createTimeLte;
    }

    public void setCreateTimeLte(Date createTimeLte) {
        this.createTimeLte = createTimeLte;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getByUser() {
        return byUser;
    }

    public void setByUser(String byUser) {
        this.byUser = byUser;
    }
}
