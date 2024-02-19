function createOptions(data) {
    const parent = document.querySelector(".stats");
    const children = [...parent.children]
    children.map((elem) => {
        if (elem.id === "currentClub") {
            const link = document.createElement("a")
            link.setAttribute("href", "/pages/club?clubId=" + data.currentClubId)
            link.innerText = data.currentClub
            elem.appendChild(link)
        } else {
            elem.innerText += data[elem.id];
        }
    })
}
const urlParams = new URLSearchParams(window.location.search);
const playerId = urlParams.get("playerId");

const call = async () => {
    const transferbtn = document.querySelector("#transfer")
    transferbtn.setAttribute("href", "/pages/player/transfer?playerId="+playerId)
    

    const res = await fetch("/api/player?playerId=" + playerId);
    const data = await res.json();

    createOptions(data)
}
call()

const deletebtn = document.querySelector("#delete")
deletebtn.addEventListener("click", () => {
    fetch("/api/player?action=1&playerId="+playerId, {method: 'POST'})
})
