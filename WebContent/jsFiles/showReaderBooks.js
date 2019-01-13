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

$(document).on(
		'click',
		'#submitReaderNameButton',
		function() {
			var readerName = $("#readerName").val();
			$.ajax({
				url : "/CityLibraryWeb/showReaderBooks",
				type : "POST",
				dataType : "text",
				data : {
					"readerName" : readerName
				},
				success : function(readerValidityObject) {
					$('#booksTable').html("");
					hideElements([ 'notValidReaderName', 'notRegisteredReader',
							'notAnyBooksMessage', 'showResult' ]);
					if (checkReaderValidity(readerValidityObject) === true) {
						printReadersBook();

					}

				}
			});

		});

function checkReaderValidity(readerValidityObject) {
	readerValidityObject = JSON.parse(readerValidityObject);
	if (readerValidityObject.validReaderName === false) {
		showElements([ 'notValidReaderName' ]);
		return false;
	}
	if (readerValidityObject.registeredReader === false) {
		showElements([ 'notRegisteredReader' ]);
		return false;
	}
	if (readerValidityObject.hasAnyTakenBooks === false) {
		showElements['notAnyBooksMessage'];
	}
	return true;
}

function printReadersBook() {
	$.ajax({
		url : "/CityLibraryWeb/showReaderBooks",
		type : "POST",
		dataType : "json",
		success : function(books) {
			var size = Object.keys(books).length;
			if (size !== 0) {

				showElements([ 'showResult', 'booksLabels' ]);
				$.each(books, function(index, book) {
					$("<tr>").appendTo($("#booksTable")).append(
							$("<td>").text(book.title)).append(
							$("<td>").text(book.author));

				});
			} else {
				showElements([ 'notAnyBooksMessage' ]);
			}
		}
	});
}
