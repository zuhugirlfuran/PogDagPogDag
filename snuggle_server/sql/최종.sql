drop database if exists ssafyfinal;
create database if not exists ssafyfinal;

use ssafyfinal;

-- 유저 테이블
CREATE TABLE `t_user` (
    `user_id` VARCHAR(100) primary key,
    `password` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(50) NOT NULL,
    `age` INT NOT NULL,
    `gender` ENUM('F', 'M') NOT NULL,
    `path` VARCHAR(255),
    `token` VARCHAR(255),
    `img` VARCHAR(255),
    `stamps` INT DEFAULT 0 
);


INSERT INTO t_user (user_id, password, nickname, age, gender, path, token, img, stamps) VALUES ('id 01', 'pass 01', 'name 01', 25, 'F', 'SSAFY', 'token', 'img', 0);


-- 카테고리 테이블
CREATE TABLE `t_category` (
    `c_id` INT AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(255) NOT NULL
);


INSERT INTO t_category (category_name) VALUES ('코바늘');
INSERT INTO t_category (category_name) VALUES ('대바늘');
INSERT INTO t_category (category_name) VALUES ('털실');
INSERT INTO t_category (category_name) VALUES ('목도리 뜨기');
INSERT INTO t_category (category_name) VALUES ('가방 뜨기');
INSERT INTO t_category (category_name) VALUES ('키링');
INSERT INTO t_category (category_name) VALUES ('부자재');
INSERT INTO t_category (category_name) VALUES ('DIY 키트');


-- 상품 테이블
CREATE TABLE `t_product` (
    `product_id` INT AUTO_INCREMENT PRIMARY KEY,
    `c_id` INT NOT NULL,
    `product_name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `img` VARCHAR(255),
    `like_count` INT DEFAULT 0 NOT NULL,
    FOREIGN KEY (`c_id`) REFERENCES `t_category`(`c_id`)
);


INSERT INTO t_product (c_id, product_name, price, img, like_count) VALUES (1, '푸딩거북이', 5000, 'img', 5);
INSERT INTO t_product (c_id, product_name, price, img, like_count) VALUES (1, '도토리를줍자', 7000, 'img', 7);
INSERT INTO t_product (c_id, product_name, price, img, like_count) VALUES (1, '도시락세트', 8000, 'img', 8);


-- 배송 주소 테이블
CREATE TABLE `t_address` (
    `address_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(15) NOT NULL,
    `is_default` ENUM('Y', 'N') DEFAULT 'Y',
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);


INSERT INTO t_address (user_id, address, phone, is_default) VALUES ('id 01', '구미 진평동', '010-1234-5678', 'Y');
INSERT INTO t_address (user_id, address, phone, is_default) VALUES ('id 01', '구미 인의동', '010-1234-5678', 'N');

-- 주문 테이블
CREATE TABLE `t_order` (
    `order_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `address_id` INT NOT NULL,
    `total_price` DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
    FOREIGN KEY (`address_id`) REFERENCES `t_address`(`address_id`)
);


INSERT INTO t_order (user_id, address_id, total_price) VALUES ('id 01', 1, 5000);
INSERT INTO t_order (user_id, address_id, total_price) VALUES ('id 01', 1, 7000);


-- 주문 상세 테이블
CREATE TABLE `t_order_detail` (
    `d_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `quantity` INT DEFAULT 1 NOT NULL,
    `order_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `completed` ENUM('Y', 'N') DEFAULT 'N',
    FOREIGN KEY (`order_id`) REFERENCES `t_order`(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `t_product`(`product_id`)
);


INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 1, 1);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 2, 3);


-- 스탬프 테이블
CREATE TABLE `t_stamp` (
    `s_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `order_id` INT NOT NULL,
    `quantity` INT DEFAULT 0,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
    FOREIGN KEY (`order_id`) REFERENCES `t_order`(`order_id`)
);


INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id 01', 1, 4);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id 01', 2, 1);


-- 댓글 테이블
CREATE TABLE `t_comment` (
    `comment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `user_id` VARCHAR(100) NOT NULL,
    `comment` TEXT NOT NULL,
    FOREIGN KEY (`product_id`) REFERENCES `t_product`(`product_id`),
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);


INSERT INTO t_comment (product_id, user_id, comment) VALUES (1, 'id 01', 'comment 01');
INSERT INTO t_comment (product_id, user_id, comment) VALUES (1, 'id 01', 'comment 02');


-- 알림 테이블
CREATE TABLE `t_notification` (
    `n_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);


INSERT INTO t_notification (user_id, title, content) VALUES ('id 01', 'title 01', 'content 01');
INSERT INTO t_notification (user_id, title, content) VALUES ('id 01', 'title 02', 'content 02');


-- 결제 테이블
CREATE TABLE `t_payment` (
    `payment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    FOREIGN KEY (`order_id`) REFERENCES `t_order`(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `t_product`(`product_id`)
);


INSERT INTO t_payment (order_id, product_id) VALUES (1,1);
INSERT INTO t_payment (order_id, product_id) VALUES (1,2);


-- 태깅 테이블
CREATE TABLE `t_tagging` (
    `tagging_id` VARCHAR(100) PRIMARY KEY,
    `video_src` VARCHAR(255) NOT NULL,
    `video_title` VARCHAR(255) NOT NULL,
    `video_content` TEXT NOT NULL
);


INSERT INTO t_tagging (tagging_id, video_src, video_title, video_content) VALUES ('taggin id 01', 'video src 01', 'video title 01', 'video content 01');
INSERT INTO t_tagging (tagging_id, video_src, video_title, video_content) VALUES ('taggin id 02', 'video src 02', 'video title 02', 'video content 02');


-- 즐겨찾기 테이블
CREATE TABLE `t_favorite` (
    `bookmark_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `tagging_id` VARCHAR(100) NOT NULL,
    `is_valid` ENUM('Y', 'N') DEFAULT 'N',
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
    FOREIGN KEY (`tagging_id`) REFERENCES `t_tagging`(`tagging_id`)
);


INSERT INTO t_favorite (user_id, tagging_id, is_valid) VALUES ('id 01', 'taggin id 01', 'Y');
INSERT INTO t_favorite (user_id, tagging_id, is_valid) VALUES ('id 01', 'taggin id 02', 'Y');


-- 좋아요 테이블
CREATE TABLE `t_like` (
    `like_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `product_id` INT NOT NULL,
    `like_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
    FOREIGN KEY (`product_id`) REFERENCES `t_product`(`product_id`)
);


INSERT INTO t_like (user_id, product_id) VALUES ('id 01', 1);
INSERT INTO t_like (user_id, product_id) VALUES ('id 01', 2);
INSERT INTO t_like (user_id, product_id) VALUES ('id 01', 3);

