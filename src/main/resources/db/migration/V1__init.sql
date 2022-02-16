CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `role` varchar(255) DEFAULT 'USER',
  `signature` varchar(255) NOT NULL,
  `state` varchar(255) DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `urls` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number_of_transitions` bigint NOT NULL DEFAULT 0,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `hash` varchar(255) NOT NULL,
  `lifetime` int DEFAULT NULL,
  `state` varchar(255) DEFAULT 'ACTIVE',
  `url` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK31nbxw9e1inas1lmdkwxqv10` (`user_id`),
  CONSTRAINT `FK31nbxw9e1inas1lmdkwxqv10` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO users (signature) VALUES ('$2a$12$g31U1GzT2o9SEUZc4l3tnu4IYCOQflrHEUnIGqQPuuOpvIuuCqHVu');
INSERT INTO users (signature) VALUES ('$2a$12$g31U1GzT2o9SEUZc4luuOpvIYCOQflrHEUnIGqQPuuOpvIuuCqHVu');