DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
                              iban VARCHAR(250) PRIMARY KEY ,
                              balance DECIMAL NOT NULL
);

INSERT INTO accounts (iban, balance) VALUES
('ES0000000001', '1500'),
('ES0000000002', '2000');

DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions (
                          iban VARCHAR(250) PRIMARY KEY ,
                          reference VARCHAR(250) ,
                          date DATE ,
                          amount DECIMAL NOT NULL,
                          fee VARCHAR(250) ,
                          description VARCHAR(250) ,
                          transaction VARCHAR(250)
);
