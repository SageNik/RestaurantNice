DROP DATABASE IF EXISTS `restaurant_nice`;
CREATE DATABASE  IF NOT EXISTS `restaurant_nice` DEFAULT CHARSET=utf8;

USE `restaurant_nice`;

--
-- Table structure for table `user`
--

-- DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` VARCHAR (30) DEFAULT NULL,
  `password` VARCHAR (30) DEFAULT NULL,
  `role` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `group`
--

-- DROP TABLE IF EXISTS `group`;
CREATE TABLE IF NOT EXISTS `group` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) DEFAULT NULL ,
  `description` VARCHAR(200) DEFAULT NULL ,
  `owner_id` int(20) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `dish_category`
--

-- DROP TABLE IF EXISTS `dish_category`;
CREATE TABLE IF NOT EXISTS `dish_category` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) DEFAULT NULL ,
  `amount_dish` int(10) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `order`
--

-- DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_state` int(5) DEFAULT NULL ,
  `sum` DOUBLE UNSIGNED DEFAULT 0.0,
  `user_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `dish`
--

-- DROP TABLE IF EXISTS `dish`;
CREATE TABLE IF NOT EXISTS `dish` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR (150) DEFAULT NULL,
  `description` VARCHAR (500) DEFAULT NULL,
  `price` DOUBLE DEFAULT 0.00,
  `amount` int(10) DEFAULT 0,
  `dish_type` int(5) UNSIGNED NOT NULL,
  `dish_category_id` int(5) UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_dish_dish_category` FOREIGN KEY (`dish_category_id`) REFERENCES `dish_category`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `join_request`
--

-- DROP TABLE IF EXISTS `join_request`;
CREATE TABLE IF NOT EXISTS `join_request` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `join_user_id` int(20) UNSIGNED DEFAULT NULL ,
  `group_id` int(20) UNSIGNED DEFAULT NULL ,
  `owner_group_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sign_up_request_user` FOREIGN KEY (`owner_group_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `order_dish`
--

# DROP TABLE IF EXISTS `order_dish`;
CREATE TABLE IF NOT EXISTS `order_dish` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(20) unsigned NOT NULL,
  `dish_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_dish_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_dish_dish` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `order_group`
--

# DROP TABLE IF EXISTS `order_group`;
CREATE TABLE IF NOT EXISTS `order_group` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(20) unsigned NOT NULL,
  `group_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_group_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_group_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `user_group`
--

# DROP TABLE IF EXISTS `user_group`;
CREATE TABLE IF NOT EXISTS `user_group` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(20) unsigned NOT NULL,
  `group_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_group_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_group_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Insert default Dishes to Menu
--
INSERT INTO `dish_category` (name) VALUES ('FIRST DISHES');
INSERT INTO `dish_category` (name) VALUES ('PIZZA');
INSERT INTO `dish_category` (name) VALUES ('MEAT DISHES');
INSERT INTO `dish_category` (name) VALUES ('FISH DISHES');
INSERT INTO `dish_category` (name) VALUES ('SALADS');
INSERT INTO `dish_category` (name) VALUES ('DRINKS');

INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,1,'Суп овощной','Состав: морковь,болгарский перец,кабачок,петрушка,соус соевый,лапша удон,шампиньоны,приправы.',49.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,1,'Солянка "Сборная"','Состав: бекон,лимон,укроп,лук,маринованный огурец,охотничьи колбаски,маслины,копченое мясо,сметана,томатная паста,копченое куриное филе.',81.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,2,'Пицца "Пепперони"','Состав: сыр моцарелла,томат,томатный соус,колбаса пепперони,белый соус,перец пепперони,красный соус.',117.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,2,'Пицца "Маргарита"','Состав: сыр моцарелла,томат,белый соус,красный соус.',99.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,3,'Рис с мясом','Состав: рис,морковь,репчатый лук,свинина,куриное филе,шампиньоны,карри,фирменный соус.',94.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,3,'Лапша рисовая с куриным филе','Состав: брокколи,спаржа,кукуруза,болгарский перец,репчатый лук,грибы шиитаке,рисовая лапша,куриное филе,омлет томаго,соус для вок.',96.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,4,'Рол с угрём','Состав: сыр филадельфия,лосось,огурец. Вес:180г',85.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,4,'Рол "Филадельфия с копченым лососем"','Состав: сыр филадельфия,копченый лосось,водоросли нори,огурец. Вес:230г',124.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,5,'Салат "Пражский"','Состав: болгарский перец,говядина,маринованный огурец,крымский лук,соус соевый,растительное масло,салат латук,яблоко.',87.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,5,'Салат "Цезарь с курицей"','Состав: томат,яйцо,сыр пармезан,пекинская капуста,куриное филе,фирменный соус.',93.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,6,'Вода Bonaqua среднегазированная','500мл.',19.00 );
INSERT INTO `dish` (dish_type,`dish_category_id`,`name`,`description`,`price`) VALUES (0,6,'Coca-Cola ','250мл.',18.00 );