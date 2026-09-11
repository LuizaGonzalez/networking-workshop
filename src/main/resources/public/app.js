function mostrarResultado(texto) {
    document.getElementById('resultado').textContent = texto;
    document.getElementById('error').textContent = '';
}

function mostrarError(texto) {
    document.getElementById('error').textContent = texto;
}

async function llamarServicio(url) {
    try{
        const respuesta = await fetch(url);
        if (!respuesta.ok) {
            mostrarError('Error del servidor: ' + respuesta.status);
            return;
        }
        const datos = await respuesta.json();
        return datos;
    } catch (e) {
        mostrarError('Error de red: ' + e.message);
    }   
}
document.getElementById('btnSaludo').addEventListener('click', async () => {
    const nombre = document.getElementById('nombre').value;
    const datos = await llamarServicio('/hello?name=' + encodeURIComponent(nombre));
    if (datos) mostrarResultado(datos.greeting);
});

document.getElementById('btnCuadrado').addEventListener('click', async () => {
    const numero = document.getElementById('numero').value;
    const datos = await llamarServicio('/square?value=' + encodeURIComponent(numero));
    if (datos) mostrarResultado('El cuadrado es: ' + datos.result);
});

document.getElementById('btnHora').addEventListener('click', async () => {
    const datos = await llamarServicio('/time');
    if (datos) mostrarResultado('Hora del servidor: ' + datos.time);
});