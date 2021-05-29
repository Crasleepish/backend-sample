# 手动将以下SQL导入到Mysql数据库中
USE webapp;

CREATE TABLE `class_info` (
`id` int NOT NULL AUTO_INCREMENT,
`class_name` varchar(255) NOT NULL,
PRIMARY KEY (`id`)
);

INSERT INTO webapp.class_info (class_name) VALUES
('一班')
,('二班')
,('三班')
;