CREATE TABLE tokens (
    token UUID PRIMARY KEY,
    distributed_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    user_id INT UNIQUE REFERENCES users
)