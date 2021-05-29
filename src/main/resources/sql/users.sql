# 手动将以下SQL导入到Mysql数据库中
USE webapp;

CREATE TABLE `users` (
`id` int NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
`gender` tinyint DEFAULT NULL,
`age` int DEFAULT NULL,
`class_id` int DEFAULT NULL,
`type` varchar(100) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `users_FK` (`class_id`),
CONSTRAINT `users_FK` FOREIGN KEY (`class_id`) REFERENCES `class_info` (`id`)
);

INSERT INTO webapp.users (name,gender,age,class_id,`type`) VALUES
('zhangsan',1,11,1,'STU')
,('lisi',0,10,2,'STU')
,('高华',0,15,3,'STU')
,('alice',0,13,1,'STU')
,('john',1,23,NULL,'TEACHER')
,('kite',1,12,2,'STU')
,('王泊棠',0,22,NULL,'TEACHER')
;