function createOptions(data) {
    const parent = document.querySelector(".stats");
    const children = [...parent.children]
    children.map((elem) => {
        if (elem.id === "founded") {
            const foundedDate = new Date(data.founded)
            const infoSpan = document.createElement("span")
            infoSpan.classList.add("stats-info");
            infoSpan.innerText = foundedDate.getFullYear();
            elem.appendChild(infoSpan)
        } else {
            const infoSpan = document.createElement("span")
            infoSpan.classList.add("stats-info");
            infoSpan.innerText = data[elem.id];
            elem.appendChild(infoSpan)
        }
    })
}
function insertPlayers(data) {
    const parent = document.querySelector(".players");

    const headerCard = document.createElement("div")
    headerCard.classList.add("card")
    headerCard.classList.add("player-card")
    headerCard.classList.add("player-card-header")
    const headerBody = document.createElement("div")
    headerBody.classList.add("card-body")
    
    const tableHeads = ["Player Name", "Nationality", "Goals", "Assists"];
    tableHeads.map((title,ind) => {
        const el = document.createElement("span");
        if (ind == 0) {
            el.classList.add("first-header")
        }
        el.innerText = title;
        headerBody.appendChild(el)
    })
    
    headerCard.appendChild(headerBody);
    parent.appendChild(headerCard);
    
    data.map((player) => {
        const playerCard = document.createElement("div");
        playerCard.classList.add("card");
        playerCard.classList.add("player-card");
        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");
        const nameSpan = document.createElement("a");
        nameSpan.setAttribute("href","/pages/player?playerId="+player.id)
        nameSpan.innerText = player.fullName;
        const nationalitySpan = document.createElement("span");
        nationalitySpan.innerText = player.nationality;
        const goalSpan = document.createElement("span");
        goalSpan.innerText = player.goals;
        const assistSpan = document.createElement("span");
        assistSpan.innerText = player.assists;

        cardBody.appendChild(nameSpan);
        cardBody.appendChild(nationalitySpan);
        cardBody.appendChild(goalSpan);
        cardBody.appendChild(assistSpan);
        playerCard.appendChild(cardBody);
        
        parent.appendChild(playerCard);    
    })
    
}
const urlParams = new URLSearchParams(window.location.search);
const clubId = urlParams.get("clubId");

const clubStats = async () => {

    const res = await fetch("/api/club?clubId=" + clubId);
    const data = await res.json();

    createOptions(data)
}
clubStats()

const players = async () => {
    const res = await fetch("/api/players?clubId=" + clubId);
    const data = await res.json();
    insertPlayers(data)
}
players()