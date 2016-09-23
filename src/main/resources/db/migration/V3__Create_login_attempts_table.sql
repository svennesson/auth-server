CREATE TABLE login_attempts (
    id serial PRIMARY KEY,
    user_id INT REFERENCES users,
    success BOOLEAN,
    timestampz TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
)