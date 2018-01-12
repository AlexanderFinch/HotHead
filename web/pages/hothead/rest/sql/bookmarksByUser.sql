select
    bookmark.bookmark_key,
    bookmark.sauce_key,
    bookmark.user_key
from bookmark
where bookmark.user_key = ?;