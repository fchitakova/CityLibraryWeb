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

$(document)
.ready(
    function() {
        $
            .ajax({
                url: "/CityLibraryWeb/availableBooks",
                dataType: "json",
                type: "POST",
                success: function(responseJson) {
                    $('#showAvailableBooks').html("");
                    var elementsToHide = ['missingBooksMessage', 'showAvailableBooksLabels'];
                    hideElements(elementsToHide);
                    var size = Object.keys(responseJson).length;
                    if (size != 0) {
                    	var elementsToShow=['infoMessage','showAvailableBooksLabels'];
                        showElements(elementsToShow);
                        $
                            .each(
                                responseJson,
                                function(index,
                                    sortedBooks) {
                                    $("<tr>")
                                        .appendTo(
                                            $("#showAvaialbleBooks"))
                                        .append(
                                            $(
                                                "<td>")
                                            .text(
                                                sortedBooks.title))
                                        .append(
                                            $(
                                                "<td>")
                                            .text(
                                                sortedBooks.author))
                                        .append(
                                            $(
                                                "<td>")
                                            .text(
                                                sortedBooks.copies));
                                });
                    } else {
                    	showElements(['missingBooksMessage']);
                    }

                }
            });
    });