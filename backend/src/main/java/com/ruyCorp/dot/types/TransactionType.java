package com.ruyCorp.dot.types;

public class TransactionType {

  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    if(type != "receita" || type != "despesa") {
      this.type = "despesa";
      return;
    }
    this.type = type;
  }

  public static TransactionType addTransaction(String type) {
    TransactionType Transaction = new TransactionType();
    Transaction.setType(type);
    return Transaction;
  }
}
