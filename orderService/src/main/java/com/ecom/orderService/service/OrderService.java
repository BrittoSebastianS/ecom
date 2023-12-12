package com.ecom.orderService.service;

import com.ecom.orderService.dto.InventoryResponse;
import com.ecom.orderService.dto.OrderLineItemsDto;
import com.ecom.orderService.dto.OrderRequest;
import com.ecom.orderService.model.Order;
import com.ecom.orderService.model.OrderLineItems;
import com.ecom.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(orderLineItemsDto -> mapToOrderLineItems(orderLineItemsDto))
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class).block();

        Boolean stockAvailability = Arrays.stream(inventoryResponsesArray).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());

        if(stockAvailability) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product not in stock, Please try again");
        }

    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = OrderLineItems.builder()
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .price(orderLineItemsDto.getPrice())
                .build();

        return orderLineItems;
    }

}
