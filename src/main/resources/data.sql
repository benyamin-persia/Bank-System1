INSERT INTO customers (first_name, last_name, email, username, password, role,enabled,failed_attempts,account_locked)
VALUES ('Admin', 'User', 'admin@bank.com', 'admin'
           ,'$2a$10$c202ijgE9hHYswbyHCMgTeXEKzr7eOKE/BHRomqMZ6I9Cq4PbxHzy',
        'ROLE_ADMIN', true,0,false);
INSERT INTO customers (first_name, last_name, email, username, password, role,enabled,failed_attempts,account_locked)
VALUES ('John', 'Doe', 'john@bank.com', 'user',
        '$2a$10$KhtTEgAOn0VPAeSttVUMW.XD5zHDExrhqYmjVJv8x8c6bjMwu8MWS',
        'ROLE_USER', true,0,false);


INSERT INTO ACCOUNTS (OWNER, BALANCE, TYPE, STATUS, CUSTOMER_ID)
VALUES ('Mohammed', 5000, 'SAVINGS', 'ACTIVE', 1);

INSERT INTO ACCOUNTS (OWNER, BALANCE, TYPE, STATUS, CUSTOMER_ID)
VALUES ('Sara', 3000, 'CHECKING', 'ACTIVE', 2);
