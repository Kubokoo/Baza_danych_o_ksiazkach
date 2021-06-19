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

function bookUserButton(id, action){
    switch (action) {
        case "editUser": case "addUser":
            var content = JSON.parse('{"Id": "", "Username": "","Password": "", "Permissions": "", "FirstName": "","LastName": ""}');
            break;

        case "editBook": case "addBook":
            var content = JSON.parse('{"ISBN": "", "Title": "","Release_Date": "", "Author": "", "Publishing_House": "","OwnerID": ""}');
            break;

        case "deleteUser":
            var content = JSON.parse('{"Id": "' + id + '"}');
            sendAsync("JSON?page=profile&action=deleteUser", "POST", JSON.stringify(content), "application/json", null);
            return;

        case "deleteBook":
            var content = JSON.parse('{"ISBN": "' + id + '"}');
            sendAsync("JSON?page=profile&action=deleteBook", "POST", JSON.stringify(content), "application/json", null);
            return;
    }

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

    switch (action) {
        case "addUser":
            sendAsync("JSON?page=profile&action=addUser", "POST", JSON.stringify(content), "application/json", null);
            break;

        case "editUser":
            sendAsync("JSON?page=profile&action=editUser", "POST", JSON.stringify(content), "application/json", null);
            break;

        case "addBook":
            sendAsync("JSON?page=profile&action=addBook", "POST", JSON.stringify(content), "application/json", null);
            break;

        case "editBook":
            sendAsync("JSON?page=profile&action=editBook", "POST", JSON.stringify(content), "application/json", null);
            break;
    }

}

function getSugestions(fieldID) {
    if(fieldID) {
        var field = document.getElementById(fieldID);
        if (field.value.length < 3){
            var element = document.getElementById(fieldID+"_Result").childNodes;
            var elementLength = element.length;
            for (var i = 0; i < elementLength; i++){
                element[0].remove();
            }
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

function changeInputFor(tHead, tBody){
    for (var i = 0; i < tHead.length; i++){
        if(tHead[i].textContent == "Id"){
            for(var j = 0; j < tBody.childElementCount; j++){
                var trBody = tBody.children[j].children;
                trBody[i].children[0].disabled = true;
            }

        }
        else if(tHead[i].textContent == "Password") {
            for(var j = 0; j < tBody.childElementCount; j++){
                var trBody = tBody.children[j].children;
                trBody[i].children[0].type = "password";
                trBody[i].children[0].placeholder = "bez zmiany hasła";
            }
        }
        else if(tHead[i].textContent == "Release_Date"){
            for(var j = 0; j < tBody.childElementCount; j++){
                var trBody = tBody.children[j].children;
                trBody[i].children[0].type = "date";
            }
        }
        else if(tHead[i].textContent == "Permissions"){
            for(var j = 0; j < tBody.childElementCount; j++){
                var trBody = tBody.children[j].children;
                trBody[i].children[0].type = "number";
                trBody[i].children[0].min = 0;
                trBody[i].children[0].max = 3;
            }
        }
        else if(tHead[i].textContent == "OwnerID"){
            for(var j = 0; j < tBody.childElementCount; j++){
                var trBody = tBody.children[j].children;
                trBody[i].children[0].type = "number";
                trBody[i].children[0].min = 0;
            }
        }
    }
}

function showTable(id){
    if(id.style.display == "none"){
        id.style.display = "table";
        id.style.opacity = 1;
    }
    else{
        id.style.opacity = 0;
        id.style.display = "none";
    }
}

window.onload = function changeInputType(){
    var fieldUsers = document.getElementById("tableUsersBody");
    var fieldBooks = document.getElementById("tableBooksBody");

    var tHead,tBody;
    if (fieldUsers != null){
        tHead = fieldUsers.children["0"].children[0].children;
        tBody = fieldUsers.children["1"];

        changeInputFor(tHead, tBody);
    }
    if(fieldBooks != null){
        tHead = fieldBooks.children["0"].children[0].children;
        tBody = fieldBooks.children["1"];

        changeInputFor(tHead, tBody);
    }
}