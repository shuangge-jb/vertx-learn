package com.guojb.vertxDemo;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class HelloWorld {
    public static void main(String[] args) {


        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(MyFirstVerticle.class.getName());

        System.out.println("Server started!");
    }
}
