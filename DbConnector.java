package com.yourcompany.tradingordersystem;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

public class DbConnector {

  private final PgPool pgPool;

  public DbConnector(Vertx vertx) {
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("your_host")
      .setDatabase("your_database")
      .setUser("your_user")
      .setPassword("your_password");

    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    pgPool = PgPool.pool(vertx, connectOptions, poolOptions);
  }

  public void insertOrder(Order order) {
    String sql = "INSERT INTO orders (order_id, price, quantity) VALUES ($1, $2, $3)";
    pgPool.preparedQuery(sql)
      .execute(Tuple.of(order.getOrderId(), order.getPrice(), order.getQuantity()), ar -> {
        if (ar.succeeded()) {
          System.out.println("Inserted order " + order.getOrderId());
        } else {
          System.out.println("Failed to insert order: " + ar.cause().getMessage());
        }
      });
  }
}

