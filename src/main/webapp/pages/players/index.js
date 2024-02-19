async function fetchPlayers(clubId) {
    try {
        const res = await fetch("/api/players?clubId=" + clubId);
        const data = await res.json();
        console.log(data)
        return data    
    } catch (err) {
        console.log(err)
    }
}
async function addPlayerCards(clubId) {
    const data = await fetchPlayers(clubId)
    const parentCard = document.querySelector("#collapse" + clubId)
    if (parentCard.childElementCount > 1) {
        return
    }
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
        console.log(player);

        cardBody.appendChild(nameSpan);
        cardBody.appendChild(nationalitySpan);
        cardBody.appendChild(goalSpan);
        cardBody.appendChild(assistSpan);
        playerCard.appendChild(cardBody);
        
        parentCard.appendChild(playerCard);
    })
}
function createCard(clubId, clubName) {
    const container = document.createElement('div');
    container.classList.add("card");

    const header = document.createElement('div');
    header.classList.add("card-header")
    header.id = "heading" + clubId;

    const headerText = document.createElement("h5")
    headerText.classList.add("mb-0")


    const headerBtn = document.createElement("button")
    headerBtn.addEventListener("click", ()=>addPlayerCards(clubId))
    headerBtn.classList.add("btn")
    headerBtn.classList.add("btn-link")
    headerBtn.classList.add("collapsed")
    headerBtn.setAttribute("data-toggle", "collapse") 
    headerBtn.setAttribute("data-target", "#collapse" + clubId) 
    headerBtn.setAttribute("aria-expanded", "false")
    headerBtn.setAttribute("aria-controls", "collapse" + clubId) 
    headerBtn.innerText = clubName


    const body = document.createElement("div")
    body.id = "collapse" + clubId;
    body.classList.add("collapse") 
    body.setAttribute("aria-labelledby", "heading" + clubId)
    body.setAttribute("data-parent", "#accordion")
    const innerCard = document.createElement("div")
    innerCard.classList.add("card")
    innerCard.classList.add("player-card")
    innerCard.classList.add("player-card-header")
    const innerBody = document.createElement("div")
    innerBody.classList.add("card-body")
    
    const tableHeads = ["Player Name", "Nationality", "Goals", "Assists"];
    tableHeads.map((title,ind) => {
        const el = document.createElement("span");
        if (ind == 0) {
            el.classList.add("first-header")
        }
        el.innerText = title;
        innerBody.appendChild(el)
    })
    
    innerCard.appendChild(innerBody)
    headerText.appendChild(headerBtn)
    header.appendChild(headerText)

    body.appendChild(innerCard)

    container.appendChild(header)
    container.appendChild(body)

    const accordion = document.querySelector('#accordion')
    accordion.appendChild(container)
}
const call = async () => {
    const res = await fetch("/api/clubs")
    const data = await res.json();

    
    data.map((club) => {
        createCard(club.id, club.name)
    })
}
call()
