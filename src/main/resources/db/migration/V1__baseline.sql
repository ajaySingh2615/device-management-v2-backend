-- Baseline: minimal app metadata table (safe to keep forever)
create table if not exists app_info(
    k varchar(64) primary key,
    v varchar(255) not null
);

insert into app_info (k, v) values ("schema", "devices-backend-bootstrapped")
on duplicate key update v = values(v);