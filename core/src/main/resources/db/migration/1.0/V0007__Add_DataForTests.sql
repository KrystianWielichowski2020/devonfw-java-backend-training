INSERT INTO Item(id, ModificationCounter, name, description, price) VALUES (22, 0, 'spaghetti carbonara', 'Italy', 270);
INSERT INTO Item(id, ModificationCounter, name, description, price) VALUES (23, 0, 'pizza', 'Italy', 220);
INSERT INTO Item(id, ModificationCounter, name, description, price) VALUES (24, 0, 'spaghetti ala biedra', 'Italy', 180);
INSERT INTO Item(id, ModificationCounter, name, description, price) VALUES (25, 0, 'pierogi ruskie', 'Poland', 200);
INSERT INTO Item(id, ModificationCounter, name, description, price) VALUES (26, 0, 'bigos', 'Poland', 210);

INSERT INTO Customer(id, ModificationCounter, firstname, lastname) VALUES (32, 0, 'Bogumil', 'Swiety');
INSERT INTO Customer(id, ModificationCounter, firstname, lastname) VALUES (33, 0, 'Pawel', 'Kowal');
INSERT INTO Customer(id, ModificationCounter, firstname, lastname) VALUES (34, 0, 'Adam', 'Malysz');
INSERT INTO Customer(id, ModificationCounter, firstname, lastname) VALUES (35, 0, 'Jan', 'Nowak');

INSERT INTO OrderSummary(id, ModificationCounter, price, ownerId, creationDate, status) VALUES (42, 0, 671.10, 31, '2020-03-12', 'PAID');
INSERT INTO OrderSummary(id, ModificationCounter, price, ownerId, creationDate, status) VALUES (43, 0, 671.10, 33, '2020-02-12', 'NEW');
INSERT INTO OrderSummary(id, ModificationCounter, price, ownerId, creationDate, status) VALUES (44, 0, 671.10, 34, '2020-04-13', 'NEW');
INSERT INTO OrderSummary(id, ModificationCounter, price, ownerId, creationDate, status) VALUES (45, 0, 671.10, 35, '2020-10-01', 'NEW');

INSERT INTO OrderPosition(orderId, itemId) VALUES (42, 23);
INSERT INTO OrderPosition(orderId, itemId) VALUES (42, 25);
INSERT INTO OrderPosition(orderId, itemId) VALUES (43, 23);
INSERT INTO OrderPosition(orderId, itemId) VALUES (44, 23);
INSERT INTO OrderPosition(orderId, itemId) VALUES (45, 23);