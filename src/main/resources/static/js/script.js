const table = document.querySelector("#table")
const currentLocation = document.location.protocol + "//" + document.location.host;

function createLine(data){
    const div = document.createElement("table")
    div.innerHTML =  "<tr id=\"contact_" + data[0] + "\">\n" +
        "        <th scope=\"row\">" + data[0] + "</th>\n" +
        "        <th class=\"name\">" + data[1] + "</th>\n" +
        "        <th class=\"description\">" + data[2] + "</th>\n" +
        "        <th class=\"date\">" + data[3] + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <p class=\"change\">Изменить</p>\n" +
        "            <p class=\"delete\">Удалить</p>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}

function createLineWithCase(data){
    const div = document.createElement("table")
    div.innerHTML =  "<tr id=\"contact_" + data["id"] + "\">\n" +
        "        <th scope=\"row\">" + data["id"] + "</th>\n" +
        "        <th class=\"name\">" + data["name"] + "</th>\n" +
        "        <th class=\"description\">" + data["description"] + "</th>\n" +
        "        <th class=\"date\">" + data["date"] + "</th>\n" +
        "        <th scope=\"col\" style=\"display: flex; justify-content: space-around\">\n" +
        "            <p class=\"change\">Изменить</p>\n" +
        "            <p class=\"delete\">Удалить</p>\n" +
        "        </th>\n" +
        "    </tr>"
    return div.firstChild
}


$(function (){
    for (let i = 0; i < contacts.length; i++)
        table.appendChild(createLine(contacts[i]).firstChild);
    addListener()
})


// Добавить

function addContact(){
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'none'

    const description = document.getElementById("description");
    const name = document.getElementById("name");
    const date = document.getElementById("date");

    createAjaxQuery("/addCase", {description: description.value, name: name.value, date: date.value},
        addSuccessHandler)
    description.value = ""
    name.value = ""
    date.value = ""
}

function addSuccessHandler(data){
    table.appendChild(createLineWithCase(data).firstChild);
    addListener()
}

// Удалить

function deleteContact(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/deleteCase/" + id, {},
        deleteSuccessHandler)
}

function deleteSuccessHandler(data){
    table.removeChild(document.getElementById("contact_" + data))
}

// Изменить
function tryChangeContact(event){
    const id = event.currentTarget.parentNode.parentNode.id.split("_")[1]
    createAjaxQuery("/getCase/" + id, {}, tryChangeHandler)
}

function tryChangeHandler(data){
    document.getElementById("name").value = data.name;
    document.getElementById("description").value = data.description;
    document.getElementById("date").value = data.date;
    const changeButton = document.getElementById("changeButton")
    changeButton.style.display = 'flex'
    changeButton.dataset.id = data.id
    changeButton.addEventListener('click', updateChanges)
}

function updateChanges(){
    const changeButton = document.getElementById("changeButton")
    const description = document.getElementById("description");
    const name = document.getElementById("name");
    const date = document.getElementById("date");
    changeButton.style.display = "none"
    createAjaxQuery("/updateCase/" + changeButton.dataset.id, {description: description.value, name: name.value, date: date.value}, updateHandler)
    description.value = ""
    name.value = ""
    date.value = ""
    changeButton.dataset.id = ""
}

function updateHandler(data){
    const tr = document.getElementById("contact_" + data.id)
    tr.querySelector(".name").innerHTML = data.name
    tr.querySelector(".description").innerHTML = data.description
    tr.querySelector(".date").innerHTML = data.date
}

function createAjaxQuery(path, contact, toFunction){
    $.ajax({
        url: currentLocation + path,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(contact),
        success: toFunction
    })
}


function addListener(){
    const contact = document.querySelectorAll(".delete")
    const change = document.querySelectorAll(".change")
    for (let i = 0; i < contact.length; i++){
        contact[i].addEventListener('click', deleteContact)
        change[i].addEventListener('click', tryChangeContact)
    }
}

document.getElementById("addButton").addEventListener("click", addContact);
