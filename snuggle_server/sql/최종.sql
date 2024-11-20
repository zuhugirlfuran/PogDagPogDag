drop database if exists ssafyfinal;
create database if not exists ssafyfinal;

use ssafyfinal;

-- 유저 테이블
CREATE TABLE `t_user` (
    `user_id` VARCHAR(100) primary key,
    `password` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(50) NOT NULL,
    `age` INT NOT NULL,
    `gender` CHAR(1) NOT NULL CHECK (gender IN ('Y', 'N')),
    `path` VARCHAR(255),
    `token` VARCHAR(255),
    `img` VARCHAR(255),
    `stamps` INT DEFAULT 0 
);

-- 카테고리 테이블
CREATE TABLE `t_category` (
    `c_id` INT AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(255) NOT NULL
);

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

-- 주문 테이블
CREATE TABLE `t_order` (
    `order_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `total_price` DECIMAL(10, 2) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(15) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);

-- 주문 상세 테이블
CREATE TABLE `t_order_detail` (
    `d_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `user_id` VARCHAR(100) NOT NULL,
    `order_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `completed` CHAR(1) CHECK (completed IN ('Y', 'N')) DEFAULT 'N',
    FOREIGN KEY (`order_id`) REFERENCES `t_order`(`order_id`),
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);

-- 스탬프 테이블
CREATE TABLE `t_stamp` (
    `s_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `order_id` INT NOT NULL,
    `quantity` INT DEFAULT 0,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
    FOREIGN KEY (`order_id`) REFERENCES `t_order`(`order_id`)
);

-- 댓글 테이블
CREATE TABLE `t_comment` (
    `comment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `user_id` VARCHAR(100) NOT NULL,
    `comment` TEXT NOT NULL,
    FOREIGN KEY (`product_id`) REFERENCES `t_product`(`product_id`),
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);

-- 알림 테이블
CREATE TABLE `t_notification` (
    `n_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);

-- 결제 테이블
CREATE TABLE `t_payment` (
    `payment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    FOREIGN KEY (`order_id`) REFERENCES `t_order`(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `t_product`(`product_id`)
);

-- 태깅 테이블
CREATE TABLE `t_tagging` (
    `tagging_id` VARCHAR(100) AUTO_INCREMENT PRIMARY KEY,
    `video_src` VARCHAR(255) NOT NULL,
    `video_title` VARCHAR(255) NOT NULL,
    `video_content` TEXT NOT NULL
);

-- 즐겨찾기 테이블
CREATE TABLE `t_favorite` (
    `bookmark_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `tagging_id` INT NOT NULL,
    `is_valid` CHAR(1) CHECK (is_valid IN ('Y', 'N')) DEFAULT 'N',
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
	FOREIGN KEY (`tagging_id`) REFERENCES `t_tagging`(`tagging_id`)
);

-- 좋아요 테이블
CREATE TABLE `t_like` (
    `like_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `product_id` INT NOT NULL,
    `like_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
    FOREIGN KEY (`product_id`) REFERENCES `t_product`(`product_id`)
);

