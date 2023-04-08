package com.yourcompany.tradingordersystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class TradingOrderVerticle extends AbstractVerticle {

  private OrderProcessor orderProcessor;
  private DbConnector dbConnector;

  @Override
  public void start(Promise<Void> startPromise) {
    dbConnector = new DbConnector(vertx);
    orderProcessor = new OrderProcessor(1024, dbConnector);
    Router router = Router.router(vertx);
    router.post("/order").handler(this::handleNewOrder);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8080");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }

  private void handleNewOrder(RoutingContext routingContext) {
    Order order = routingContext.getBodyAsJson().mapTo(Order.class);
    orderProcessor.submitOrder(order);
    routingContext.response().setStatusCode(201).end();
  }
}

