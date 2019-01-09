
$( document ).ready(function() {
  $("#menu").load("index.jsp");
});

$(document).ready(
    function(){
    	$.ajax({
			url :"/CityLibraryWeb/availableBooks",
		    dataType: "json",
			type : "POST",
			success : function(responseJson) {
				$('#showAvailableBooks').html("");
			if (responseJson!==null){ 
				document.getElementById("infoMessage").style.visibility="visible";
				document.getElementById("showAvailableBooks").style.visibility="visible";
				 $.each(responseJson, function(index, sortedBooks) { 
			            $("<tr>").appendTo($("#showAvailableBooks"))                    
			                .append($("<td>").text(sortedBooks.title))  
			                .append($("<td>").text(sortedBooks.author)) 
			                .append($("<td>").text(sortedBooks.copies));
			        });
				}
				else{
					document.getElementById("missingBooksMessage").style.visibility="visible";
				}
			
			
		}
    });
    });