const par = document.querySelector('p');
const f = async ()=>{
    const res = await fetch("/api/staff");
    const data = res.json();
    console.log(data);
    
};
f();