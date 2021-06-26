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
function setHint(elementId, responseText){
    var elem = document.getElementById(elementId)
    var result = "";
    if(responseText != ""){
        var response = JSON.parse(responseText);
        var fieldElementId = elementId.replace("_Result","");
        for(var i = 0; i < response.hint.length; i++){
            result += "<div class='fieldHelper' "
                + "onclick='setSugestion(\"" + fieldElementId + "\", \"" + response.hint[i]+"\")'>"
                + response.hint[i] + "</div>";
        }
        elem.innerHTML = result;
    }
}

function message(jsonMessage) {
    if(jsonMessage){
        var jsonContent = JSON.parse(jsonMessage);
        if(jsonContent.Action){
            switch(jsonContent.Action){
                case "deleteUser":
                    var tr = document.getElementById("userID_" + jsonContent.Id);
                    tr.parentNode.deleteRow(tr.rowIndex - 1);
                    break;

                case "deleteBook":
                    var tr = document.getElementById("deleteBook_" + jsonContent.Id);
                    tr.parentNode.parentNode.parentNode.deleteRow(tr.rowIndex - 1);
                    break;
                // case "addUser": //TODO przenieść akcje z wcześniejszego rekordu ze zmianą id w akcjach
                //     var tr = document.getElementById("tableUserAdd").rows[1];
                //     tr = tr.cloneNode(true);
                //     tr.children[0].display = "table-cell";
                //
                //
                //     var targetTable = document.getElementById("tableUsersBody");
                //     targetTable.appendChild(tr);
                //     break;
            }
        }
        if(jsonContent.Response)
            alert(jsonContent.Response);
    }
}

const IDregex = /[a-zA-Z]+[_]{1}/;

function bookUserButton(element, action){
    switch (action) {
        case "editUser": case "addUser":
            var content = JSON.parse('{"Id": "", "Username": "","Password": "", "Permissions": "", "FirstName": "","LastName": ""}');
            break;

        case "editBook": case "addBook":
            var content = JSON.parse('{"ISBN": "", "Title": "","Release_Date": "", "Author": "", "Publishing_House": "","OwnerID": ""}');
            break;

        case "deleteUser":
            var id = element.id.replace(IDregex,"");
            var content = JSON.parse('{"Id": "' + id + '"}');
            sendAsync("JSON?page=profile&action=deleteUser", "POST", JSON.stringify(content), "application/json", null);
            return;

        case "deleteBook":
            var id = element.id.replace(IDregex,"");
            var content = JSON.parse('{"ISBN": "' + id + '"}');
            sendAsync("JSON?page=profile&action=deleteBook", "POST", JSON.stringify(content), "application/json", null);
            return;
    }

    var tr = element.parentElement.parentElement;

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
        if(fieldID.value){
            if (fieldID.value.length < 3){
                var element = document.getElementById(fieldID.id+"_Result").childNodes;
                var elementLength = element.length;
                for (var i = 0; i < elementLength; i++){
                    element[0].remove();
                }
            }
            else if(fieldID.value.length >= 3){
                var fieldResult = document.getElementById(fieldID.id+"_Result");
                var resultField = "<div class='fieldHelper'>" + "Ładuję dane..." + "</div>";

                fieldResult.innerHTML = resultField;

                sendAsync("JSON?action=hintHandler", "POST", "{\"" + fieldID.id + "\": " +
                    "\"" + fieldID.value + "\"}", "application/json", fieldID.id+"_Result");
            }
        }
    }
}

function setSugestion(fieldID, data){
    var field = document.getElementById(fieldID);
    field.value = data;
}

function sendAsync (url, method, data, dataType, elementId){
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
        if (requester.readyState === 4){
            if (requester.status === 200){
                if(elementId){
                    setHint(elementId, requester.responseText);
                }
                else
                    message(requester.responseText);
            }
        }
    }

    requester.send(data);
    return requester;
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

function changeInputFor(tHead, tBody){
    const urlSearchParams = new URLSearchParams(window.location.search);
    const params = Object.fromEntries(urlSearchParams.entries());
    const page = params.page;

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
                trBody[i].children[0].max = 2;
            }
        }
        else if(tHead[i].textContent == "OwnerID"){
            for(var j = 0; j < tBody.childElementCount; j++){
                var trBody = tBody.children[j].children;
                trBody[i].children[0].type = "number";
                trBody[i].children[0].min = 0;
                if(page == "browseBooks")
                    trBody[i].children[0].disabled = true;
            }
        }
        // else if(tHead[i].textContent == "ISBN"){
        //     for(var j = 0; j < tBody.childElementCount; j++){
        //         var trBody = tBody.children[j].children;
        //         trBody[i].children[0].disabled = disabled;
        //     }
        // }
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