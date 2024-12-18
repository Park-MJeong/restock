# **📖 프로젝트 소개**

상품이 재입고 되었을 때 ,재입고 알림을 설정한 유저들에게 재입고 알림을 보내줍니다.

---

## 기술 스택

- Java
- Spring Boot 3.x
- Spring Boot Data JPA
- MySQL

---

## **모델링**

**Product(상품)**

| id(PK) | BIGINT | 상품 아이디 |
| --- | --- | --- |
| restockCount | INTEGER | 재입고 회차 |
| quantity | BIGINT | 재입고 상태 |

**ProductNotificationHistory(상품별 재입고 알림 히스토리)**

| id | BIGINT | 해당 테이블 고유 식별자 |
| --- | --- | --- |
| productId(FK) | BIGINT | 상품 아이디 |
| restockCount | INTEGER | 재입고 회차 |
| notiStatus | VARCHAR(50) | 재입고 알림 발송 상태 |
| userId | BIGINT | 마지막 발송 유저 아이디 |

**ProductUserNotification(상품별 재입고 알림을 설정한 유저)**

| id | BIGINT | 해당 테이블 고유 식별자 |
| --- | --- | --- |
| productId(FK) | BIGINT | 상품 아이디 |
| userId | BIGINT | 유저 아이디 |
| pushStatus | VARCHAR(5) | 활성화 여부 |
| createdAt | TIMESTAMP | 생성 날짜 |
| updatedAt | TIMESTAMP | 수정 날짜 |

**ProductUserNotificationHistory(상품+유저별 알림 히스토리)**

| id | BIGINT | 해당 테이블 고유 식별자 |
| --- | --- | --- |
| product(FK) | BIGINT | 상품 아이디 |
| userId | BIGINT | 유저 아이디 |
| restockCount | INTEGER | 재입고 회차 |
| sendAt | TIMESTAMP | 발송 날짜 |

---

- 상품 재입고
    - 상품테이블  :  재입고 회차 +1, 재입고 상태 +10  (재고추가로직이 없어서 강제로 우선 추가해줌)
    - 상품별 재입고 알림 히스토리 :
    - 상품별 재입고 알림을 설정한 유저 :  수정날짜변경, 활성화여부 N
    - 상품+유저별 알림 히스토리 : 재입고회차,발송 날짜

---

## **기술적 고민 및 구현**

- 복합인덱스의 필요성 :  쿼리의 검색을 최적화, 데이터의 조회 속도를 향상 시키위해서 사용.
    
    재입고알림을 설정한 유저순서대로 메세지를 전송해야한다는 조건이 있습니다.
    
    - **ProductUserNotification(상품별 재입고 알림을 설정한 유저)테이블**에서  항상 상품아이디로 필터링 후 수정날짜별로 정렬해야 하므로 ****복합인덱스를 설정해주면 성능이 향상될 수 있다고 생각합니다.
    - where와 order조건으로 사용되는 **상품아이디**와 **수정날짜를** 복합인덱스로 걸어줍니다
    - 활성화여부는 해당 조건에서 모두 yes로 주어지므로 생략하여 생각하였습니다
    - 
    
    <aside>
    
    CREATE INDEX idx_product_user_notification
    ON ProductUserNotification (productId, updatedAt);
    
    </aside>
<img width="887" alt="KakaoTalk_20241216_233012077" src="https://github.com/user-attachments/assets/05cc9757-0e5a-42d2-a604-dd4444c0a292" />

- 1초에500개의 알림전송 : 알림전송되면 테이블에 해당 내역을 저장해야 합니다. 
  - 알림이 전송될때마다 쿼리문이 작동하는것 보다 캐시에 저장해서 한번에 테이블에 저장하면 처리속도가 빨라집니다. 
  - redis에 먼저 값을 저장후 주기적으로 리스트에 모아서 sql로 쿼리를 1번만 보내도록 코드를 구현하였습니다.
