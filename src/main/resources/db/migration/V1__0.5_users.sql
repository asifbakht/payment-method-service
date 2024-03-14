CREATE TABLE IF NOT EXISTS `payment_method` (
    `id` VARCHAR(50) NOT NULL PRIMARY KEY,
    `customer_id` varchar(50) NOT NULL,
    `payment_name` varchar(50) DEFAULT NULL,
    `payment_type` varchar(10) DEFAULT NULL,
    `card_holder_name` varchar(50) DEFAULT NULL,
    `card_number` varchar(16) DEFAULT NULL,
    `expiration_month` SMALLINT NULL,
    `expiration_year` SMALLINT NULL,
    `cvv` SMALLINT,
    `card_type` varchar(10) DEFAULT NULL,
    `account_number` varchar(12) DEFAULT NULL,
    `routing_number` varchar(9) DEFAULT NULL,
    `account_holder_name` varchar(50) DEFAULT NULL,
    `bank_name` varchar(50) DEFAULT NULL,
    `is_deleted` smallint DEFAULT 0,
    `date_created` timestamp DEFAULT NOW(),
    `date_updated` timestamp NOT NULL DEFAULT NOW() ON UPDATE NOW()
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;