package com.enjin.enjincoin.spigot_framework.player;

import com.enjin.enjincoin.sdk.model.service.tokens.Token;

import java.math.BigDecimal;

public class TokenData {

    // ID of the token
    private String id;

    // Wallet Balance from the TP (max balance)
    private BigDecimal balance;

    // Amount available for in-game checkout
    private int checkedout = 0;

    public TokenData(Token token) {
        this.id = token.getTokenId();
        this.balance = token.getBalance();
    }

    public String getId() {
        return this.id;
    }

    public int getCheckedOut() {
        return this.checkedout;
    }

    public void setCheckedOut(int checkedOut) {
        this.checkedout = checkedOut;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public BigDecimal addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        return this.balance;
    }
}
