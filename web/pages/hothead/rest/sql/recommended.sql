select
    sauce.sauce_key,
    sauce.name,
    company.name as company_name,
    recommended.message
from sauce
join recommended on recommended.sauce_key = sauce.sauce_key
join company on company.company_key = sauce.company_key
order by rand()
limit 7;