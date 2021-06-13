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

function message(text) {
    if(text) alert(text);
}

function changeUser(id){
    var content = JSON.parse('{"Id": "", "Username": "","Password": "", "Permissions": "", "FirstName": "","LastName": ""}');

    var tr = id.parentElement.parentElement;

    var properties = [];
    for(var i = 0; i < tr.childElementCount - 1; i++){
        properties[i] = tr.children[i].children[0].value;
    }

    var i = 0;
    for (var key in content) {
        if (content.hasOwnProperty(key)) {
            content[key] = properties[i];
            i++;
        }
    }

    sendAsync("JSON?page=profile&action=editUser", "POST", JSON.stringify(content), "application/json", null);
}

function deleteUser(id){
    var content = JSON.parse('{"Id": "' + id + '"}');

    sendAsync("JSON?page=profile&action=deleteUser", "POST", JSON.stringify(content), "application/json", null);
}

function deleteBook(ISBN){
    var content = JSON.parse('{"ISBN": "' + ISBN + '"}');

    sendAsync("JSON?page=profile&action=deleteBook", "POST", JSON.stringify(content), "application/json", null);
}

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

            return;
        }
        else if(field.value.length >= 3){
            var fieldResult = document.getElementById(fieldID+"_Result");
            var result = "<div class='fieldHelper' onclick='setSugestion(\"" + fieldID + "\", \"Ładuję dane...\")'>" + "Ładuję dane..." + "</div>";

            fieldResult.innerHTML = result;
        }
    }
}

function setSugestion(fieldID, data){
    var field = document.getElementById(fieldID);
    field.value = data;
}

function sendAsync (url, method, data, dataType, elementId){ //TODO ogarnąć callback żeby zwracało wynik dopiero po
    // wykoaniu
    method = method || "GET";
    data = data || null;
    dataType = dataType || "text/plain";
    elementId = elementId || null;

    if(!window.XMLHttpRequest){
        return null;
    }

    var requester = new XMLHttpRequest();

    requester.open(method, url);
    requester.setRequestHeader("Content-Type", dataType);

    requester.onreadystatechange = function () {
        if(elementId){
            el = document.getElementById(elementId);
            el.style.display = "block";
            el.innerHTML = "Ładuję dane...";
        }

        if (requester.readyState === 4){
            if (requester.status === 200){
                message(requester.responseText);
            }
        }
    }

    requester.send(data);
    return requester;
}

window.onload = function changeInputType(){
    var field = document.getElementById("tableUsersBody");

    if (field != null){
        var thead = field.children["0"];
        var trHead = thead.children[0].children;

        var tbody = field.children["1"];

        for (var i = 0; i < trHead.length; i++){
            if(trHead[i].textContent == "Id"){
                for(var j = 0; j < tbody.childElementCount; j++){
                    var trBody = tbody.children[j].children;
                    trBody[i].children[0].disabled = true;
                }

            }
            else if(trHead[i].textContent == "Password") {
                for(var j = 0; j < tbody.childElementCount; j++){
                    var trBody = tbody.children[j].children;
                    trBody[i].children[0].type = "password";
                    trBody[i].children[0].placeholder = "bez zmiany hasła";
                }
            }
        }
    }
}