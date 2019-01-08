$( document ).ready(function() {
  $("#menu").load("index.jsp");
});

$( document ).ready(function() {
    $('#showBooksButton').click(function() {
        var sortingOrder = $('input[name=sortingOrder]:checked', '#chooseSortingOrder').val();
        if (sortingOrder == null) {
        	document.getElementById("invalidChoice").style.visibility="visible";
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
                $('#showBooks').html("");
                if (responseJson !== null) {
                	document.getElementById("infoMessage").style.visibility="visible";
                	document.getElementById("showBooks").style.visibility="visible";
                    $.each(responseJson, function(index, sortedBooks) {
                        $("<tr>").appendTo($("#showBooks"))
                            .append($("<td>").text(sortedBooks.title))
                            .append($("<td>").text(sortedBooks.author));
                    });
                } else {
                	document.getElementById("errorMessage").style.visibility="visible";
                }

            }

        });
}
    });
});
    