-- Product(상품)
CREATE TABLE Product (
                         id BIGINT PRIMARY KEY,                     -- 상품 아이디
                         restockCount INTEGER NOT NULL DEFAULT 0,   -- 재입고 회차
                         quantity BIGINT    -- 재입고 상태
);

-- ProductNotificationHistory(상품별 재입고 알림 히스토리)
CREATE TABLE ProductNotificationHistory (
                                            id BIGINT PRIMARY KEY,                      -- 테이블 고유 식별자
                                            productId BIGINT NOT NULL,                  -- 상품 아이디 (FK)
                                            restockCount INTEGER NOT NULL,              -- 재입고 회차
                                            notiStatus VARCHAR(50),                     -- 재입고 알림 발송 상태
                                            userId BIGINT,                              -- 마지막 발송 유저 아이디
                                            FOREIGN KEY (productId) REFERENCES Product(id) ON DELETE CASCADE
);

-- ProductUserNotification(상품별 재입고 알림을 설정한 유저)
CREATE TABLE ProductUserNotification (
                                         id BIGINT PRIMARY KEY,                             -- 테이블 고유 식별자
                                         productId BIGINT NOT NULL,                         -- 상품 아이디 (FK)
                                         userId BIGINT NOT NULL,                            -- 유저 아이디
                                         pushStatus VARCHAR(5) DEFAULT 'Y'
                                             CHECK ( pushStatus IN('Y','N') ),           -- 활성화 여부
                                         createdAt TIMESTAMP NOT NULL,                      -- 생성 날짜
                                         updatedAt TIMESTAMP NOT NULL,                     -- 수정 날짜
                                         FOREIGN KEY (productId) REFERENCES Product(id) ON DELETE CASCADE
);

-- ProductUserNotificationHistory(상품+유저별 알림 히스토리)
CREATE TABLE ProductUserNotificationHistory (
                                                id BIGINT PRIMARY KEY,                      -- 테이블 고유 식별자
                                                product BIGINT NOT NULL,                    -- 상품 아이디 (FK)
                                                userId BIGINT NOT NULL,                     -- 유저 아이디
                                                restockCount INTEGER NOT NULL,              -- 재입고 회차
                                                sendAt TIMESTAMP NOT NULL,                  -- 발송 날짜
                                                FOREIGN KEY (product) REFERENCES Product(id) ON DELETE CASCADE
);

CREATE INDEX idx_product_user_notification
    ON ProductUserNotification (productId, updatedAt);
