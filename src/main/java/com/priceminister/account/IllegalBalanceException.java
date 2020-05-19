package com.priceminister.account;

public class IllegalBalanceException extends RuntimeException {

    private static final long serialVersionUID = -9204191749972551939L;

    public IllegalBalanceException(String message) {
        super(message);
    }

}
