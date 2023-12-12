package com.ecom.inventoryService.controller;

import com.ecom.inventoryService.dto.InventoryRequest;
import com.ecom.inventoryService.dto.InventoryResponse;
import com.ecom.inventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public void stockEntry(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.stockEntry(inventoryRequest);
    }

    @GetMapping
    public List<InventoryResponse> stockAvailability(@RequestParam List<String> skuCodes){
        return inventoryService.stockAvailability(skuCodes);
    }

}
