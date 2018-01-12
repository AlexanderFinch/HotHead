function recommendedItem(title, content){
    var $item = $("<div />").addClass("recommendedItem");
    var $title = $("<div />").addClass("recommendedTitle").html(title);
    var $divider = $("<div />").addClass("hDivider");
    var $content = $("<div />").addClass("recommendedContent").html(content);
    var $button = $("<button />").addClass("buttonBlue ui-btn ui-shadow ui-corner-all").html("Check it out!");
    var $buttonContainer = $("<div />").addClass("recommendedButtonContainer");
    $buttonContainer.append($button);
    $item.append($title).append($divider).append($content).append($buttonContainer);
    $("#recommended").append($item);
    var $recIndItem = $("<div />").addClass("recommendedIndItem");
    $("#recommendedInd").append($recIndItem);
}

function popularItem(title, company, comments, userName, rating){
    var $item = $("<div />").addClass("popularItem");
    var $pic = $("<img src='/hothead/drawable/firesauce.png' />").addClass("clipImg100");
    var $picDiv = $("<div />").addClass("popularImage").append($pic);
    var $title = $("<div />").addClass("popularTitle popularText").html(title);
    var $company = $("<div />").addClass("popularCompany popularText").html(company);
    var $rating = $("<div />").addClass("popularRating popularText").html(rating);
    var $comments = $("<div />").addClass("popularComments popularText").html(comments);
    var $user = $("<div />").addClass("popularUser popularText").html(userName);
    var $divider = $("<div />").addClass("hDivider");
    var $star = $("<img  src='/hothead/drawable/star_off.png'/>").addClass("imageCenter");
    var $bookmark = $("<div />").addClass("popularBookmark").html("Bookmark").prepend($star);
    $item.append($picDiv).append($title).append($company).append($rating).append($rating)
            .append($comments).append($user).append($divider).append($bookmark);
    $("#popular").append($item);
    var $popIndItem = $("<div />").addClass("popularIndItem");
    $("#popularInd").append($popIndItem);
}

function reviewItem(name, company, comments, rating){
    var $item = $("<div />").addClass("reviewItem");
    var $pic = $("<img src='/hothead/drawable/firesauce.png' />").addClass("clipImg50");
    var $picDiv = $("<div />").addClass("reviewImage").append($pic);
    var $title = $("<div />").addClass("reviewTitle");
    var $name = $("<div />").html(name);
    var $company = $("<div />").html(company);
    $title.append($name).append($company);
    var $rating = $("<div />").addClass("reviewRating").html(rating);
    var $comments = $("<div />").addClass("reviewComments").html(comments);
    $item.append($picDiv).append($title).append($rating).append($comments);
    $("#review").append($item);
}

function bookmarkItem(name, company, rating){
    var $item = $("<div />").addClass("bookmarkItem");
    var $pic = $("<img src='/hothead/drawable/firesauce.png' />").addClass("clipImg50");
    var $picDiv = $("<div />").addClass("reviewImage").append($pic);
    var $title = $("<div />").addClass("reviewTitle");
    var $name = $("<div />").html(name);
    var $company = $("<div />").html(company);
    $title.append($name).append($company);
    var $rating = $("<div />").addClass("reviewRating").html(rating);
    $item.append($picDiv).append($title).append($rating);
    $("#bookmark").append($item);
}

function searchItem(name, company, rating){
    var $item = $("<div />").addClass("searchItem");
    var $pic = $("<img src='/hothead/drawable/firesauce.png' />").addClass("clipImg50");
    var $picDiv = $("<div />").addClass("searchImage").append($pic);
    var $title = $("<div />").addClass("searchTitle");
    var $name = $("<div />").html(name);
    var $company = $("<div />").html(company);
    $title.append($name).append($company);
    var $rating = $("<div />").addClass("searchRating").html(rating);
    $item.append($picDiv).append($title).append($rating);
    $("#searchResults").append($item);
}