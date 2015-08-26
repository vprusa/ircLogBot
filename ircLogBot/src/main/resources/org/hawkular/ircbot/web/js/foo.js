$.ajaxSetup({"error":function(XMLHttpRequest,textStatus, errorThrown) {   
	console.error(textStatus);
	console.error(errorThrown);
	console.error(XMLHttpRequest.responseText);
}});

$(document).ready(function() {
	var hideModal;
	$('.modal').hide();

	var hideIt = function() {
		$('.modal').addClass('close');
		hideModal = setTimeout(function(){
			console.log( "hiding!" );
			$('.modal').hide();
		}, 450);
	};

	var displayed = false;
	$('#expand').on('click', function(e){
		if (!displayed) {
			clearTimeout(hideModal);
			$('.modal').show(0, function(){
				console.log( "showing!" );
				$('.modal').removeClass('close');
			});
		} else {
			hideIt();
		}
		displayed = !displayed;
		e.stopPropagation();
	});
	$(':not(#expand)').on('click', function(e){
		if (displayed) {
			hideIt();
			displayed = false;
		}
		e.stopPropagation();
	});

	window.doTheStuff = function() {
		var searchPhrase = $('#searchText').val();
		getData(searchPhrase);
		return false;
	};

	function worker() {
		doTheStuff();
		setTimeout(worker, 10000);
	};
	worker();
});
