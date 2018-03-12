/**
 * 	======		Verification que le nom soit pas compose d'expression special =======
 */

$("#addButton").click(function() {
	var expr = '/^[a-z0-9_-]{3,16}$/';
	var exprName = new RegExp("[!@#$%^&*()<>]");
	if (exprName.test($("#computerName").val())) {
		alert(" nom de l'ordinateur  invalide !")
	}	
});
