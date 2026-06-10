<div align="center">

# 🛒 GestionTienda

**Aplicación de escritorio para la gestión integral de una tienda**  
*Proyecto Integrador — DAM · Curso 2025–2026*

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com)
[![SQLite](https://img.shields.io/badge/SQLite-3-003B57?style=flat-square&logo=sqlite&logoColor=white)](https://www.sqlite.org)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-5382A1?style=flat-square)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![JUnit](https://img.shields.io/badge/Tests-JUnit%204-25A162?style=flat-square&logo=junit5&logoColor=white)](https://junit.org)
[![MVC](https://img.shields.io/badge/Patrón-MVC-blueviolet?style=flat-square)]()

</div>

---

## 📌 Índice

- [Descripción](#-descripción)
- [Funcionalidades](#-funcionalidades)
- [Roles de usuario](#-roles-de-usuario)
- [Arquitectura](#-arquitectura)
- [Estructura del repositorio](#-estructura-del-repositorio)
- [Base de datos](#-base-de-datos)
- [Pruebas](#-pruebas)
- [Cómo ejecutar](#-cómo-ejecutar)
- [Tecnologías](#-tecnologías)
- [Documentación y recursos](#-documentación-y-recursos)

---

## 📖 Descripción

**GestionTienda** es una aplicación de escritorio desarrollada en **Java con Swing** que simula el sistema de gestión de una tienda tipo Fnac. Implementa el patrón arquitectónico **MVC (Modelo–Vista–Controlador)** y persiste los datos en una base de datos **SQLite** local.

El sistema contempla tres tipos de usuarios con distintos niveles de acceso: administrador, empleado y comprador, cada uno con su propio entorno de trabajo dentro de la misma aplicación.

---

## ✅ Funcionalidades

| Nº | Requisito Funcional | Rol |
|----|---|---|
| RF1 | Alta de productos | Administrador |
| RF2 | Consulta de productos con filtros (nombre, precio, categoría) | Todos |
| RF3 | Modificación de productos | Administrador |
| RF4 | Baja lógica / reactivación de productos | Administrador |
| RF5 | Alta de empleados | Administrador |
| RF6 | Eliminación (baja lógica) de empleados | Administrador |
| RF7 | Agregar productos al carrito | Comprador |
| RF8 | Eliminar productos del carrito | Comprador |
| RF9 | Incrementar stock de productos | Empleado / Admin |
| RF10 | Decrementar stock de productos | Empleado / Admin |
| RF11 | Simulacro de pago y registro de transacción | Comprador |
| RF12 | Gestión de usuarios (habilitar / deshabilitar) | Administrador |
| RF13 | Consulta del historial de transacciones con filtros | Administrador |
| RF14 | Edición del perfil del comprador y baja voluntaria | Comprador |

---

## 👥 Roles de usuario

```
┌─────────────────────┬────────────────────────────────────────────────────┐
│ Rol                 │ Acceso                                             │
├─────────────────────┼────────────────────────────────────────────────────┤
│ 🔑 Administrador    │ Todo: empleados, productos, stock, transacciones,  │
│                     │ gestión de usuarios                                │
├─────────────────────┼────────────────────────────────────────────────────┤
│ 🧑‍💼 Empleado        │ Gestión de stock (incrementar / decrementar)       │
├─────────────────────┼────────────────────────────────────────────────────┤
│ 🛒 Comprador        │ Tienda, carrito, pago, perfil                      │
└─────────────────────┴────────────────────────────────────────────────────┘
```

**Credenciales de prueba** (incluidas en `init.sql`):

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `admin` | Administrador |
| `Zoe` | `1234` | Administrador |
| `empleado` | `empleado` | Empleado |
| `comprador` | `comprador` | Comprador |

---

## 🏗 Arquitectura

El proyecto sigue estrictamente el patrón **MVC**:

```
┌──────────────────────────────────────────────────┐
│                   Vista (View)                   │
│  VPrincipal · VloginForm · VShop · VCarrito      │
│  VGestionProd · VGestionStock · VGestionEmp      │
│  VRegistrarse · VCuenta · VTrans · VGestionUsr   │
└───────────────────┬──────────────────────────────┘
                    │  ActionListener / MouseListener
                    ▼
┌──────────────────────────────────────────────────┐
│               Controlador (Ctrl)                 │
│  Gestiona la navegación entre paneles,           │
│  orquesta las llamadas a los DAOs y              │
│  centraliza toda la lógica de negocio.           │
└───────────────────┬──────────────────────────────┘
                    │
                    ▼
┌──────────────────────────────────────────────────┐
│                  Modelo (Model)                  │
│  POJOs: Usuario · Empleado · Comprador           │
│         Producto · Carrito · Transacciones       │
│                                                  │
│  DAOs:  TableUsuarioDAO · TableProductoDAO       │
│         TableCarritoDAO · TableCarritoProductoDAO│
│         TableTransaccionesDAO · AccessDBProp     │
└──────────────────────────────────────────────────┘
```

---

## 📁 Estructura del repositorio

```
TrabajoIntrgrador/
│
├── 📁 .git/
├── 📁 .vscode/
│
├── 📁 BaseDeDatos/          # Scripts y recursos de la BD
│
├── 📁 Documentación/        # Documentos del proyecto (funcional, pruebas, manual)
│
├── 📁 Ejecutable/           # Distribución lista para ejecutar
│   ├── 📁 DB/               # Carpeta con la BD SQLite generada en tiempo de ejecución
│   └── ☕ GestionTienda.jar  # JAR ejecutable
│
├── 📁 Java/
│   └── 📁 GestionTienda/    # Proyecto Eclipse
│       ├── 📁 .metadata/
│       ├── 📁 .settings/
│       ├── 📁 bin/           # Clases compiladas
│       ├── 📁 DB/            # init.sql · datosPrueba.sql · ConfiguracionDB.properties
│       ├── 📁 doc/           # Javadoc generado (javadoc.xml)
│       ├── 📁 Lib/           # Librerías externas (SQLite JDBC, JUnit...)
│       ├── 📁 src/
│       │   └── 📁 com/dam/
│       │       ├── 📁 ctrl/        # Ctrl.java
│       │       ├── 📁 main/        # App.java (punto de entrada)
│       │       ├── 📁 model/
│       │       │   ├── 📁 acessbd/  # DAOs + AccessDBProp
│       │       │   └── 📁 pojos/    # Entidades del dominio
│       │       ├── 📁 pruebas/     # Tests JUnit (RF1–RF11)
│       │       └── 📁 view/        # Vistas Swing + Fuentes + Constantes
│       ├── 📄 .classpath
│       ├── 📄 .gitignore
│       ├── 📄 .project
│       └── 📄 javadoc.xml
│
├── 📄 .gitignore
├── 📦 Ejecutable.zip        # Distribución comprimida
└── 📄 README.md
```

---

## 🗄 Base de datos

La BD **SQLite** (`tiendaFnac.db`) se genera automáticamente en la primera ejecución a partir del script `DB/init.sql`. El esquema relacional es el siguiente:

```
usuario ──< empleado
usuario ──< comprador ──< carrito ──< carrito_producto >── producto
                    └──────────────── transacciones
```

**Tablas:**

| Tabla | Descripción |
|---|---|
| `usuario` | Base de todos los perfiles (autorizacion 1/2/3) |
| `empleado` | Extensión de usuario con NSS e IBAN |
| `comprador` | Extensión de usuario con dirección y tarjeta |
| `producto` | Catálogo de artículos con stock y baja lógica |
| `carrito` | Carritos de compra asociados a compradores |
| `carrito_producto` | Relación N:M carrito ↔ producto con cantidad |
| `transacciones` | Historial de compras finalizadas |

---

## 🧪 Pruebas

El proyecto cuenta con una suite de **tests de integración JUnit 4** que cubren los requisitos funcionales principales. Cada clase de prueba es autónoma: inicializa su propio estado en `@Before` y limpia la BD en `@After`.

| Clase de test | RF cubierto |
|---|---|
| `ProductoAltaTest` | RF1 — Alta de productos |
| `ProductoConsultaTest` | RF2 — Consulta con filtros |
| `ProductoModificacionTest` | RF3 — Modificación |
| `ProductoEliminacionTest` | RF4 — Baja lógica |
| `EmpleadoAltaTest` | RF5 — Alta de empleados |
| `EmpleadoEliminacionTest` | RF6 — Baja de empleados |
| `CarritoAgregarProductoTest` | RF7 — Agregar al carrito |
| `CarritoEliminarProductoTest` | RF8 — Eliminar del carrito |
| `StockAgregarTest` | RF9 — Incremento de stock |
| `StockEliminarTest` | RF10 — Decremento de stock |
| `SimulacroPagoTest` | RF11 — Pago y transacción |

---

## ▶ Cómo ejecutar

### Opción A — JAR ejecutable (recomendado)

1. Ir a la carpeta `Ejecutable/`
2. Asegurarse de que la carpeta `DB/` esté junto al JAR
3. Ejecutar:

```bash
java -jar GestionTienda.jar
```

> La base de datos se crea automáticamente en `DB/tiendaFnac.db` si no existe.

### Opción B — Desde el IDE (Eclipse)

1. Importar el proyecto desde `Java/GestionTienda/`
2. Añadir las librerías de `Lib/` al build path
3. Ejecutar `com.dam.main.App`

### Requisitos

- **Java 17** o superior
- Las librerías del directorio `Lib/` (SQLite JDBC, JUnit 4)

---

## 🛠 Tecnologías

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje principal |
| Java Swing | Interfaz gráfica de escritorio |
| SQLite 3 | Base de datos embebida |
| JDBC | Acceso a la base de datos |
| JUnit 4 | Pruebas unitarias e integración |
| Eclipse IDE | Entorno de desarrollo |
| Fuente Nunito | Tipografía corporativa de la UI |

---

## 📚 Documentación y recursos

| Recurso | Enlace |
|---|---|
| 📋 Tablero JIRA | [Abrir proyecto](https://zoejastreb.atlassian.net?continue=https%3A%2F%2Fzoejastreb.atlassian.net%2Fwelcome%2Fsoftware&atlOrigin=eyJpIjoiMDNkODAzMGJkY2I5NDgxNGEwNzhlZjE0YWVkMWUxNGYiLCJwIjoiaiJ9) |
| 🎨 Diseño Figma | [Ver prototipo](https://www.figma.com/design/Nk1sano1QO7MnV7ucJRZjR/Trabajo-integrador?node-id=0-1&t=Av93u47PaKBNVXQs-1) |
| 📄 Documento funcional | [Google Docs](https://docs.google.com/document/d/1UCxW5JPl4t4v2eb6IAEatjdwIZ9gRpA2lvf5H_UUbe4/edit?usp=sharing) |
| 📝 Plan de pruebas | [Google Docs](https://docs.google.com/document/d/1UCxW5JPl4t4v2eb6IAEatjdwIZ9gRpA2lvf5H_UUbe4/edit?usp=sharing) |
| 📖 Manual de usuario | [Google Docs](https://docs.google.com/document/d/1DXk0TBI3-3RNe7-CAt1Ck3KL21Tuvel2-UcTjdUxhmo/edit?usp=sharing) |

---

<div align="center">

*Desarrollado por **Zoe** · DAM 2025–2026*

</div>
