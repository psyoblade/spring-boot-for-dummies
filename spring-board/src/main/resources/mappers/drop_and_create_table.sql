DROP TABLE IF EXISTS `board_item`;
CREATE TABLE IF NOT EXISTS `board_item` (
`board_idx` int NOT NULL AUTO_INCREMENT COMMENT '글 번호',
`title` varchar(300) NOT NULL COMMENT '제목',
`contents` text NOT NULL COMMENT '내용',
`hit_cnt` smallint NOT NULL DEFAULT '0' COMMENT '조회수',
`created_datetime` datetime NOT NULL COMMENT '작성시간',
`creator_id` varchar(50) NOT NULL COMMENT '작성자',
`updated_datetime` datetime DEFAULT NULL COMMENT '수정시간',
`updater_id` varchar(50) DEFAULT NULL COMMENT '수정자',
`deleted_yn` char(1) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
PRIMARY KEY (`board_idx`)
);
DROP TABLE IF EXISTS `t_board`;
CREATE TABLE IF NOT EXISTS `t_board` (
`board_idx` int NOT NULL AUTO_INCREMENT COMMENT '글 번호',
`title` varchar(300) NOT NULL COMMENT '제목',
`contents` text NOT NULL COMMENT '내용',
`hit_cnt` smallint NOT NULL DEFAULT '0' COMMENT '조회수',
`created_datetime` datetime NOT NULL COMMENT '작성시간',
`creator_id` varchar(50) NOT NULL COMMENT '작성자',
`updated_datetime` datetime DEFAULT NULL COMMENT '수정시간',
`updater_id` varchar(50) DEFAULT NULL COMMENT '수정자',
`deleted_yn` char(1) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
PRIMARY KEY (`board_idx`)
);
