const info = async () => {
    try {
        const res = await fetch("/api/staff");
        const data = await res.json();
        data.map((val) => {
            const listItem = document.createElement("li");
            listItem.innerText = val.fullName;
            staffList.appendChild(listItem);
        })
        console.log(data);
    } catch (e) {
        console.error(e);
    }
}
info();
const staffList = document.querySelector(".staff-list");

