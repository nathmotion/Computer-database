/**
 * 
 */

$("#addButton").click(function() {
	var expr = '/^[a-z0-9_-]{3,16}$/';
	var exprName = new RegExp("[!@#$%^&*()<>]");
	if (exprName.test($("#computerName").val())) {
		alert(" nom de l'ordinateur  invalide !")
	}	
});

// document.getElementById("addButton").onclick=function() {
// var name=new String(document.getElementById("computerName").value)
// var introduced = document.getElementById("continued").value;
// var discontinued = document.getElementById("discontinued").value;
// var exprName = new RegExp("[!@#$%^&*()<>]");
// var expr='/^[a-z0-9_-]{3,16}$/';
// var dateRexpr = '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[-
// /.](19|20)dd$';
// if (dateRexpr.test(introduced)) {
// alert('date introduite n\'est pas un format correct !');
// return false;
// }
// if (dateRexpr.test(discontinued)) {
// alert('date de retrait n\'est pas un format correct !');
// return false;
// }
// if(!expre.test(name)){
// alert("Nom Ordinateur invalide ");
// return false;
// }
// if (new Date(introduced).getTime() > new Date(discontinued).getTime()) {
// alert('La date de discontinued doit etre plus grande que celle de continued')
// return false;
// }
// return true;
// };
