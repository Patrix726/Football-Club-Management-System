
const call = async () => {
    const res = await fetch("/api/clubs")
    const data = await res.json();

    const parentCard = document.querySelector(".container")
    
    data.map((club) => {
        const clubCard = document.createElement("div");
        clubCard.classList.add("card");
        clubCard.classList.add("club-card");
        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");

        const nameSpan = document.createElement("a");
        nameSpan.setAttribute("href","/pages/club?clubId="+club.id)
        nameSpan.innerText = club.name;
        
        const countrySpan = document.createElement("span");
        countrySpan.innerText = club.country;
        
        const managerSpan = document.createElement("span");
        managerSpan.innerText = club.manager;
        
        cardBody.appendChild(nameSpan);
        cardBody.appendChild(countrySpan)
        cardBody.appendChild(managerSpan)

        clubCard.appendChild(cardBody);
        
        parentCard.appendChild(clubCard);
    })
}
call()
