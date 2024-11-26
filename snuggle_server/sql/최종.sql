DROP DATABASE IF EXISTS ssafyfinal;
CREATE DATABASE IF NOT EXISTS ssafyfinal;
USE ssafyfinal;


-- 유저 테이블
CREATE TABLE `t_user` (
    `user_id` VARCHAR(100) PRIMARY KEY,
    `password` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(50) NOT NULL,
    `age` INT NOT NULL,
    `gender` ENUM('F', 'M') NOT NULL,
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
    `price` INT NOT NULL,
    `content` VARCHAR(255),
    `img` VARCHAR(255),
    `like_count` INT DEFAULT 0,
    FOREIGN KEY (`c_id`) REFERENCES `t_category`(`c_id`)
);

-- 배송 주소 테이블
CREATE TABLE `t_address` (
    `address_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `user_name` VARCHAR(30) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(15) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);

-- 주문 테이블
CREATE TABLE `t_order` (
    `order_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `address_id` INT NOT NULL,
    `total_price` DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`),
    FOREIGN KEY (`address_id`) REFERENCES `t_address`(`address_id`)
);

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
    `channel` VARCHAR(30) NOT NULL,
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
    `tagging_id` VARCHAR(100) PRIMARY KEY,
    `video_src` VARCHAR(255) NOT NULL,
    `video_title` VARCHAR(255) NOT NULL,
    `video_content` TEXT NOT NULL,
    `video_like` INT DEFAULT 0
);

-- 즐겨찾기 테이블
CREATE TABLE `t_favorite` (
    `favorite_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `tagging_id` VARCHAR(100) NOT NULL,
    `is_valid` ENUM('Y', 'N') DEFAULT 'Y',
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

-- 쿠폰 테이블
CREATE TABLE `t_coupon` (
    `coupon_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(100) NOT NULL,
    `coupon_name` VARCHAR(100),
    `coupon_start` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `coupon_end` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `coupon_discount` DOUBLE,
    `coupon_use` BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (`user_id`) REFERENCES `t_user`(`user_id`)
);


INSERT INTO t_user (user_id, password, nickname, age, gender, path, token, img, stamps) VALUES ('id 01', 'pass 01', 'name 01', 25, 'F', 'SSAFY', 'token', 'img', 0);
INSERT INTO t_user (user_id, password, nickname, age, gender, path, token, img, stamps) VALUES ('id 02', 'pass 02', 'name 02', 22, 'F', 'SSAFY', 'token2', 'img', 10);
select * from t_user;

INSERT INTO t_category (category_name) VALUES ('뜨개실');
INSERT INTO t_category (category_name) VALUES ('DIY 키트');
INSERT INTO t_category (category_name) VALUES ('도서&도안');


-- 여기부터 실
INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '100% 순면콘사 대용량 콘사', 2000, "각종 소품, 및 매트, 가방 등 목화면으로 세탁할수록 더 밝아지는 100% 면사 입니다.", "https://url.kr/m4gnfg");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '블루밍코튼 미니볼 10+1', 2500, "포인트로 소량만 필요할 때 \n 다양한 색상을 쓰고 싶을 때 \n 33g 미니볼 출시", "https://url.kr/ckyiaz");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '알파카 키드모헤어', 45000, "가벼우면서 보온성이 좋아서 가디건, 모자, 머플러 등 다양한 작품에 잘 어울립니다.", "https://github.com/rmfosem613/save_img/raw/main/wool_3.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '롱그라데이션 담당사', 1000, "귀여운 통 그라데이션 미니볼 출시! \n 작은 인형이나 키링, 쁘띠목도리 뜨기 좋은 양이예요.", "https://github.com/rmfosem613/save_img/raw/main/wool_4.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '멜란지 키드모헤어', 10000, "배색뜨기 좋은 귀여운 미니볼 \n 가벼우면서 보온성이 좋아서 가디건, 모자, 머플러 등 다양한 작품에 잘 어울립니다.", "https://github.com/rmfosem613/save_img/raw/main/wool_5.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '극세사 블랙 수면사', 6500, "가볍고 폭신한 수면사로 가장 인기많은 블랙 색상 콘사입니다.", "https://github.com/rmfosem613/save_img/raw/main/wool_6.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '화이트블루 이태리 린넨실', 2000, "사각사각 매끄러운 느낌의 리투아니아 린넨 \n 의류나 소품뜨기 좋은 실 입니다. \n 초기재고가 적어 품절시 재입고가 어렵습니다.", "https://github.com/rmfosem613/save_img/raw/main/wool_7.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '슬림 메탈릭얀 의류용 얇은실', 3000, "기존 메탈릭얀들보다 가는 버전으로 레이스나 의류를 뜨기에도 좋고 은은하게 합사용으로 써도 좋은 굵기예요.", "https://github.com/rmfosem613/save_img/raw/main/wool_8.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '반짝이 골드 롱 날개사', 1500, "넉넉한 용량으로 바란스, 러그등의 큰 대형 작품에도 부담없이 사용하실 수 있어요!", "https://github.com/rmfosem613/save_img/raw/main/wool_9.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (1, '도트 팬시얀 50g 10+1 특수사', 2500, "합사하여 사용시 따로 모양을 내지 않아도 자연스럽고 예쁜 컬러와 편물을 만들어주는 슬러브사 입니다.", "https://url.kr/7fkhyn");

-- 여기부터 키트
INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '푸딩한접시', 6500, "달콤한 푸딩을 닮은 아기자기한 소품으로, 공간에 따뜻한 감성을 더해줍니다. 초보자도 쉽게 따라 할 수 있어 직접 만드는 즐거움을 느껴보세요!", "https://url.kr/zrdzk9");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '푸딩 거북', 7500, "달콤한 푸딩처럼 귀여운 거북이를 만들어 보세요! 초보자도 쉽게 따라 할 수 있는 구성과 도안으로, 만들면서 힐링과 성취감을 동시에 느낄 수 있습니다.", "https://url.kr/2o2mga");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '따끈따근 주먹밥', 4500, "포근한 뜨개실로 따뜻한 주먹밥 소품을 완성해보세요. 손쉽게 따라 할 수 있는 DIY 키트로, 식탁이나 주방을 꾸미기에 제격입니다.", "https://url.kr/id4dmp");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '피크닉 도시락 세트', 5000, "맛깔나는 도시락 메뉴를 뜨개질로 재현해보세요! 소품 하나하나 정성스럽게 만들며, 직접 완성한 피크닉 도시락 세트로 공간을 따뜻하게 장식해보세요.", "https://url.kr/wtjhkb");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '말랑말랑 푸딩 세트', 4000, "말랑말랑하고 달콤한 느낌의 푸딩 소품을 손으로 직접 만들어보세요. 푸딩을 닮은 귀여운 디저트로, 데코용으로도 완벽합니다!", "https://url.kr/xagkko");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '계란말이 초밥 한접시', 3000, "노릇노릇한 계란말이와 쫀득한 초밥을 손뜨개로 재현해보세요. 한 접시 가득한 귀여운 소품으로, 인테리어에 아기자기한 감성을 더해줍니다.", "https://url.kr/efv6qe");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '스트라이프 주전자 보온덮개', 3500, "추운 겨울, 주전자의 온기를 지켜줄 스트라이프 무늬 보온덮개를 직접 만들어보세요. 감각적인 색감과 따뜻한 소재로 실용성과 아름다움을 모두 잡았습니다.", "https://url.kr/hmm6av");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '동물나라 기린 친구', 10000, "동물 친구 기린을 뜨개질로 완성해보세요. 긴 목과 귀여운 패턴이 특징인 기린 소품은 아이들에게도 인기 만점입니다.", "https://url.kr/e23nau");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '이거썽 제주 가보젠?', 3000, "제주를 떠올리게 하는 감성 소품 DIY 키트입니다. 돌담, 감귤, 바다를 모티브로 한 디테일이 돋보이는 작품을 직접 만들어 보세요.", "https://url.kr/icea6g");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '건강 채소 한바구니', 12000, "신선한 채소가 가득한 한바구니를 손으로 직접 완성해보세요. 가지, 당근, 브로콜리 등 아기자기한 채소들이 소품으로 생생하게 태어납니다.", "https://url.kr/cr29hg");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '달콤 거북이 디저트', 5500, "거북이 모양의 귀여운 디저트를 키링으로 만들어보세요! 가방이나 열쇠에 달기 좋은 아기자기한 소품으로, 매일의 일상을 달콤하게 장식해줍니다.", "https://url.kr/ptt8kl");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (2, '앵두 컵케이크', 5500, "앵두가 올라간 컵케이크 모양의 키링을 완성해보세요. 손쉽게 만들 수 있는 간단한 구성으로, 디저트를 닮은 상큼한 키링이 완성됩니다!", "https://url.kr/7oew2e");


-- 여기부터 도안

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '챔프 티라미수 터틀넥 스웨터 도안', 5500, 
"달콤한 티라미수를 연상시키는 부드럽고 포근한 터틀넥 스웨터를 직접 떠보세요. 심플하면서도 세련된 디자인과 디테일한 설명이 담긴 도안으로 초보자도 쉽게 따라 할 수 있습니다. 추운 겨울, 따뜻한 니트로 스타일과 보온성을 모두 챙기세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_1.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '쁘띠니트 올가 자켓 도안', 6500, "클래식한 감성을 담은 쁘띠니트 올가 자켓 도안입니다. 우아한 실루엣과 고급스러운 디테일이 돋보이는 디자인으로, 간절기부터 겨울까지 다양하게 활용할 수 있습니다. 체계적인 설명과 쉬운 구성으로 니팅의 즐거움을 느껴보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_2.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '리네아 밍크 쁘띠 헤링본 스카프 도안', 3500, "고급스러운 헤링본 패턴과 부드러운 밍크 텍스처를 담은 쁘띠 스카프 도안입니다. 클래식하면서도 트렌디한 디자인으로, 어떤 룩에도 잘 어울리는 아이템을 직접 만들어보세요. 상세한 설명과 꿀팁이 가득해 초보자도 완성할 수 있습니다. 올겨울, 따뜻함과 스타일을 동시에 잡아보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_3.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '포그니 손모아장갑 도안', 4500, "한 손에 쏙 들어오는 따뜻하고 귀여운 포그니 손모아장갑을 직접 떠보세요. 부드러운 소재와 심플한 디자인으로, 보온성과 스타일을 모두 만족시킬 수 있는 아이템입니다. 체계적인 도안과 함께 손쉽게 따라 할 수 있어 초보자도 도전해볼 수 있습니다. 추운 겨울, 당신의 손을 따뜻하게 감싸줄 최고의 작품을 만들어보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_4.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '소프트 브리오쉬 비니 도안', 6500, "부드러운 브리오쉬 패턴으로 완성하는 스타일리시한 비니 도안입니다. 유연하고 포근한 디자인이 특징이며, 남녀노소 모두에게 잘 어울리는 머스트 해브 아이템을 만들어 보세요. 브리오쉬 뜨개 기술을 상세히 설명해 초보자도 도전할 수 있는 구성입니다. 올겨울, 직접 만든 따뜻한 비니로 패션과 보온을 동시에 잡아보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_5.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '오란다 체커보드 투웨이 스트랩 백 도안', 7000, "감각적인 체커보드 패턴과 실용적인 투웨이 디자인이 돋보이는 스트랩 백 도안입니다. 캐주얼부터 포멀까지 다양한 스타일에 매치 가능한 다재다능한 가방을 직접 만들어보세요. 세세한 도안과 설명 덕분에 초보자도 손쉽게 완성할 수 있습니다. 스타일리시하면서도 실용적인 핸드메이드 백으로 당신만의 매력을 더해보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_6.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '슬리핑 도그 인형 도안', 4000, "잠든 강아지의 사랑스러운 모습을 그대로 담은 슬리핑 도그 인형 도안입니다. 폭신한 소재와 포근한 디자인으로 아이들의 친구는 물론, 인테리어 소품으로도 완벽한 선택입니다. 간단하면서도 섬세한 설명이 포함되어 있어 초보자도 쉽게 따라 할 수 있습니다. 손뜨개로 완성하는 귀여운 강아지 인형으로 따뜻한 감성을 더해보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_7.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '쁘띠니트 페스티벌 스웨터 도안', 5000, "밝고 경쾌한 컬러 스트라이프가 돋보이는 쁘띠니트 페스티벌 스웨터를 직접 만들어보세요. 클래식한 디자인과 유쾌한 포인트가 조화를 이루어 일상에서 특별한 분위기를 더해줍니다. 상세하고 친절한 도안으로 초보자도 쉽게 따라 할 수 있으며, 편안한 착용감과 스타일을 모두 만족시킬 수 있는 아이템입니다.", "https://github.com/rmfosem613/save_img/raw/main/design/design_8.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '린넨 라미 코튼 숏 레글런 가디건 도안', 8500, "가볍고 통기성이 좋은 린넨, 라미, 코튼 소재를 활용한 숏 레글런 가디건 도안입니다. 심플하면서도 세련된 디자인으로, 봄과 여름철 가볍게 걸칠 수 있는 아이템을 직접 만들어보세요. 레글런 소매로 자연스러운 핏을 완성하며, 초보자도 쉽게 완성할 수 있도록 상세한 설명이 포함되어 있습니다. 당신만의 스타일리시한 가디건을 직접 뜨개질로 완성해보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_9.png");

INSERT INTO t_product (c_id, product_name, price, content, img) 
VALUES (3, '미소 탈리 썬 바이저 도안', 6500, "여름의 햇살을 완벽하게 막아줄 미소 탈리 썬 바이저를 직접 만들어보세요. 통기성이 좋은 소재와 유니크한 디자인으로, 실용성과 스타일을 모두 갖춘 썬 바이저를 완성할 수 있습니다. 초보자도 쉽게 따라 할 수 있는 도안과 친절한 가이드를 제공하여, 나만의 핸드메이드 썬 바이저로 여름을 더 시원하고 멋지게 즐겨보세요!", "https://github.com/rmfosem613/save_img/raw/main/design/design_10.png");



INSERT INTO t_address (user_id, address, user_name, phone) VALUES ('id 01', '구미 진평동', '미갱', '010-1234-5678');
SELECT * FROM t_address WHERE user_id = 'id 01';
INSERT INTO t_order (user_id, address_id, total_price) VALUES ('id 01', 1, 5000);
-- INSERT INTO t_order (user_id, address_id, total_price) VALUES ('id 01', 1, 7000);

-- INSERT INTO t_order (user_id, address_id, total_price) VALUES ('id 02', 2, 10000.00);
 -- INSERT INTO t_order (user_id, address_id, total_price) VALUES ('id 01', 1, 15000.00);

-- 첫 번째 주문(3번 주문)의 상세 정보
-- INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (3, 1, 2);
-- INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (3, 3, 1);

-- 두 번째 주문(4번 주문)의 상세 정보
-- INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (4, 2, 1);
-- INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (4, 4, 2);
select * from t_order;

 INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 1, 1);
 INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 2, 3);

-- INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (2, 4, 3);
-- INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (2, 2, 3);

-- INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id 01', 1, 4);
-- INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id 01', 2, 1);

INSERT INTO t_comment (product_id, user_id, comment) VALUES (1, 'id 01', 'comment 01');
INSERT INTO t_comment (product_id, user_id, comment) VALUES (1, 'id 01', 'comment 02');
INSERT INTO t_comment (product_id, user_id, comment) VALUES (1, 'id 02', 'comment 03');
INSERT INTO t_comment (product_id, user_id, comment) VALUES (2, 'id 01', 'comment 04');
select * from t_comment;

INSERT INTO t_notification (user_id, title, channel, content) 
VALUES 
('id 01', '배송 출발 안내', 'delivery', '주문하신 제품의 배송이 시작되었어요. 안전하게 배송해드릴게요.'),
('id 01', '새로운 기능 출시 안내2', 'broad', 'NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!'),
('id 01', '배송 출발 안내', 'delivery', '주문하신 제품의 배송이 시작되었어요. 안전하게 배송해드릴게요.'),
('id 01', '새로운 기능 출시 안내4', 'broad', 'NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!'),
('id 01', '배송 출발 안내', 'delivery', '주문하신 제품의 배송이 시작되었어요. 안전하게 배송해드릴게요.'),
('id 01', '새로운 기능 출시 안내6', 'broad', 'NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!');


INSERT INTO t_notification (user_id, title, channel, content) 
VALUES 
('id 02', '배송 출발 안내', 'delivery', '주문하신 제품의 배송이 시작되었어요. 안전하게 배송해드릴게요.'),
('id 02', '새로운 기능 출시 안내3', 'broad', 'NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!'),
('id 02', '배송 출발 안내', 'delivery', '주문하신 제품의 배송이 시작되었어요. 안전하게 배송해드릴게요.'),
('id 02', '새로운 기능 출시 안내5', 'brotad', 'NFC 태그를 활용하여 제품을 스캔하면 해당 제품의 도안 및 안내 영상이 떠요!');
select * from t_notification;


INSERT INTO t_payment (order_id, product_id) VALUES (1, 1);
INSERT INTO t_payment (order_id, product_id) VALUES (1, 2);

INSERT INTO t_tagging (tagging_id, video_src, video_title, video_content) VALUES ('taggin id 01', 'https://github.com/user-attachments/assets/0484c120-eba2-42d3-9e00-a511b5c6c1ee', '푸딩을 만들어보자', '푸딩 영상 설명~~');
INSERT INTO t_tagging (tagging_id, video_src, video_title, video_content) VALUES ('taggin id 02', 'https://github.com/user-attachments/assets/c6f1995b-344a-453f-ae18-0e5438c16b73', '피크민을 만들어보자', '픽닠픽닡');


INSERT INTO t_favorite (user_id, tagging_id, is_valid) VALUES ('id 01', 'taggin id 01', 'Y');
INSERT INTO t_favorite (user_id, tagging_id, is_valid) VALUES ('id 01', 'taggin id 02', 'Y');


INSERT INTO t_like (user_id, product_id) VALUES ('id 01', 1);
INSERT INTO t_like (user_id, product_id) VALUES ('id 01', 2);
INSERT INTO t_like (user_id, product_id) VALUES ('id 01', 3);


INSERT INTO t_coupon (user_id, coupon_name, coupon_start, coupon_end, coupon_discount) 
VALUES('id 01', '10% 할인쿠폰', '2024-11-23', '2024-11-30', 0.1);
INSERT INTO t_coupon (user_id, coupon_name, coupon_start, coupon_end, coupon_discount) 
VALUES('id 01', '2000원 할인쿠폰', '2024-11-23', '2024-11-30', 2000.0);
INSERT INTO t_coupon (user_id, coupon_name, coupon_start, coupon_end, coupon_discount) 
VALUES('id 01', '1000원 할인쿠폰', '2024-11-20', '2024-11-23', 1000.0);

