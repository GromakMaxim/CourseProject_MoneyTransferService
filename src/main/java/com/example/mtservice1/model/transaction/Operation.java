package com.example.mtservice1.model.transaction;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Operation {
    @NotNull
    private String code;

    @Min(100)
    private int operationId;

    public Operation() {
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "code='" + code + '\'' +
                ", operationId=" + operationId +
                '}';
    }
}
