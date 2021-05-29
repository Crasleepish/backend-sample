# 实验用表，手动将以下SQL导入到Mysql数据库中
USE webapp;

CREATE TABLE `articles` (
`id` int NOT NULL AUTO_INCREMENT,
`title` varchar(255) DEFAULT NULL,
`author_id` int DEFAULT NULL,
`bonus` int DEFAULT NULL,
`commit_time` timestamp NULL DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `author_id` (`author_id`),
CONSTRAINT `articles_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO webapp.articles (title,author_id,bonus,commit_time) VALUES
('My favorite food',1,80,'2020-05-06 21:14:57.000')
,('A funny weekend',1,85,'2020-11-07 19:11:22.000')
,('动物园游记',3,90,'2020-09-11 15:02:09.000')
,('开心的一天',3,66,'2021-01-15 21:43:35.000')
,('我最爱的电影',6,75,'2021-01-24 03:02:55.000')
,('如何边睡觉边吃饭',6,99,'2020-04-07 17:19:46.000')
,('如何边洗衣服边拉屎',6,100,'2020-09-11 01:29:36.000')
,('什么动物最好吃',2,13,'2020-02-09 07:59:59.000')
;