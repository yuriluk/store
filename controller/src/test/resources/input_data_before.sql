INSERT INTO company_codes( code) VALUES('AH');
INSERT INTO company_codes( code) VALUES('ET');
INSERT INTO company_codes( code) VALUES('GA');


INSERT INTO addresses(street, house_number, postal_code, city, country_code)
                                            VALUES('Novatorskaya', 154, 220000, 'Minsk', 'BY');
INSERT INTO addresses(street, house_number, postal_code, city, country_code)
                                            VALUES('Svobodi', 45, 125500, 'Pinsk', 'BY');
INSERT INTO addresses(street, house_number, postal_code, city, country_code)
                                            VALUES('Morskaya', 35, 320077, 'Slonim', 'BY');

INSERT INTO geo_locations(latitude, longitude) VALUES(53.908508, 27.432272);
INSERT INTO geo_locations(latitude, longitude) VALUES(53.8508, 27.4);
INSERT INTO geo_locations(latitude, longitude) VALUES(53.7, 27.5);


INSERT INTO stores (name, phone_number, geo_location_id, address_id) VALUES ('Test shop', '+375291234567', 1, 1);
INSERT INTO stores (name, phone_number, geo_location_id, address_id) VALUES ('Shop 2', '+375291112233', 2, 2);
INSERT INTO stores (name, phone_number, geo_location_id, address_id) VALUES ('Shop 3', '+375297755666', 3, 3);

INSERT INTO store_codes(company_code_id, store_id) VALUES(1, 1);
INSERT INTO store_codes(company_code_id, store_id) VALUES(2, 2);
INSERT INTO store_codes(company_code_id, store_id) VALUES(3, 3);
