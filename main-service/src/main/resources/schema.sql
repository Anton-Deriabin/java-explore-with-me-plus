CREATE TABLE IF NOT EXISTS users  (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(250) NOT NULL,
    email VARCHAR(254) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
    );
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
    );
CREATE TABLE IF NOT EXISTS locations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id)
    );
CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(120) NOT NULL,
    annotation VARCHAR(2000) NOT NULL,
    category_id BIGINT NOT NULL,
    description VARCHAR(7000) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    paid BOOLEAN NOT NULL,
    participant_limit INTEGER NOT NULL DEFAULT 0,
    request_moderation BOOLEAN NOT NULL DEFAULT TRUE,
    confirmed_requests INTEGER DEFAULT 0,
    created_on TIMESTAMP NOT NULL,
    initiator_id BIGINT NOT NULL,
    state VARCHAR(10) NOT NULL,
    published_on TIMESTAMP,
    location_id BIGINT NOT NULL,
    views INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT fk_events_to_users FOREIGN KEY (initiator_id) REFERENCES users(id),
    CONSTRAINT fk_events_to_categories FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT fk_events_to_locations FOREIGN KEY (location_id) REFERENCES locations(id)
    );
CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(50) NOT NULL,
    pinned BOOLEAN NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (id)
    );
CREATE TABLE IF NOT EXISTS events_compilations (
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    CONSTRAINT fk_events_to_compilations FOREIGN KEY(compilation_id) REFERENCES compilations(id),
    CONSTRAINT fk_compilations_to_events FOREIGN KEY(event_id) REFERENCES events(id)
    );
CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    status VARCHAR(10) NOT NULL,
    created timestamp NOT NULL,
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT fk_requests_to_events FOREIGN KEY(event_id) REFERENCES events(id),
    CONSTRAINT fk_requests_to_users FOREIGN KEY(requester_id) REFERENCES users(id)
    );
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text VARCHAR(2000) NOT NULL,
    event_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    created timestamp NOT NULL,
    updated timestamp NOT NULL,
    updated_by VARCHAR(5) NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT fk_comments_to_events FOREIGN KEY(event_id) REFERENCES events(id),
    CONSTRAINT fk_comments_to_users FOREIGN KEY(author_id) REFERENCES users(id)
    );
