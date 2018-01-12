function Swiper(tabName, pageName, saveState){
	var speed = 200;

	var click = function(el){
		var $oldTab = $("#"+tabName+" > div."+tabName+'Active');
		var $newTab = $(el);
		if($oldTab.index() > $newTab.index())
			swipeRight($newTab.index());
		else if($oldTab.index() < $newTab.index())
			swipeLeft($newTab.index());
	};
		
	var swipeLeft = function(index){
		var $oldPage = $("#"+pageName+" > div:visible");
		if($oldPage.next().length != 0){
			var $newPage = $oldPage.next();
			if(index){
				$newPage = $($oldPage.parent().children()[index]);
			}
			var $tab = $($("#"+tabName).children()[$newPage.index()]);
			$oldPage.hide('slide', {direction: 'left'}, speed);
			$newPage.show('slide', {direction: 'right'}, speed);
			$tab.addClass(tabName+'Active').siblings().removeClass(tabName+'Active');
			if(saveState){
			    sessionStorage.setItem("pageIndex", index);
			}
		}
	};
		
	var swipeRight = function(index){
		var $oldPage = $("#"+pageName+" > div:visible");
			if($oldPage.prev().length != 0){
			var $newPage = $oldPage.prev();
			if(index || index === 0){
				$newPage = $($oldPage.parent().children()[index]);
			}
			var $tab = $($("#"+tabName).children()[$newPage.index()]);
			$oldPage.hide('slide', {direction: 'right'}, speed);
			$newPage.show('slide', {direction: 'left'}, speed);
			$tab.addClass(tabName+'Active').siblings().removeClass(tabName+'Active');
			if(saveState){
                sessionStorage.setItem("pageIndex", index);
            }
		}
	};

	var gotoPage = function(index){
		var $tab = $("#"+tabName);
		var $oldPage = $("#"+pageName+" > div:visible");
        if(index && index >= 0 && index < $tab.children().length){
			$($tab.children()[index]).trigger("click");
		}
	};
	
	this.swipeHandler = function(e){
		var direction = e.swipestart.coords[0] - e.swipestop.coords[0];
		if(direction < 0)
			swipeRight();
		else 
			swipeLeft();
		e.stopPropagation();
	};

    $("#"+tabName+" > div").click(function(){click(this)});	
	$("#"+tabName+" > div:first-child").addClass(tabName+'Active');
	$("#"+pageName+" > div").on("swipe", this.swipeHandler).not(':first-child').hide()
	$("#"+pageName).css('display','flex');

	if(sessionStorage.getItem("pageIndex") !== null){
        gotoPage(sessionStorage.getItem("pageIndex"));
	}
}
