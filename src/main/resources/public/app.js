function mostrarCargando() {
    document.getElementById('resultado').textContent = 'Cargando ...';
    document.getElementById('error').textContent = '';
}
function mostrarResultado(texto) {
    document.getElementById('resultado').textContent = texto;
    document.getElementById('error').textContent = '';
}
function mostrarError(texto) {
    document.getElementById('resultado').textContent = '';
    document.getElementById('error').textContent = texto;
}

async function llamarServicio(url, boton) {
    mostrarCargando();
    boton.disabled =  true;
    try{
        const respuesta = await fetch(url);
        let datos = null;
        try{
            datos = await respuesta.json();
        } catch {
            
        }
        if (!respuesta.ok) {
            let mensaje;
            if (datos && datos.error){
                mensaje = datos.error;
            }else {
                mensaje = `Error del servidor: ${respuesta.status}`;
            }
            mostrarError(mensaje);
            return null;
        }
        return datos;
    } catch (e) {
        mostrarError('Error de red: ' + e.message);
        return null;
    } finally {
        boton.disabled = false;
    } 
}
document.getElementById('btnSaludo').addEventListener('click', async (evento) => {
    const nombre = document.getElementById('nombre').value;
    const datos = await llamarServicio('/hello?name=' + encodeURIComponent(nombre), evento.target);
    if (datos) mostrarResultado(datos.greeting);
});

document.getElementById('btnCuadrado').addEventListener('click', async (evento) => {
    const numero = document.getElementById('numero').value;
    const datos = await llamarServicio('/square?value=' + encodeURIComponent(numero),evento.target);
    if (datos) mostrarResultado('El cuadrado es: ' + datos.result);
});

document.getElementById('btnHora').addEventListener('click', async (evento) => {
    const datos = await llamarServicio('/time', evento.target);
    if (datos) mostrarResultado('Hora del servidor: ' + datos.time);
});