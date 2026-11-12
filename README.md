# Proyecto 02 · Gestión de un parqueadero

**Programación Avanzada** · Pontificia Universidad Javeriana
**Integrantes:** Juan José Silva, Angel Santiago Beltrán

## ¿De qué trata?
Es un programa de consola para manejar un parqueadero. Permite registrar cuándo entra y sale cada vehículo, calcular cuánto debe pagar, leer archivos con varios movimientos de una sola vez y sacar un resumen del día.

## ¿Cuánto se cobra?

| Vehículo | Parqueo (por minuto) | Recarga (por minuto) |
|---|---|---|
| Carro | $120 | No aplica |
| Moto | $80 | No aplica |
| Eléctrico | $108 | $50 |

**Convenios:** dan minutos gratis, pero solo en el parqueo (no en la recarga).

- Ninguno: 0 minutos
- Almacén: 30 minutos
- Restaurante: 60 minutos
- Cine: 120 minutos

**Cómo se calcula el total:**
1. Se restan los minutos gratis del convenio al tiempo total (si da negativo, queda en 0).
2. Si el vehículo tiene una mensualidad que cubre el día de entrada, el parqueo es gratis. Si no, se cobra por los minutos que quedaron.
3. La recarga se cobra siempre sobre todos los minutos, sin descuento.
4. El total es parqueo + recarga.

**Placas:** los carros y eléctricos usan el formato `ABC123` y las motos `KLM45D`. Siempre se guardan en mayúsculas.

**Mensualidades:** se registran por placa con una fecha de inicio y una de fin (ambas cuentan). Solo se mira el día en que el vehículo entró.

## Errores que controla el programa

| Situación | Error |
|---|---|
| La placa no corresponde al tipo de vehículo | `PlacaInvalidaException` |
| Entra un vehículo que ya está adentro | `VehiculoYaAdentroException` |
| Sale un vehículo que no tiene tiquete abierto | `VehiculoNoEncontradoException` |
| La salida es antes de la entrada, o la mensualidad termina antes de empezar | `FechaInvalidaException` |

Si algo se rechaza, no pasa nada con el parqueadero y tampoco se gasta un número de tiquete. Al leer un archivo, el programa sigue con la siguiente línea. Si una línea está mal escrita, avisa en qué número de línea está.

## Archivos que usa
- `Mensualidades.txt`: una mensualidad por línea, así: `placa*inicio*fin`
- `Movimientos.txt`: entradas y salidas del parqueadero.
  - Entrada: `E*placa*TIPO*yyyy-MM-dd HH:mm`
  - Salida: `S*placa*yyyy-MM-dd HH:mm*CONVENIO`
  - Las líneas que empiezan con `#` son comentarios y se ignoran.
- `Cierre_yyyy-MM-dd.txt`: resumen del día con los totales, los vehículos que siguen adentro y las placas que más vienen.

Las opciones 8 (guardar) y 9 (recuperar) del menú sirven para no perder los tiquetes abiertos ni el número del siguiente tiquete.
