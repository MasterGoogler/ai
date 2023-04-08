
package com.yourcompany.tradingordersystem;

import io.vertx.core.Vertx;

public class OrderProcessor {

  private final RingBuffer<Order> ringBuffer;
  private final DbConnector dbConnector;
  private final Vertx vertx;

  public OrderProcessor(int bufferSize, DbConnector dbConnector) {
    this.vertx = Vertx.vertx();
    this.ringBuffer = new RingBuffer<>(bufferSize);
    this.dbConnector = dbConnector;
    startProcessing();
  }

  private void startProcessing() {
    vertx.setPeriodic(1L, id -> {
      Order order = ringBuffer.read();
      if (order != null) {
        dbConnector.insertOrder(order);
      }
    });
  }
  
  public void submitOrder(Order order) {
    ringBuffer.write(order);
  }
}


