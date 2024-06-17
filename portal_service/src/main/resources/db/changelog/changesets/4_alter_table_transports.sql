ALTER TABLE transports
ADD COLUMN IF NOT EXISTS state_number varchar not null default 'x000xx'