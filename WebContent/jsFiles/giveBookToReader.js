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


$(document).ready(function() {
	$.ajax({
		url : "/CityLibraryWeb/availableBooks",
		type : "POST",
		dataType : "json",
		success : function(books) {
			if (books !== null) {
				showElements([ 'readerNameSubmit' ]);
			} else {
				showElements([ 'notAnyBooksMessage' ]);
			}
		}
	});
});

$(document).on('click', '#submitReaderNameButton', function() {
	var readerName = $("#readerName").val();
	$.ajax({
		url : "/CityLibraryWeb/giveBook",
		type : "POST",
		dataType : "text",
		data : {
			"readerName" : readerName
		},
		success : function(readerValidityObject) {
			$('#booksTable').html("");
			hideElements(['showResult']);
			if (checkReaderValidity(readerValidityObject) === true) {
				printAvailableBooksOptions();
			}

		}
	});

});

$(document).on(
		'click',
		'#submitBookButton',
		function() {
			var chosenBook = $('input[name=booksToChoose]:checked',
					'#showResult').val();
			if (chosenBook == null) {
				showElements([ 'invalidChoice' ]);
				return;
			} else {
				$.ajax({
					url : "/CityLibraryWeb/giveBook",
					type : "POST",
					dataType : "json",
					data : {
						"chosenBook" : JSON.stringify(chosenBook)
					},
					success : function() {
						showElements([ 'successfullyGivenBook' ]);
					}
				});
			}
		});

function printAvailableBooksOptions() {
	$.ajax({
		url : "/CityLibraryWeb/availableBooks",
		type : "POST",
		dataType : "json",
		success : function(books) {
			var size=Object.keys(books).length;
			if (size !== 0) {
				showElements([ 'showResult', 'booksLabels' ]);
				$.each(books, function(index, book) {
					$("<tr>").appendTo($("#booksTable")).append(
							'<input type="radio" name="booksToChoose" value="'
									+ book.author + " " + book.title + '">')
							.append($("<td>").text(book.title)).append(
									$("<td>").text(book.author));

				});
			} else {
				showElements([ 'notAnyBooksMessage' ]);
			}
		}
	});
}

function checkReaderValidity(readerValidityObject) {
	readerValidityObject = JSON.parse(readerValidityObject);
	hideElements(['notValidReaderName', 'notRegisteredReader']);
	if (readerValidityObject.validReaderName === false) {
		showElements([ 'notValidReaderName' ]);
		return false;
	}
	if (readerValidityObject.registeredReader === false) {
		showElements([ 'notRegisteredReader' ]);
		return false;
	}
	return true;
}