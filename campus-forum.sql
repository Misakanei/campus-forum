/*
 Navicat Premium Dump SQL

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : campus-forum

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 22/11/2024 19:50:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '分类描述',
  `created_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '板块表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, '测试板块1', '测试板块1的描述', '2024-11-14 18:04:04', 0);
INSERT INTO `categories` VALUES (2, '测试2', '测试板块222', '2024-11-14 18:04:07', 0);
INSERT INTO `categories` VALUES (3, '啦啦啦啦啦111', 'h哈哈哈哈11111', '2024-11-16 01:01:42', 1);
INSERT INTO `categories` VALUES (4, '测试添加板块', '我是描述', '2024-11-16 00:38:00', 0);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `post_id` int NOT NULL COMMENT '外键关联帖子表',
  `user_id` int NOT NULL COMMENT '外键关联用户表',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `created_time` datetime NOT NULL COMMENT '评论时间',
  `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除标识',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `comments_postid`(`post_id` ASC) USING BTREE,
  INDEX `comments_userid`(`user_id` ASC) USING BTREE,
  CONSTRAINT `comments_postid` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comments_userid` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (1, 1, 1, '测试评论', '2024-11-14 13:19:26', 0);
INSERT INTO `comments` VALUES (2, 1, 2, '测试评论2测试测啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2024-11-14 14:07:10', 0);
INSERT INTO `comments` VALUES (3, 1, 1, '评论接口测试', '2024-11-14 20:24:12', 0);
INSERT INTO `comments` VALUES (4, 2, 1, '评论哦', '2024-11-14 21:04:13', 0);
INSERT INTO `comments` VALUES (5, 2, 1, '哇哇哇哇哇哇哇哇哇', '2024-11-14 21:04:52', 0);
INSERT INTO `comments` VALUES (6, 1, 1, '111', '2024-11-15 14:19:45', 1);

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `post_id` int NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `user_id` int NOT NULL COMMENT '外键关联用户表',
  `category_id` int NOT NULL COMMENT '外键关联分类表',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `reading_volume` int NULL DEFAULT 0 COMMENT '阅读量',
  `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除标识',
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片存放路径',
  `support` int NULL DEFAULT 0 COMMENT '点赞数',
  PRIMARY KEY (`post_id`) USING BTREE,
  INDEX `userid`(`user_id` ASC) USING BTREE,
  INDEX `categoryid`(`category_id` ASC) USING BTREE,
  CONSTRAINT `categoryid` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (1, 1, 1, '测试1', '测试修改帖子', '2024-11-13 15:27:46', '2024-11-15 15:18:53', 236, 0, '/uploads/images/55dfb988-851e-4704-8ea7-a61069ec524a.jpg', 26);
INSERT INTO `posts` VALUES (2, 1, 2, '测试测试', '测试测试', '2024-11-13 16:21:32', '2024-11-13 16:21:17', 121, 0, NULL, 6);
INSERT INTO `posts` VALUES (3, 2, 1, '这又是一条测试数据哦', '还是测试测试测试测试出', '2024-11-14 18:52:10', NULL, 229, 0, NULL, 33);
INSERT INTO `posts` VALUES (4, 1, 3, '我是标题', '我怕看到我客服里面撒旦法刷卡达芙妮欧舒丹开门sd卡女的看妇女健康的女警飞啊顺丰凭什么艾佛森卡佛菩开房间', '2024-11-14 18:57:44', NULL, 153, 0, NULL, 0);
INSERT INTO `posts` VALUES (5, 1, 1, '发帖测试', '发帖测试发帖测试发帖测试发帖测试发帖测试发帖测试发帖测试发帖测试发帖测试', '2024-11-15 13:14:01', '2024-11-15 13:14:01', 109, 0, NULL, 0);
INSERT INTO `posts` VALUES (6, 1, 2, '111', '111', '2024-11-15 13:42:06', NULL, 110, 1, '/uploads/images/4827f1a4-1697-402f-bb70-7eeab79ab7d2.jpg', 0);
INSERT INTO `posts` VALUES (7, 1, 1, '我的我的挖的啊啊', '1', '2024-11-15 13:48:28', '2024-11-15 13:48:28', 110, 0, '/uploads/images/d4ddb26e-a0dc-4a4f-be32-2bd363b4bccf.jpg', 0);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `created_time` datetime NOT NULL COMMENT '注册时间',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '最后更新时间',
  `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除标识',
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `avatar_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像路径',
  `is_admin` int NOT NULL DEFAULT 0 COMMENT '管理员标识',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'root', 'd3b1294a61a07da9b49b6e22b2cbd7f9', 'root@test.com', '2024-11-13 13:25:17', '2024-11-15 14:56:15', 0, '我是测试签名111', '/uploads/images/avatars/avatar_1_19a4112d-242d-477a-9958-2b71d5148f88.jpg', 1);
INSERT INTO `users` VALUES (2, 'testUser', 'd3b1294a61a07da9b49b6e22b2cbd7f9', 'testUser@example.com', '2024-11-13 13:25:36', '2024-11-15 21:44:43', 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (3, 'zhangsan', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@qq.com', '2024-11-14 00:14:29', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (4, 'test', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:15:50', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (5, 'test1', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:17:44', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (6, 'test2', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:19:21', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (7, 'test3', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:19:44', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (8, 'wangwu', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:21:00', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (9, 'user', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:21:52', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (10, 'testaaaa', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:23:19', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (11, 'aaaaaa', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 00:24:13', NULL, 0, NULL, NULL, 0);
INSERT INTO `users` VALUES (12, 'fhs', 'd3b1294a61a07da9b49b6e22b2cbd7f9', '123@test.com', '2024-11-14 01:18:38', '2024-11-15 21:46:53', 1, NULL, NULL, 0);
INSERT INTO `users` VALUES (13, 'aaa', 'd3b1294a61a07da9b49b6e22b2cbd7f9', 'aaa@aaa.com', '2024-11-15 14:42:38', NULL, 0, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
