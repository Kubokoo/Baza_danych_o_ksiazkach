// function formatISBN(fieldID){ //TODO przenieść do backend i wstawiać myślniki po przysłaniu
//     let element = document.getElementById(fieldID);
//     let value = element.value;
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
//     let field = document.getElementById(fieldID);
//     if (field.value.length == 10){
//         let res = 0;
//         for(let i = 0; i < field.value.length; i++){
//             res += field.value[i] * i + 1;
//         }
//         res = res % 11;
//     }
//     else if(field.value.length == 13) {
//
//     }
// }

function translateColumns(tHead){
    for (let i = 0; i < tHead.length; i++){
        for (let key in translatons) {
            if (translatons.hasOwnProperty(key)) {
                if(key == tHead[i].textContent){
                    tHead[i].textContent = translatons[key];
                }
            }
        }

    }
}

function setHint(elementId, responseText){
    let elem = document.getElementById(elementId)
    let result = "";
    if(responseText != ""){
        let response = JSON.parse(responseText);
        let fieldElementId = elementId.replace("_Result","");
        for(let i = 0; i < response.hint.length; i++){
            result += "<div class='fieldHelper' "
                + "onclick='setSugestion(\"" + fieldElementId + "\", \"" + response.hint[i]+"\")'>"
                + response.hint[i] + "</div>";
        }
        elem.innerHTML = result;
    }
}

function message(jsonMessage) {
    if(jsonMessage){
        const jsonContent = JSON.parse(jsonMessage);
        if(jsonContent.Action){
            let tr;
            switch(jsonContent.Action){
                case "deleteUser":
                    tr = document.getElementById("userID_" + jsonContent.Id);
                    tr.parentNode.deleteRow(tr.rowIndex - 1);
                    break;

                case "deleteBook":
                    tr = document.getElementById("deleteBook_" + jsonContent.Id);
                    tr.parentNode.parentNode.parentNode.deleteRow(tr.rowIndex - 1);
                    break;
                // case "addUser": //TODO przenieść akcje z wcześniejszego rekordu ze zmianą id w akcjach
                //     let tr = document.getElementById("tableUserAdd").rows[1];
                //     tr = tr.cloneNode(true);
                //     tr.children[0].display = "table-cell";
                //
                //
                //     let targetTable = document.getElementById("tableUsersBody");
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
    let content, id;
    switch (action) {
        case "editUser": case "addUser":
            content = JSON.parse('{"Id": "", "Username": "","Password": "", "Permissions": "", "FirstName": "","LastName": ""}');
            break;

        case "editBook": case "addBook":
            content = JSON.parse('{"ISBN": "", "Title": "","Release_Date": "", "Author": "", "Publishing_House": "","OwnerID": ""}');
            break;

        case "deleteUser":
            id = element.id.replace(IDregex,"");
            content = JSON.parse('{"Id": "' + id + '"}');
            sendAsync("JSON?page=profile&action=deleteUser", "POST", JSON.stringify(content), "application/json", null);
            return;

        case "deleteBook":
            id = element.id.replace(IDregex,"");
            content = JSON.parse('{"ISBN": "' + id + '"}');
            sendAsync("JSON?page=profile&action=deleteBook", "POST", JSON.stringify(content), "application/json", null);
            return;
    }

    let tr = element.parentElement.parentElement;

    let properties = [];
    for(let i = 0; i < tr.childElementCount - 1; i++){
        properties[i] = tr.children[i].children[0].value;
    }

    let i = 0;
    for (let key in content) {
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
                let element = document.getElementById(fieldID.id+"_Result").childNodes;
                let elementLength = element.length;
                for (let i = 0; i < elementLength; i++){
                    element[0].remove();
                }
            }
            else if(fieldID.value.length >= 3){
                let fieldResult = document.getElementById(fieldID.id+"_Result");
                let resultField = "<div class='fieldHelper'>" + "Ładuję dane..." + "</div>";

                fieldResult.innerHTML = resultField;

                sendAsync("JSON?action=hintHandler", "POST", "{\"" + fieldID.id + "\": " +
                    "\"" + fieldID.value + "\"}", "application/json", fieldID.id+"_Result");
            }
        }
    }
}

function setSugestion(fieldID, data){
    let field = document.getElementById(fieldID);
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

    let requester = new XMLHttpRequest();

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

    for (let i = 0; i < tHead.length; i++){
        if(tHead[i].textContent == "Id"){
            for(let j = 0; j < tBody.childElementCount; j++){
                let trBody = tBody.children[j].children;
                trBody[i].children[0].disabled = true;
            }

        }
        else if(tHead[i].textContent == "Password") {
            for(let j = 0; j < tBody.childElementCount; j++){
                let trBody = tBody.children[j].children;
                trBody[i].children[0].type = "password";
                trBody[i].children[0].placeholder = "bez zmiany hasła";
            }
        }
        else if(tHead[i].textContent == "Release_Date"){
            for(let j = 0; j < tBody.childElementCount; j++){
                let trBody = tBody.children[j].children;
                trBody[i].children[0].type = "date";
            }
        }
        else if(tHead[i].textContent == "Permissions"){
            for(let j = 0; j < tBody.childElementCount; j++){
                let trBody = tBody.children[j].children;
                trBody[i].children[0].type = "number";
                trBody[i].children[0].min = 0;
                trBody[i].children[0].max = 2;
            }
        }
        else if(tHead[i].textContent == "OwnerID"){
            for(let j = 0; j < tBody.childElementCount; j++){
                let trBody = tBody.children[j].children;
                trBody[i].children[0].type = "number";
                trBody[i].children[0].min = 0;
                if(page == "browseBooks")
                    trBody[i].children[0].disabled = true;
            }
        }
        // else if(tHead[i].textContent == "ISBN"){
        //     for(let j = 0; j < tBody.childElementCount; j++){
        //         let trBody = tBody.children[j].children;
        //         trBody[i].children[0].disabled = disabled;
        //     }
        // }
    }
}

window.onload = function changeInputType(){
    let fieldUsers = document.getElementById("tableUsersBody");
    let fieldBooks = document.getElementById("tableBooksBody");

    let tHead,tBody;
    if (fieldUsers != null){
        tHead = fieldUsers.children["0"].children[0].children;
        tBody = fieldUsers.children["1"];

        changeInputFor(tHead, tBody);
        translateColumns(tHead);
    }
    if(fieldBooks != null){
        tHead = fieldBooks.children["0"].children[0].children;
        tBody = fieldBooks.children["1"];

        changeInputFor(tHead, tBody);
        translateColumns(tHead);
    }
}