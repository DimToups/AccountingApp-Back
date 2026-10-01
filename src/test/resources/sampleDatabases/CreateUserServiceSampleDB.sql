INSERT INTO users(username, password, enabled, firstname, lastname)
    VALUES ('Pizza', 'someNonHashedPasswordThatWontWork', true, 'Pizza', 'Romarin'),
           ('Perpustakaan', 'someNonHashedPasswordThatWontWork', true, null, null);

INSERT INTO authorities (authority, username)
    VALUES ('ROLE_USER', 'Pizza'),
           ('ROLE_USER', 'Perpustakaan');