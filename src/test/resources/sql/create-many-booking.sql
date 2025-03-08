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
values ('Тестовый клиент ' || CURRENT_TIMESTAMP, 'y228@ya.ru');

with cte2 as (
    select id
    from client
    where email = 'y228@ya.ru'
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
values ('2025-10-01', '2025-10-02', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-02', '2025-10-03', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-03', '2025-10-04', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-04', '2025-10-05', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-05', '2025-10-06', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-06', '2025-10-07', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-07', '2025-10-08', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-08', '2025-10-09', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-09', '2025-10-10', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-11', '2025-10-12', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-12', '2025-10-13', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-13', '2025-10-14', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-14', '2025-10-15', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-15', '2025-10-16', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-16', '2025-10-17', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-17', '2025-10-18', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-18', '2025-10-19', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-19', '2025-10-20', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-20', '2025-10-21', (select id from cte2), (select id from cte3), 1000.00),
        ('2025-10-21', '2025-10-22', (select id from cte2), (select id from cte3), 1000.00);
