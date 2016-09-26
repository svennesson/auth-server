ALTER TABLE login_attempts
ALTER COLUMN timestampz
SET DEFAULT NOW() AT TIME ZONE 'Europe/Stockholm';