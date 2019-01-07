$( document ).ready(function() {
    console.log( "ready!" );
    $('#showBooksButton').click(function() {
        var sortingOrder = $('input[name=sortingOrder]:checked', '#chooseSortingOrder').val();
        if (sortingOrder == null) {
            var infoMessage = '<%=languageResources.getResource(Constants.INVALID_CHOICE)%>';
            alert(infoMessage);
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
                    $("<h2>").appendTo($("#showBooks"))
                        .append("<%=languageResources.getResource(Constants.PRINT_ALL_BOOKS)%>");

                    var $table = $("<table>").appendTo($("#showBooks"))
                        .append($("<th>").text("<%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%>"))
                        .append($("<th>").text("<%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%>"));
                    $.each(responseJson, function(index, sortedBooks) {
                        $("<tr>").appendTo($table)
                            .append($("<td>").text(sortedBooks.title))
                            .append($("<td>").text(sortedBooks.author));
                    });
                } else {
                    var infoMessage = '<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>';
                    $("<h2>").appendTo($("#showBooks")).append(infoMessage);
                }

            }

        });
}
    });
});
    