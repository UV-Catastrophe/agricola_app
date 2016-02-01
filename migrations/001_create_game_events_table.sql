CREATE TABLE game_events (
	id UUID PRIMARY KEY,
	game_id UUID NOT NULL,
	player_id UUID NOT NULL,
	event_type VARCHAR NOT NULL,
	body VARCHAR NOT NULL
);

CREATE INDEX game_events_game_id_idx ON game_events (game_id);
CREATE INDEX game_events_player_id_idx ON game_events (player_id);
