delete from booking;
delete from advert;

insert into apartment (city, street, house, room_count)
values ('Москва', 'Центральная', 'дом 10', 'TWO_BEDROOM');

with cte1 as (
    select id
    from apartment
    order by id
    limit 1
)
insert into advert (
    price, is_active, apartment_id, description
)
values (100, true, (select id from cte1), 'Первая тестовая квартира '  || CURRENT_TIMESTAMP),
       (200, true, (select id from cte1), 'Вторая тестовая квартира ' || CURRENT_TIMESTAMP),
       (300, true, (select id from cte1), 'Третья тестовая квартира ' || CURRENT_TIMESTAMP),
       (400, true, (select id from cte1), 'Четвертая тестовая квартира ' || CURRENT_TIMESTAMP),
       (500, true, (select id from cte1), 'Пятая тестовая квартира ' || CURRENT_TIMESTAMP),
       (600, true, (select id from cte1), 'Шестая тестовая квартира ' || CURRENT_TIMESTAMP),
       (700, true, (select id from cte1), 'Седьмая тестовая квартира ' || CURRENT_TIMESTAMP),
       (800, true, (select id from cte1), 'Восьмая тестовая квартира ' || CURRENT_TIMESTAMP),
       (900, true, (select id from cte1), 'Девятая тестовая квартира ' || CURRENT_TIMESTAMP),
       (1000, true, (select id from cte1), 'Девятая тестовая квартира ' || CURRENT_TIMESTAMP),
       (1100, true, (select id from cte1), 'Одинадцатая тестовая квартира ' || CURRENT_TIMESTAMP);
