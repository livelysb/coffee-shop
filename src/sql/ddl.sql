CREATE DATABASE `coffee`;
GRANT ALL PRIVILEGES ON `coffee`.* TO 'coffee'@'localhost' IDENTIFIED BY 'coffee';
FLUSH PRIVILEGES;



DROP TABLE IF EXISTS `coffee`.`menu`;
CREATE TABLE `coffee`.`menu`
(
    `menu_id`    BIGINT      NOT NULL AUTO_INCREMENT COMMENT '메뉴번호',
    `menu_name`  VARCHAR(20) NOT NULL COMMENT '메뉴명',
    `menu_price` INT         NOT NULL COMMENT '메뉴금액',
    PRIMARY KEY (`menu_id`)
);

DROP TABLE IF EXISTS `coffee`.`order`;
CREATE TABLE `coffee`.`order`
(
    `order_id`    BIGINT      NOT NULL AUTO_INCREMENT COMMENT '주문번호',
    `user_id`     VARCHAR(20) NOT NULL COMMENT '유저 아이디',
    `menu_id`     BIGINT      NOT NULL COMMENT '주문메뉴번호',
    `menu_name`   VARCHAR(20) NOT NULL COMMENT '주문메뉴명',
    `order_price` BIGINT      NOT NULL COMMENT '주문금액',
    `ordered_at`  DATETIME(6) NOT NULL COMMENT '주문일시',
    PRIMARY KEY (`order_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_menu_id` (`menu_id`),
    KEY `idx_ordered_at` (`ordered_at`)
);

DROP TABLE IF EXISTS `coffee`.`user`;
CREATE TABLE `coffee`.`user`
(
    `user_seq`   BIGINT      NOT NULL AUTO_INCREMENT COMMENT '유저 시퀀스',
    `user_id`    VARCHAR(20) NOT NULL COMMENT '유저 아이디',
    `user_point` BIGINT      NOT NULL DEFAULT 0 COMMENT '유저 포인트',
    PRIMARY KEY (`user_seq`),
    UNIQUE KEY (`user_id`)
);

DROP TABLE IF EXISTS `coffee`.`point_transaction`;
CREATE TABLE `coffee`.`point_transaction`
(
    `point_transaction_id` BIGINT      NOT NULL AUTO_INCREMENT COMMENT '포인트 이력 번호',
    `user_seq`             BIGINT      NOT NULL COMMENT '유저 아이디',
    `point`                BIGINT      NOT NULL COMMENT '충전/사용 포인트',
    `transaction_type`     VARCHAR(10) COMMENT '포인트 이력 구분',
    `transacted_at`        DATETIME(6) NOT NULL COMMENT '포인트 이력 일시',
    PRIMARY KEY (`point_transaction_id`),
    KEY `idx_user_seq` (`user_seq`)
);