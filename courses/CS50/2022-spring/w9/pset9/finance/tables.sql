-- Here's how to create the stocks and transactions tables in finance.db:
CREATE TABLE stocks (
    id INTEGER,
    symbol TEXT,
    shares INTEGER
);

CREATE TABLE transactions (
    id INTEGER,
    symbol TEXT,
    shares INTEGER,
    price NUMERIC,
    time TEXT DEFAULT (DATETIME('now'))
);
