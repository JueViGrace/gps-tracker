-- +goose Up
CREATE TABLE IF NOT EXISTS session(
    id TEXT NOT NULL PRIMARY KEY,
    refresh_token TEXT NOT NULL,
    access_token TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS session_tracking(
    latitude REAL NOT NULL DEFAULT 0,
    longitude REAL NOT NULL DEFAULT 0,
    altitude REAL NOT NULL DEFAULT 0,
    time TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    session_id TEXT NOT NULL PRIMARY KEY,
    FOREIGN KEY (session_id) REFERENCES session(id)
);

-- +goose Down
DROP TABLE session;
DROP TABLE session_tracking;

