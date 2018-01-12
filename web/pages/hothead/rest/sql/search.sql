select
    sauce.sauce_key,
    sauce.name,
    sauce.description,
    company.name as company_name,
    sauce.shu,
    sauce.hotness,
    sauce.pepper,
    ifnull((select avg(rating) from review where review.sauce_key = sauce.sauce_key), 0) as rating,
    (select count(*) from review where review.sauce_key = sauce.sauce_key) as rating_count
from sauce
join company on sauce.company_key = company.company_key
where sauce.name like ?
or sauce.description like ?
or sauce.pepper like ?
or sauce.hotness like ?;