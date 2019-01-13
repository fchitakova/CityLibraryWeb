$(document).ready(function() {
	$("#menu").load("index.jsp");
});

function hideElements(elementsId) {
	var elementsNumber = elementsId.length;
	for ( var i in elementsId) {
		document.getElementById(elementsId[i]).style.display = "none";
	}
}

function showElements(elementsId) {
	var elementsNumber = elementsId.length;
	for ( var i in elementsId) {
		document.getElementById(elementsId[i]).style.display = "block";
	}
}


$(document).on('click', '#submit', function() {
	var title = $('#title').val();
	var author=$('#author').val();
	$.ajax({
		url : "/CityLibraryWeb/addBook",
		type : "POST",
		dataType : "text",
		data : {
			"title":title,
			"author":author
		},
		success : function(successfullyAdded) {
			hideElements(['successfullyAddedBook','notValidTitleOrAuthor']);
			if (successfullyAdded === true) {
				showElements(['successfullyAddedBook']);
			}
			else{
				showElements(['notValidTitleOrAuthor']);
			}

		}
	});

});