// function formatISBN(fieldID){ //TODO przenieść do backend i wstawiać myślniki po przysłaniu
//     var element = document.getElementById(fieldID);
//     var value = element.value;
//     switch (value.length) {
//         case 3:
//             // value+="-";
//             element.value+="-";
//             break;
//
//         default:
//             break;
//     }
// }

// function validateISBN(fieldID){ //TODO Przenieść do backend
//     var field = document.getElementById(fieldID);
//     if (field.value.length == 10){
//         var res = 0;
//         for(var i = 0; i < field.value.length; i++){
//             res += field.value[i] * i + 1;
//         }
//         res = res % 11;
//     }
//     else if(field.value.length == 13) {
//
//     }
// }

function getSugestions(fieldID) {
    if(!fieldID) return;
    else {
        var field = document.getElementById(fieldID);
        if (field.value.length < 3){
            var element = document.getElementById(fieldID+"_Result").childNodes;
            var elementLength = element.length;
            for (var i = 0; i < elementLength; i++){
                element[0].remove();
            }
            // element[0].style.display = "none";

            return;
        }
        else if(field.value.length >= 3){
            var fieldResult = document.getElementById(fieldID+"_Result");
            var result = "<div class='fieldHelper' onclick='setSugestion(\"" + fieldID + "\", \"Ładuję dane...\")'>" + "Ładuję dane..." + "</div>"; // odpowiedz.sugestia[i]

            fieldResult.innerHTML = result;
        }
    }
}

function setSugestion(fieldID, data){
    var field = document.getElementById(fieldID);
    field.value = data;
}

function wyslijAsynchronicznie (url, funkcjeZwrotne, metoda, wysylaneDane, typDanych){
    metoda = metoda || "GET";
    wysylaneDane = wysylaneDane || null;
    typDanych = typDanych || "text/plain";

    if(!window.XMLHttpRequest){
        return null;
    }

    var requester = new XMLHttpRequest();

    requester.open(metoda, url);
    requester.setRequestHeader("Content-Type", typDanych);

    requester.onreadystatechange = function () {
        el = document.getElementById("wyniki");
        el.style.display = "block";
        el.innerHTML = "Ładuję dane...";

        if (requester.readyState === 4){
            if (requester.status === 200){
                funkcjeZwrotne.success(requester);
            }
            else {
                funkcjeZwrotne.falure(requester);
            }
        }
    }

    requester.send(wysylaneDane);
    return requester;
}

function pobierzSugestie() {
    var wartosc = {wartosc: document.getElementById("pole").value};
    if (wartosc.wartosc.length == 0){
        var elementy = document.getElementsByClassName("lista");
        var elementyLength = elementy.length;
        for (var i = 0; i < elementyLength; i++){
            elementy[0].remove();
        }
        return;
    }
    var wynik = {
        success: function (requester) {
            var rezultat = "";
            var odpowiedz = JSON.parse(requester.responseText);
            for(var i = 0; i < odpowiedz.sugestia.length; i++){
                rezultat += "<div class='lista'>" + odpowiedz.sugestia[i] + "</div>";
            }
            field.innerHTML = rezultat;
        },
        falure: function (requester){
            alert("Wystąpił błąd: " + requester.status);
        }
    }
    var typDanych = 'application/json';
    wyslijAsynchronicznie("sugestieJSON", wynik, "POST", JSON.stringify(wartosc), typDanych);
}