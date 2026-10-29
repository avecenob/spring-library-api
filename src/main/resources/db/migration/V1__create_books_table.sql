CREATE TABLE books (
    id             UUID PRIMARY KEY DEFAULT uuidv7(),
    title          VARCHAR(255) NOT NULL,
    author         VARCHAR(255) NOT NULL,
    isbn           VARCHAR(20)  NOT NULL UNIQUE,
    published_year INTEGER,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT now()
);