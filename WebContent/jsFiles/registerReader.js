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


$(document).on('click','#submitReaderNameButton',
		function() {
			var readerName = $("#readerName").val();
			$.ajax({
				url : "/CityLibraryWeb/registerReader",
				type : "POST",
				dataType : "json",
				data : {
					"readerName" : readerName
				},
				success : function(readerValidityObject) {
					hideElements(['notValidReaderName','notRegisteredReader','successfulRegistration']);
					if(readerValidityObject.validReaderName==false){
						showElements(['notValidReaderName']);
						return;
					}
					if(readerValidityObject.registeredReader==false){
						showElements(['notRegisteredReader']);
						return;
					}
						showElements(['successfulRegistration']);
				}
			});

		});


