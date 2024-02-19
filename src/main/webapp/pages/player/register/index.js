const submitbtn = document.querySelector("#submit-btn")
submitbtn.addEventListener("click", () => {
    const contractInput = document.querySelector("#contractIn")
    const contractEnd = document.querySelector("#contractEndDate")
    contractEnd.value = new Date(contractInput.value).getTime();
    
    const birthdateIn = document.querySelector("#birthdateIn")
    const dateOfBirth = document.querySelector("#dateOfBirth")
    dateOfBirth.value = new Date(birthdateIn.value).getTime();
    
    const form = document.querySelector("form")
    form.submit();
})



function createOptions(data) {
    const parent = document.querySelector("#club-dropdown");
    data.map((club) => {
        const elem = document.createElement("option")
        elem.innerText = club.name;
        elem.value = club.id;
        parent.appendChild(elem)
    })
}

const call = async () => {

    const res = await fetch("/api/clubs");
    const data = await res.json();

    createOptions(data)
}
call()
