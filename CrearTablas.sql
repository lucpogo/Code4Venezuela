create table ubicaciones (
lat numeric,
lon numeric,
rad varchar(10),
ubicacion varchar(50)
);

create table tweets (
query varchar(30),
ubicacion varchar(50),
id numeric,
tweet_date timestamp,
usernick varchar(200),
userid numeric,
username varchar(200),
tweet_text varchar(500),
hashtags varchar(500),
is_retweet boolean,
retweet_of numeric
);

create table busquedas (
busqueda varchar(100)
);
