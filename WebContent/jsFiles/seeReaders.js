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
.ready( function() {
        $.ajax({
                url: "/CityLibraryWeb/seeReaders",
                dataType: "json",
                type: "POST",
                success: function(readers) {
                    $('#showAllReaders').html("");
                    var elementsToHide = ['infoMessage', 'missingReadersMessage','showReaderName'];
                    hideElements(elementsToHide);
                    var size = Object.keys(readers).length;
                    if (size != 0) {
                    	var elementsToShow=['infoMessage','showReaderName'];
                        showElements(elementsToShow);
                        $.each(readers, function(index,reader) {
                                    $("<li>") .appendTo($("#showAllReaders"))
                                        .append(reader.name);
                                });
                    } else {
                    	showElements(['missingBooksMessage']);
                    }

                }
            });
    });