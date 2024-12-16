package com.restock.controller;

import com.restock.service.RestockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestockController {
    private final RestockService restockService;

    public RestockController(RestockService restockService) {
        this.restockService = restockService;
    }

    //    재입고 알림 전송 api
    @PostMapping("/product/{productId}/notifications/re-stock")
    public ResponseEntity<Void> sendRestock(@PathVariable("productId") long productId) {
        restockService.sendByRestock(productId);
        return ResponseEntity.ok().build();
    }
//    재입고 알림 전송 api(manual)
//    @PostMapping("/admin/product/{productId}/notifications/re-stock")
//    public ResponseEntity<Void> manualSendRestock(@PathVariable("productId") long productId) {
//
//    }
}
