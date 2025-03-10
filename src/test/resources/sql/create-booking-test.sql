delete from booking;
delete from advert;
delete from client;

insert into apartment (city, street, house, room_count)
values ('Москва', 'Центральная', 'дом 10', 'TWO_BEDROOM');

with cte1 as (
    select id
    from apartment
    order by id desc
    limit 1
)
insert into advert (
    price, is_active, apartment_id, description
)
values (1100, true, (select id from cte1), 'Тестовая квартира ' || CURRENT_TIMESTAMP);

insert into client (name, email)
values ('Тестовый клиент ' || CURRENT_TIMESTAMP, 'testemail@test.ru ' || CURRENT_TIMESTAMP);

with cte2 as (
    select id
    from client
    order by id
    limit 1
),
cte3 as (
	    select id
        from advert
        order by id
        limit 1
)
insert into booking (date_start, date_end, client_id, advert_id, price)
values ('2025-10-01', '2025-10-10', (select id from cte2), (select id from cte3), 9900.00);
