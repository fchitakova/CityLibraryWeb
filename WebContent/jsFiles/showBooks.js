$( document ).ready(function() {
  $("#menu").load("index.jsp");
});

$( document ).ready(function() {
    $('#showBooksButton').click(function() {
        var sortingOrder = $('input[name=sortingOrder]:checked', '#chooseSortingOrder').val();
        if (sortingOrder == null) {
        	document.getElementById("invalidChoice").style.display="block";
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
                	document.getElementById("infoMessage").style.display="block";
                	$("#fillBooksInfo").html("");
                	document.getElementById("invalidChoice").style.display="none";
                	document.getElementById("showBooks").style.display="block";
                    $.each(responseJson, function(index, sortedBooks) {
                        $("<tr>").appendTo($("#fillBooksInfo"))
                            .append($("<td>").text(sortedBooks.title))
                            .append($("<td>").text(sortedBooks.author));
                    });
                } else {
                	document.getElementById("errorMessage").style.display="block";
                }

            }

        });
}
    });
});
    