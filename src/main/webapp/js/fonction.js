function check() {

    document.getElementById("CheckVentesEnCours").disabled =true
    document.getElementById("CheckVentesNonDebutees").disabled =true
    document.getElementById("CheckVentesTerminees").disabled =true

    document.getElementById("CheckEncheresOuvertes").disabled =false
    document.getElementById("CheckEncheresEnCours").disabled =false
    document.getElementById("CheckEncheresRemportees").disabled =false

}

function checkBoxVente() {

    document.getElementById("CheckVentesEnCours").disabled =false
    document.getElementById("CheckVentesNonDebutees").disabled =false
    document.getElementById("CheckVentesTerminees").disabled =false


    document.getElementById("CheckEncheresOuvertes").disabled =true
    document.getElementById("CheckEncheresEnCours").disabled =true
    document.getElementById("CheckEncheresRemportees").disabled =true

    document.getElementById("CheckEncheresOuvertes").checked =false
    document.getElementById("CheckEncheresEnCours").checked =false
    document.getElementById("CheckEncheresRemportees").checked =false


}