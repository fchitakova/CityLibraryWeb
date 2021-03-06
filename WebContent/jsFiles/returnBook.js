$(document).ready(function() {
	showElements([ 'readerNameSubmit' ]);
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
				url : "/CityLibraryWeb/returnBook",
				type : "POST",
				dataType : "text",
				data : {
					"readerName" : readerName
				},
				success : function(readerValidityObject) {
					$('#booksTable').html("");
					hideElements([ 'notValidReaderName', 'notRegisteredReader',
							'notAnyBooksMessage', 'showResult' ]);
					if (checkReturningReaderValidity(readerValidityObject) === true) {
						printReadersBook();
					}

				}
			});

		});

function checkReturningReaderValidity(readerValidityObject) {
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




$(document).on('click','#submitBookButton',function() {
			var chosenBook = $('input[name=booksToChoose]:checked',
					'#showResult').val();
			if (chosenBook == null) {
				showElements([ 'invalidChoice' ]);
				return;
			} else {
				$.ajax({
					url : "/CityLibraryWeb/returnBook",
					type : "POST",
					dataType : "json",
					data : {
						"chosenBook" : JSON.stringify(chosenBook)
					},
					success : function() {
						showElements([ 'successfullyReturnedBook' ]);
					}
				});
			}
		});

function printReadersBook() {
	$.ajax({
		url : "/CityLibraryWeb/returnBook",
		type : "POST",
		dataType : "json",
		success : function(books) {
			var size=Object.keys(books).length;
			if (size !== 0) {
				showElements([ 'showResult', 'booksLabels']);
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
