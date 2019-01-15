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

function processInput() {
	var regex = new RegExp("^(\\w+\\s*\\w*)+$");
	var searchText = $('#searchedText').val();
	var result = regex.test(searchText);
	if (result == false) {
		showElements([ 'invalidInputMessage' ]);
		return;
	}
	var searchType = $('input[name=searchType]:checked', '#chooseSortingOrder')
			.val();
	if (searchType == null) {
		showElements([ 'invalidChoice' ]);
		return;
	}
	$.ajax({
		url : "/CityLibraryWeb/searchBooks",
		data : {
			"searchType" : JSON.stringify(searchType),
			"searchText":  JSON.stringify(searchText)
		},
		dataType : "json",
		type : "POST",
		success : function(responseJson) {
			$("#fillBooksInfo").html("");
			hideElements([ 'invalidChoice', 'invalidInputMessage',
					'notAnyBooksMessage' ]);
			var size = Object.keys(responseJson).length;
			if (size == 0) {
				showElements([ 'notAnyBooksMessage' ]);
			}
			$.each(responseJson, function(index, sortedBooks) {
				$("<tr>").appendTo($("#fillBooksInfo")).append(
						$("<td>").text(sortedBooks.title)).append(
						$("<td>").text(sortedBooks.author));
			});
		}
	});
}