CREATE TABLE IF NOT EXISTS product (
                         id bigint NOT NULL AUTO_INCREMENT,
                         descripcion varchar(255),
                         nombre varchar(255),
                         precio integer,
                         PRIMARY KEY (id)
) ENGINE=InnoDB;
