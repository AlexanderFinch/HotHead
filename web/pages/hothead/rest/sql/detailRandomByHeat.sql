select
    sauce.name,
    sauce.sauce_key,
    company.name as company_name,
    sauce.pepper,
    sauce.hotness,
    sauce.shu,
    sauce.description
from sauce
join company on sauce.company_key = company.company_key
where (? = 'all' or sauce.hotness in (?,?))
order by rand() limit 1;