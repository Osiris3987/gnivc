CREATE TABLE IF NOT EXISTS tasks (
                                     id UUID,
                                     start_point String,
                                     end_point String,
                                     driver_first_name String,
                                     driver_last_name String,
                                     description String,
                                     transport_state_number String,
                                     created_at datetime64,
                                     company_id String,
                                     driver_id UUID
)
    ENGINE = Kafka SETTINGS kafka_broker_list = 'kafka:9092',
                            kafka_topic_list = 'tasks',
                            kafka_group_name = 'task_consumer_group_clickhouse',
                            kafka_format = 'JSONEachRow';

CREATE TABLE tasks_view (
                            id UUID,
                            start_point String,
                            end_point String,
                            driver_first_name String,
                            driver_last_name String,
                            description String,
                            transport_state_number String,
                            created_at datetime64,
                            company_id String,
                            driver_id UUID
) ENGINE = MergeTree()
      ORDER BY (company_id, created_at);

CREATE MATERIALIZED VIEW tasks_consumer TO tasks_view AS
SELECT * FROM tasks;




CREATE TABLE IF NOT EXISTS races (
    id UUID,
    created_at datetime64,
    started_at datetime64,
    ended_at datetime64,
    task_id UUID
)
    ENGINE = Kafka SETTINGS kafka_broker_list = 'kafka:9092',
        kafka_topic_list = 'races',
        kafka_group_name = 'task_consumer_group_clickhouse',
        kafka_format = 'JSONEachRow';

CREATE TABLE IF NOT EXISTS races_view (
                                     id UUID,
                                     created_at datetime64,
                                     started_at datetime64,
                                     ended_at datetime64,
                                     task_id UUID
) ENGINE = MergeTree()
  ORDER BY (task_id);

CREATE MATERIALIZED VIEW races_consumer TO races_view AS
SELECT * FROM races;




CREATE TABLE IF NOT EXISTS race_events (
                                    event_type String,
                                    created_at datetime64,
                                    race_id UUID
)   ENGINE = Kafka SETTINGS kafka_broker_list = 'kafka:9092',
        kafka_topic_list = 'race_events',
        kafka_group_name = 'task_consumer_group_clickhouse',
        kafka_format = 'JSONEachRow';

CREATE TABLE IF NOT EXISTS race_events_view (
                                           event_type String,
                                           created_at datetime64,
                                           race_id UUID
)   ENGINE = ReplacingMergeTree
    PRIMARY KEY (race_id, event_type);

CREATE MATERIALIZED VIEW race_events_consumer TO race_events_view AS
SELECT * FROM race_events;