# Proyecto 02 · Gestión de un parqueadero

**Programación Avanzada** · Pontificia Universidad Javeriana
**Integrantes:** [Juan José Silva], [Angel Santiagi Beltran]

## Descripción
Aplicación de consola que gestiona un parqueadero: registra entradas y salidas, calcula el cobro de cada tiquete (con convenios y mensualidades), procesa archivos de movimientos y genera el cierre diario.


## Reglas de negocio

| Vehículo | Parqueo ($/min) | Recarga ($/min) |
|---|---|---|
| Carro | 120 | – |
| Moto | 80 | – |
| Eléctrico | 108 | 50 |

**Convenios** (minutos gratis, solo sobre el parqueo): `NINGUNO` 0 · `ALMACEN` 30 · `RESTAURANTE` 60 · `CINE` 120

```
cobrables = max(0, minutos − minutos gratis)
parqueo   = 0 si una mensualidad cubre el día de la entrada; si no, calcularTarifa(cobrables)
recarga   = cargoAdicional(minutos)   // siempre sobre todos los minutos
total     = parqueo + recarga
```

**Placas:** carro y eléctrico `ABC123`; moto `KLM45D`. Siempre en mayúsculas.
**Mensualidades:** por placa, con rango inclusivo; solo se revisa el día de la entrada.

## Validaciones
| Operación | Excepción |
|---|---|
| Entrada con placa inválida para el tipo | `PlacaInvalidaException` |
| Entrada de un vehículo que ya está adentro | `VehiculoYaAdentroException` |
| Salida sin tiquete abierto | `VehiculoNoEncontradoException` |
| Salida anterior a la entrada / mensualidad con fin antes del inicio | `FechaInvalidaException` |

Un rechazo no cambia el estado ni consume número de tiquete, y la carga continúa con la siguiente línea. Las líneas mal formadas se informan con su número de línea.

## Archivos
- `Mensualidades.txt`: `placa*inicio*fin`
- `Movimientos.txt`:
  - Entrada: `E*placa*TIPO*yyyy-MM-dd HH:mm`
  - Salida: `S*placa*yyyy-MM-dd HH:mm*CONVENIO`
  - Las líneas con `#` son comentarios.
- `Cierre_yyyy-MM-dd.txt`: totales del día, vehículos adentro y placas frecuentes.
- Opciones 8 (guardar) y 9 (recuperar) conservan tiquetes abiertos y el consecutivo.

## Estructura
```
[modelo/]  [excepciones/]  [negocio/]  [persistencia/]  [presentacion/]
```
