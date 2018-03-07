/**
 * 
 */
$(document).submit(function(){
	var introduced = $('#introduced').val()
	var discontinued = $('#discontinued').val()
		if(new Date(introduced).getTime()>new Date($(discontinued).getTime())){
			 alert('La date de discontinued doit etre plus grande que celle de continued')
		}
});

$(document).ready(function(){
	$("#formAddComputer").validate({
		rules:{
			computerName:{
					required:true
			},
			
			
		}

	})
});