$( document ).ready(function() {
  $("#menu").load("index.jsp");
});


$(document).ready(function(){
	if (getAvailableBooks()!==null){
		    document.getElementById("readerNameSubmit").style.visibility="visible";
			}
			else{
			document.getElementById("notAnyBooksMessage").style.visibility="visible";		
	     	}
	});

	
function getAvailableBooks(){
		$.ajax({
			url :"/CityLibraryWeb/availableBooks",
			type : "POST",
		    dataType: "json",
			success:
			 function(returnedBooks){
					return returnedBooks;
			}
	});
}		
	

	
	
	$(document).on('click', '#submitReaderNameButton', function(){
		var readerName=$("#readerName").val();
		$.ajax({
			url:"/CityLibraryWeb/giveBook",
			type:"GET",
			dataType:"text",
			data:readerName ,
				  success:function(readerValidityObject){
					  alert(readerName);
					  $('#showResult').html("");
					  if(checkReaderValidity(readerValidityObject)===true){
						  printAvailableBooksOptions();
						}
				
				  }
			});
			
		});
	
	
	function printAvailableBooksOptions(){
		document.getElementById("showResult").style.visibility="visible";
		    var availableBooks=getAvailableBooks();
			 $.each(JSON.parse(availableBooks), function(index, availableBook) {
		            $("<tr>").appendTo($("#booksTable"))
		                .append("<input type=\"radio\" name=\"booksToChoose\" value=index>")
		                .append($("<td>").text(availableBook.title))      
		                .append($("<td>").text(availableBook.author));
		        });
	}
	
	
	
	function checkReaderValidity(readerValidityObject){
		 var parsedReaderValidity=JSON.parse(readerValidityObject);
		  if(parsedReaderValidity.isValidReaderName===false){
			  $("<h2>").appendTo($("#showResult")).append('<%=languageResources.getResource(Constants.NOT_VALID_READER_NAME)%>');
            return false;
		  }
		  if(parsedReaderValidity.isRegisteredReader===false){
			  $("<h2>").appendTo($("#showResult")).append('<%=languageResources.getResource(Constants.NOT_REGISTERED_READER)%>');
				return false;
			}
			return true;
	}