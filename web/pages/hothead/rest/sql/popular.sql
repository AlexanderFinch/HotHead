select
    sauce.image,
    sauce.name,
    sauce.sauce_key,
    company.name as company_name,
    review.rating,
    (select count(*) from review where review.sauce_key = sauce.sauce_key) as rating_count,
    review.comments,
    users.screen_name
from review
join sauce on review.sauce_key = sauce.sauce_key
join company on sauce.company_key = company.company_key
join users on review.user_key = users.user_key
order by rating desc
limit 7;