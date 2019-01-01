var modal1=document.getElementById('seeAllBooksByTitle-modal1');
 
 var modal1Button=document.getElementById('seeAllBooksByTitleButton');
 
 var modal1CloseButton=document.getElementById('modal1-close');
 
 modal1Button.addEventListener('click',openModal1);
 
 modal1CloseButton.addEventListener('click',closeModal1);
 
 function openModal1(){
	 modal1.style.display='block';
 }
 
 function closeModal1(){
	 modal1.style.display='none';
 }