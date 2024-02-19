async function fetchPlayers(leagueId) {
    try {
        const res = await fetch("/api/league?leagueId=" + leagueId);
        const data = await res.json();
        console.log(data)
        return data    
    } catch (err) {
        console.log(err)
    }
}
async function addPlayerCards(leagueId) {
    const data = await fetchPlayers(leagueId)
    const parentCard = document.querySelector("#collapse" + leagueId)
    if (parentCard.childElementCount > 1) {
        return
    }
    data.map((club,i) => {
        const clubCard = document.createElement("div");
        clubCard.classList.add("card");
        clubCard.classList.add("club-card");
        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");

        const rankSpan = document.createElement("span")
        rankSpan.innerText = i + 1;
        rankSpan.id = "rank"
        const nameSpan = document.createElement("span");
        nameSpan.innerText = club.name;
        
        const clubStats = document.createElement("div");
        clubStats.classList.add("club-stats")

        const matchesPlayedSpan = document.createElement("span");
        matchesPlayedSpan.innerText = club.played;
        const matchesWonSpan = document.createElement("span");
        matchesWonSpan.innerText = club.won;
        const matchesDrewSpan = document.createElement("span");
        matchesDrewSpan.innerText = club.drew;
        const matchesLostSpan = document.createElement("span");
        matchesLostSpan.innerText = club.lost;
        const pointsSpan = document.createElement("span")
        pointsSpan.innerText = club.points;
        
        clubStats.appendChild(matchesPlayedSpan)
        clubStats.appendChild(matchesWonSpan)
        clubStats.appendChild(matchesDrewSpan)
        clubStats.appendChild(matchesLostSpan)
        clubStats.appendChild(pointsSpan);

        cardBody.appendChild(rankSpan);
        cardBody.appendChild(nameSpan);
        cardBody.appendChild(clubStats);
        
        clubCard.appendChild(cardBody);
        
        parentCard.appendChild(clubCard);
    })
}
function createCard(leagueId, leagueName) {
    const container = document.createElement('div');
    container.classList.add("card");

    const header = document.createElement('div');
    header.classList.add("card-header")
    header.id = "heading" + leagueId;

    const headerText = document.createElement("h5")
    headerText.classList.add("mb-0")


    const headerBtn = document.createElement("button")
    headerBtn.addEventListener("click", ()=>addPlayerCards(leagueId))
    headerBtn.classList.add("btn")
    headerBtn.classList.add("btn-link")
    headerBtn.classList.add("collapsed")
    headerBtn.setAttribute("data-toggle", "collapse") 
    headerBtn.setAttribute("data-target", "#collapse" + leagueId) 
    headerBtn.setAttribute("aria-expanded", "false")
    headerBtn.setAttribute("aria-controls", "collapse" + leagueId) 
    headerBtn.innerText = leagueName


    const body = document.createElement("div")
    body.id = "collapse" + leagueId;
    body.classList.add("collapse") 
    body.setAttribute("aria-labelledby", "heading" + leagueId)
    body.setAttribute("data-parent", "#accordion")
    const innerCard = document.createElement("div")
    innerCard.classList.add("card")
    innerCard.classList.add("club-card")
    innerCard.classList.add("club-card-header")
    const innerBody = document.createElement("div")
    innerBody.classList.add("card-body")
    
    const tableHeads = ["Rank","Club Name",["Played" ,"Won", "Drew", "Lost","Points"]];
    tableHeads.map((title) => {
        if (typeof title === "string") {
            const el = document.createElement("span");
            el.id = title === "Rank" ?  "rank":"";
            el.innerText = title;
            innerBody.appendChild(el)
        } else {
            const container = document.createElement("div")
            title.map((stat) => {
                const el = document.createElement("span");
                el.innerText = stat;
                container.appendChild(el)
            })
            container.classList.add("club-stats")
            innerBody.appendChild(container)
        }
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
    const res = await fetch("/api/leagues")
    const data = await res.json();

    
    data.map((league) => {
        createCard(league.id, league.name)
    })
}
call()
