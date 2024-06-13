CREATE TABLE IF NOT EXISTS test_time (
                                     id UUID,
                                     created_at String,
                                     created_at_z String
)
    ENGINE = Kafka SETTINGS kafka_broker_list = 'kafka:9092',
        kafka_topic_list = 'test',
        kafka_group_name = 'newew',
        kafka_format = 'JSONEachRow';

CREATE TABLE IF NOT EXISTS test_time_view (
                                          id UUID,
                                          created_at datetime64,
                                          created_at_z datetime64
) ENGINE = MergeTree()
ORDER BY (id);


CREATE MATERIALIZED VIEW test_consumer TO test_time_view AS
SELECT id, substring(created_at FROM 1 FOR length(test_time.created_at) - 6) as created_at, toDateTime(substring(created_at_z FROM 1 FOR length(created_at_z) - 8)) + interval 3 hour as created_at_z FROM test_time;

