$( document ).ready(function() {
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



$( document ).ready(function() {
    $('#showBooksButton').click(function() {
        var sortingOrder = $('input[name=sortingOrder]:checked', '#chooseSortingOrder').val();
        if (sortingOrder == null) {
        	showElements(['invalidChoice']);
            return;
        }
        else{
        $.ajax({
            url: "/CityLibraryWeb/allBooks",
            data: {
                "sortingOrder": JSON.stringify(sortingOrder)
            },
            dataType: "json",
            type: "POST",
            success: function(responseJson) {
                var size=Object.keys(responseJson).length;
                if (size!=0) {
                	showElements(['infoMessage','showBooks',])
                	$("#fillBooksInfo").html("");
                	hideElements(['invalidChoice']);
                    $.each(responseJson, function(index, sortedBooks) {
                        $("<tr>").appendTo($("#fillBooksInfo"))
                            .append($("<td>").text(sortedBooks.title))
                            .append($("<td>").text(sortedBooks.author));
                    });
                } else {
                	showElements(['errorMessage']);
                }

            }

        });
}
    });
});
    