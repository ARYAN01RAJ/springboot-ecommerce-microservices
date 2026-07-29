package com.example.gateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayRouteConfiguration {
    // 1. ITEM SERVICE DOCS
    @Bean
    public RouterFunction<ServerResponse> itemDocsRoute() {
        return GatewayRouterFunctions.route("item-docs-route")
                .route(path("/item-service/v3/api-docs"), HandlerFunctions.http())
                .filter(FilterFunctions.stripPrefix(1))
                .filter(LoadBalancerFilterFunctions.lb("ITEM-SERVICE")) // Ensure this matches your Eureka name
                .build();
    }

    // 2. INVENTORY SERVICE DOCS
    @Bean
    public RouterFunction<ServerResponse> inventoryDocsRoute() {
        return GatewayRouterFunctions.route("inventory-docs-route")
                .route(path("/inventory-service/v3/api-docs"), HandlerFunctions.http())
                .filter(FilterFunctions.stripPrefix(1))
                .filter(LoadBalancerFilterFunctions.lb("INVENTORY-SERVICE"))
                .build();
    }

    // 3. ORDER SERVICE DOCS
    @Bean
    public RouterFunction<ServerResponse> orderDocsRoute() {
        return GatewayRouterFunctions.route("order-docs-route")
                .route(path("/order-service/v3/api-docs"), HandlerFunctions.http())
                .filter(FilterFunctions.stripPrefix(1))
                .filter(LoadBalancerFilterFunctions.lb("ORDER-SERVICE"))
                .build();
    }

    // 4. NOTIFICATION SERVICE DOCS
    @Bean
    public RouterFunction<ServerResponse> notificationDocsRoute() {
        return GatewayRouterFunctions.route("notification-docs-route")
                .route(path("/notification-service/v3/api-docs"), HandlerFunctions.http())
                .filter(FilterFunctions.stripPrefix(1))
                .filter(LoadBalancerFilterFunctions.lb("NOTIFICATION-SERVICE"))
                .build();
    }
}