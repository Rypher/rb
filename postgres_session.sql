select uuid_generate_v4() as "cid";

select * from session

drop table session

CREATE TABLE session ( 
id SERIAL PRIMARY KEY,
cid UUID not null, 
context text,
created timestamp
)


insert into session (cid, context, created)
values (
uuid_generate_v4(),
'{"name": "Ross"}',
now()
)

CREATE EXTENSION "uuid-ossp";


select 
cid, 
context, 
created 
from 
session 
limit 1


select id, cid, context, created from session where cid = '74d26cfa-ef24-4703-87af-e1dceadcdf0c' limit 1

insert into session (cid, context, created) values(uuid_generate_v4(),'{}',now()) RETURNING id, cid, context, created