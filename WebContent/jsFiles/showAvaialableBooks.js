
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
				document.getElementById("missingBooksMessage").style.display="none";
				document.getElementById("showAvailableBooksLabels").style.display="none";
			    if (responseJson!==null){
				document.getElementById("infoMessage").style.display="block";
				document.getElementById("showAvailableBooksLabels").style.display="block";
				 $.each(responseJson, function(index, sortedBooks) { 
			            $("<tr>").appendTo($("#showAvaialbleBooks"))                    
			                .append($("<td>").text(sortedBooks.title))  
			                .append($("<td>").text(sortedBooks.author)) 
			                .append($("<td>").text(sortedBooks.copies));
			        });
				}
				else{
					document.getElementById("missingBooksMessage").style.display="block";
				}
			
			
		}
    });
    });