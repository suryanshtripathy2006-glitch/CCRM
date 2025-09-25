package edu.ccrm.domain;

public class MaxCreditLimitExceededException extends RuntimeException {
    public MaxCreditLimitExceededException(String message) {
        super(message);
    }
}