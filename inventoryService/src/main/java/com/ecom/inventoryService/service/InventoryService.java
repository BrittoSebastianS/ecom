package com.ecom.inventoryService.service;

import com.ecom.inventoryService.dto.InventoryRequest;
import com.ecom.inventoryService.dto.InventoryResponse;
import com.ecom.inventoryService.model.Inventory;
import com.ecom.inventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public void stockEntry(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantityAvailable(inventoryRequest.getQuantityAvailable())
                .build();

        inventoryRepository.save(inventory);
    }

    public List<InventoryResponse> stockAvailability(List<String> skuCodes) {
        List<Inventory> inventories = inventoryRepository.findBySkuCodesIn(skuCodes);

        return inventories.stream().map(inventory -> mapToInventoryResponse(inventory)).toList();
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        InventoryResponse inventoryResponse = InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantityAvailable()>0)
                .build();

        return inventoryResponse;
    }
}
