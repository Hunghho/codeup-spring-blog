USE blog_db;

TRUNCATE blogs;

INSERT INTO blogs (title, body)
VALUE
    ("First post", "My first post"),
    ("Second post", "My second post");
