DELETE FROM product;

DELETE FROM category;

INSERT INTO
    category (name)
VALUES
    ("Papelaria"),
    ("Livro didático"),
    ("Escritório"),
    ("Acessórios"),
    ("Artesanato");

INSERT INTO
    product (name, price, category_id)
VALUES
    ("Caneta", 2.5, (SELECT id FROM category WHERE name = "Papelaria")),
    ("Lápis", 1, (SELECT id FROM category WHERE name = "Papelaria")),
    ("Borracha", 2.9, (SELECT id FROM category WHERE name = "Papelaria")),
    ("Estojo", 9, (SELECT id FROM category WHERE name = "Papelaria")),
    ("Gramática Básica", 18, (SELECT id FROM category WHERE name = "Livro didático")),
    ("Gramática Intermediária", 25, (SELECT id FROM category WHERE name = "Livro didático")),
    ("Gramática Avançada", 38.9, (SELECT id FROM category WHERE name = "Livro didático")),
    ("Pasta", 5, (SELECT id FROM category WHERE name = "Escritório")),
    ("Arquivo", 39.9, (SELECT id FROM category WHERE name = "Acessórios")),
    ("Mochila", 69, (SELECT id FROM category WHERE name = "Acessórios"));
