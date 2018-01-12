$(document).ready(function(){
    var swiper = new Swiper("nav", "navContent", true);

    loadDiscover();
    loadTrack();

    // add handlers
    $("#search").keypress(function(e){
        if(e.which == 13){
            $.ajax({
                url: "/rest/search/"+$("#search").val(),
            }).then(function(data){
                $("#searchResults").empty();
                $.each($.parseJSON(data), function(i, results){
                    searchItem(results.name, results.company, results.rating);
                });
            });
        }
    });

    $("#search").keyup(function(e){
        if($("#search").val() == ""){
            $("#searchBarContainer span").removeClass("clear");
        } else {
            $("#searchBarContainer span").addClass("clear");
            $(".clear").click(function(e){
                $("#search").val("");
                $("#searchResults").empty();
                $("#searchBarContainer span").removeClass("clear");
            });
        }
    });

    $("#tryButton").click(function(e){
        // show detail popup
    });
});

loadDiscover = function(){
    // load Discover page
    $.ajax({
        url: "/rest/recommended",
        async: false,
    }).then(function(data){
        $.each($.parseJSON(data), function(i, sauce){
            recommendedItem(sauce.name,sauce.message);
        });
    });

    var swiper1 = new Swiper("recommendedInd", "recommended");

    $.ajax({
        url: "/rest/popular",
        async: false,
    }).then(function(data){
        $.each($.parseJSON(data), function(i, review){
            popularItem(review.name, review.company, review.comments, review.user, review.rating);
        });
    });
//popularItem("Tapatio","Tapatio Foods, LLC.","A great sauce for all things", "Alex", 5);
//			popularItem("Lava","Tapatio Foods, LLC.","A great sauce for all things", "Alex", 5);
//			popularItem("Reaper Sling Blade","Tapatio Foods, LLC.","A great sauce for all things", "Alex", 5);
//			popularItem("Gringo Bandito Hot Sauce","Tapatio Foods, LLC.","A great sauce for all things", "Alex", 5);
    var swiper2 = new Swiper("popularInd", "popular");
};

loadTrack = function(){
    reviewItem("Tapatio","Tapatio Foods, LLC.","A great sauce for all things", 5);
    reviewItem("Tapatio","Tapatio Foods, LLC.","A great sauce for all things", 5);
    reviewItem("Tapatio","Tapatio Foods, LLC.","A great sauce for all things", 5);
    reviewItem("Tapatio","Tapatio Foods, LLC.","A great sauce for all things", 5);

    bookmarkItem("Tapatio","Tapatio Foods, LLC.", 5);
    bookmarkItem("Tapatio","Tapatio Foods, LLC.", 5);
    bookmarkItem("Tapatio","Tapatio Foods, LLC.", 5);
    bookmarkItem("Tapatio","Tapatio Foods, LLC.", 5);
};