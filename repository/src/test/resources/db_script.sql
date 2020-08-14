CREATE TABLE IF NOT EXISTS addresses
(
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(85)    NOT NULL,
    country_code VARCHAR(3)   NOT NULL,
    house_number int NOT NULL,
    postal_code int NOT NULL,
    street VARCHAR(85) NOT NULL
);

CREATE TABLE IF NOT EXISTS company_codes
(
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(2)   NOT NULL,
    constraint company_codes_code
        unique (code)
);

CREATE TABLE IF NOT EXISTS geo_locations
(
    id BIGSERIAL PRIMARY KEY,
    latitude decimal NOT NULL,
    longitude decimal NOT NULL
);

CREATE TABLE IF NOT EXISTS stores
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(85)  NOT NULL,
    phone_number VARCHAR(15)  NOT NULL,
    address_id bigint NULL,
    geo_location_id BIGINT NULL,
    constraint store_address
        foreign key (address_id) references addresses (id),
    constraint store_location
        foreign key (geo_location_id) references geo_locations (id)
);

CREATE TABLE IF NOT EXISTS store_codes
(
    company_code_id BIGINT NOT NULL,
    store_id  BIGINT   NOT NULL,
    constraint store_codes_company_code_id
        foreign key (company_code_id) references company_codes (id),
    constraint store_codes_store_id
        foreign key (store_id) references stores (id)
);
