function trocarPag() {
    let dropdown = document.getElementById("dropdown");
    let selecionado = dropdown.value;
    if (selecionado) {
        window.location.href = selecionado;
    }
}