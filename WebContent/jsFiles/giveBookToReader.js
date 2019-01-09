$(document).ready(function() {
	$("#menu").load("index.jsp");
});

$(document).ready(function() {
    $.ajax({
        url: "/CityLibraryWeb/availableBooks",
        type: "GET",
        dataType: "json",
        success: function(books) {
            if (books !== null) {
                document.getElementById("readerNameSubmit").style.display = "block";
            } else {
                document.getElementById("notAnyBooksMessage").style.display = "block";
            }
        }
    });
});

$(document).on('click', '#submitReaderNameButton', function() {
	var readerName = $("#readerName").val();
	$.ajax({
		url : "/CityLibraryWeb/giveBook",
		type : "GET",
		dataType : "text",
		data : {
			"readerName" : readerName
		},
		success : function(readerValidityObject) {
			$('#booksTable').html("");
			document.getElementById("showResult").style.display = "none";
			if (checkReaderValidity(readerValidityObject) === true) {
				printAvailableBooksOptions();
			}

		}
	});

});


$(document).on('click','#submitBookButton',function(){
	var chosenBook= $('input[name=booksToChoose]:checked', '#showResult').val();
    if (chosenBook == null) {
    	document.getElementById("invalidChoice").style.display="block";
        return;
    }
    else{
	 $.ajax({
		 url:"/CityLibraryWeb/giveBook",
		 type: "POST",
		 dataType: "json",
		 data: {
             "chosenBook": JSON.stringify(chosenBook)
         },
        success:function(){
        	document.getElementById("successfullyGivenBook").style.display="block";
        }
      });
    }
});

function printAvailableBooksOptions() {
    $.ajax({
            url: "/CityLibraryWeb/availableBooks",
            type: "POST",
            dataType: "json",
            success: function(books) {
                if (books !== null) {
                    document.getElementById("showResult").style.display = "block";
                    document.getElementById("booksLabels").style.display = "block";
                    $.each(books, function(index, book) {
                            $("<tr>").appendTo($("#booksTable"))
                            .append('<input type="radio" name="booksToChoose" value="'+book.author+" "+book.title+'">').append($("<td>").text(book.title))
                            .append($("<td>").text(book.author));
                  
                        });
                    }
                  else{
                        document.getElementById("notAnyBooksMessage").style.display = "block";
                    }
            }
    });
   }
			



function checkReaderValidity(readerValidityObject) {
	readerValidityObject = JSON.parse(readerValidityObject);
	document.getElementById("notValidReaderName").style.display = "none";
	document.getElementById("notRegisteredReader").style.display = "none";
	if (readerValidityObject.validReaderName === false) {
		document.getElementById("notValidReaderName").style.display = "block";
		return false;
	}
	if (readerValidityObject.registeredReader === false) {
		document.getElementById("notRegisteredReader").style.display = "block";
		return false;
	}
	return true;
}