package com.restock.service;

import com.restock.domain.product.Product;
import com.restock.domain.productNotificationHistory.ProductNotificationHistory;
import com.restock.domain.productNotificationHistory.ProductNotificationHistoryRepository;
import com.restock.domain.productUserNotification.ProductUserNotification;
import com.restock.domain.product.ProductRepository;
import com.restock.domain.productUserNotification.ProductUserNotificationRepository;
import com.restock.domain.productUserNotificationHistory.ProductUserNotificationHistoryRepository;
import com.restock.service.Redis.RedisService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RestockService {
    private final ProductRepository productRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;
    private final RedisService redisService;
    public RestockService(ProductRepository productRepository, ProductNotificationHistoryRepository productNotificationHistoryRepository, ProductUserNotificationRepository productUserNotificationRepository, ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository, RedisService redisService) {
        this.productRepository = productRepository;
        this.productNotificationHistoryRepository = productNotificationHistoryRepository;
        this.productUserNotificationRepository = productUserNotificationRepository;
        this.productUserNotificationHistoryRepository = productUserNotificationHistoryRepository;
        this.redisService = redisService;
    }

    @Transactional
    public void sendByRestock(long productId){
        Product product= productRepository.getProduct(productId).add();
//        재입고가 되면 재입고회차를 늘려준다.

        productRepository.save(product);
        log.info("제품테이블 재입고 : productId={},재입고회차:{}",productId,product.getRestockCount());

//        해당 재입고알림상태를 진행중으로 변경해준다.
        ProductNotificationHistory history = ProductNotificationHistory.inProgress(product);
        productNotificationHistoryRepository.save(history);
        log.info("제품 재입고히스토리 : productId={},재입고회차:{},재입고 알림상태:{}",history.getProductId(),history.getRestockCount(),history.getNotiStatus());

        sendByNotification(productId);
    }

    public void sendByNotification(long productId){
        Product product = productRepository.getProduct(productId);
        log.info("제품테이블: productId={},재입고회차:{},수량:{}",productId,product.getRestockCount(),product.getQuantity());
        List<ProductUserNotification> userList = productUserNotificationRepository.notiList(productId);
        log.info("제품 알림설정한 사용자 : productId:{},사용자 수:{} ",productId,userList.size());

//        제폼의 재고가 없어질때까지 유저들에게 알림을 보낸다.
//        알림받은사람은 무조건산다는 가정을 한다.
        ProductNotificationHistory history = new ProductNotificationHistory(product);
        for(ProductUserNotification user : userList){
            Long userId = user.getUserId();
            log.info("유저아이디:{}",user.getUserId());
            try{
                if(product.isSoldOut()){ //품절되면 soleout저장
                     history.cancelBySoldOut(userId);
                    productNotificationHistoryRepository.notiStatus(history); //상품별 재입고 알림 히스토리 상태변경
                    break;
                }
                redisService.saveList(product,userId); //레디스에 알림정보 저장
                 //수량감소
                product.decrease();
                productRepository.save(product);
                history.completed(userList.get(userList.size()-1).getUserId());
                productNotificationHistoryRepository.notiStatus(history);
            }catch(Exception e){
                log.error("알림전송에러"+e.getMessage());
                history.cancelByError(userId);
                productNotificationHistoryRepository.notiStatus(history);
                break;
            }
        }
    }
}
