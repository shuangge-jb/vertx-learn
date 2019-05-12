package com.guojb.vertxDemo;

import com.guojb.error.Error;
import com.guojb.error.ErrorCode;
import com.guojb.error.StatusCode;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class MyFirstVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        Router router = Router.router(vertx);
        router.route("/v1.0/features").handler(this::features).failureHandler(this::failure);
        router.route("/v1.0/vpcs").handler(operation()).failureHandler(this::failure);
        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    private void features(RoutingContext routingContext) {
        List<String> list = routingContext.queryParam("engine");
        if (!list.get(0).equals("kafka") && !list.get(0).equals("rabbitmq")) {
            routingContext.response().setStatusCode(StatusCode.INVALID_PARAM).end(Json.encodePrettily(new Error(ErrorCode.INVALID_ENGINE, "invalid param.")));
        } else {
            String str = Json.encodePrettily(new Result());
            routingContext
                    .response()
                    .setStatusCode(200)
                    .end(str);
        }
    }

    private Handler<RoutingContext> operation(){

        return Future.<Message<String>>future(f ->
                vertx.eventBus().send("http://localhost:3066/vpc/v1.0/vpcs", "", Future.future())
        ).compose((msg) ->
                Future.<Message<String>>future(f ->
                        vertx.eventBus().send("http://localhost:3066/vpc/v1.0/subnets", msg.body(), f)
                )
        ).compose((msg) ->
                Future.<Message<String>>future(f ->
                        vertx.eventBus().send("http://localhost:3066/vpc/v1.0/security-groups", msg.body(), f)
                )
        ).setHandler((res) -> {
            if (res.failed()) {
                //deal with exception
                return;
            }
            //deal with the result
        });
    }
    private void failure(RoutingContext routingContext) {
        routingContext.response().setStatusCode(routingContext.statusCode()).end();
    }
}
