package com.dam.view;

/**
 * Clase final de utilidad que centraliza las constantes de la interfaz gráfica (`ConstantesBotones`).
 * <p>
 * Agrupa de forma estática los textos de los botones, rutas de recursos iconográficos y comandos de acción 
 * (`ActionCommand`) compartidos entre las diferentes vistas y el controlador de la aplicación. Al estar 
 * definida como {@code final} y contar con un constructor privado, se garantiza que no pueda ser instanciada 
 * ni heredada.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public final class ConstantesBotones {

	/**
	 * Constructor privado para asegurar la naturaleza no instanciable de la clase de utilidad.
	 * Evita de forma explícita que se creen objetos de este tipo desde el exterior.
	 */
	private ConstantesBotones() {
	}

	/** Texto descriptivo o comando para la acción de inicio de sesión o entrada. */
	public static final String ENTRAR = "Entrar";
	/** Texto descriptivo o comando para acceder al formulario de alta de un nuevo usuario. */
	public static final String REGISTRARSE = "Registrarse";
	/** Texto descriptivo o comando general para iniciar sesión en la plataforma. */
	public static final String INICIAR_SESION = "Iniciar Sesión";
	/** Texto descriptivo o comando para procesar la pasarela de pago del carrito. */
	public static final String PAGAR = "Pagar";
	/** Texto descriptivo o comando para disparar la acción de filtrado en el catálogo. */
	public static final String BUSCAR_PRODUCTO = "Buscar Producto";
	/** Texto descriptivo o comando para buscar registros en el personal de trabajo. */
	public static final String BUSCAR_EMPLEADO = "Buscar Empleado";
	/** Texto descriptivo o comando para expandir la información detallada de un elemento. */
	public static final String VER_MAS = "Ver más";
	/** Texto descriptivo o comando para contraer la vista detallada de un componente. */
	public static final String VER_MENOS = "Ver menos";
	/** Ruta relativa de almacenamiento del recurso gráfico para el icono de adición o incremento. */
	public static final String MASICONO = "Iconos/icons8-más-30.png";
	/** Identificador o comando simplificado para la operación de suma o incremento de unidades. */
	public static final String MAS = "mas";
	/** Ruta relativa de almacenamiento del recurso gráfico para el icono de sustracción o decremento. */
	public static final String MENOSICONO = "Iconos/icons8-subtract-30.png";
	/** Identificador o comando simplificado para la operación de resta o decremento de unidades. */
	public static final String MENOS = "menos";
	/** Texto descriptivo o comando para navegar a la sección del carrito de compra. */
	public static final String CARRITO = "Carrito";
	/** Texto descriptivo o comando para procesar y consolidar el alta de un formulario. */
	public static final String REGISTRAR = "Registrar";
	/** Texto descriptivo o comando para abortar la operación en curso y volver atrás. */
	public static final String CANCELAR = "Cancelar";
	/** Texto descriptivo o comando para restablecer los valores por defecto de los campos de entrada. */
	public static final String LIMPIAR = "Limpiar";
	/** Texto descriptivo o comando para acceder al formulario de alta de personal laboral. */
	public static final String REGISTRAR_EMPLEADO = "Registrar Empleado";
	/** Texto descriptivo o comando para suspender o eliminar la cuenta de un trabajador. */
	public static final String ELIMINAR_EMPLEADO = "Eliminar Empleado";
	/** Texto descriptivo o comando para agregar una nueva entidad al catálogo comercial. */
	public static final String ADD_PRODUCTO = "Agregar Producto";
	/** Texto descriptivo o comando para guardar los cambios editados sobre un producto. */
	public static final String MODIFICAR_PRODUCTO = "Modificar Producto";
	/** Texto descriptivo o comando para eliminar lógicamente un artículo de la tienda. */
	public static final String ELIMINAR_PRODUCTO = "Eliminar Producto";
	/** Texto descriptivo o comando para invalidar la sesión actual y retornar al login. */
	public static final String CERRAR_SESION = "Cerrar Sesión";
	/** Texto descriptivo o comando para alternar al panel de administración de empleados. */
	public static final String GESTION_EMPLEADOS = "Gestión de Empleados";
	/** Texto descriptivo o comando para alternar al panel de administración de artículos. */
	public static final String GESTION_PRODUCTOS = "Gestión de Productos";
	/** Texto descriptivo o comando para acceder al control de existencias e inventario de almacén. */
	public static final String GESTION_STOCK = "Gestión del Stock";
	/** Texto descriptivo o comando para alternar a la vista del histórico de auditoría de ventas. */
	public static final String VER_TRANSACCIONES = "Ver Transacciones";
	/** Texto descriptivo o comando para proceder a la confirmación final de la orden de compra. */
	public static final String COMPRAR = "Comprar";
	/** Texto descriptivo o comando para restaurar la visibilidad comercial de un producto. */
	public static final String HABILITAR_PRODUCTO = "Habilitar Producto";
	/** Texto descriptivo o comando para ocultar o congelar un artículo del catálogo. */
	public static final String DESHABILITAR_PRODUCTO = "Deshabilitar Producto";
	/** Texto descriptivo o comando para acceder a la sección de edición del perfil de usuario. */
	public static final String MI_CUENTA = "Mi cuenta";
	/** Texto descriptivo o comando para guardar los datos modificados de la cuenta del comprador. */
	public static final String MODIFICAR_COMPRADOR = "Modificar Perfil";
	/** Texto descriptivo o comando para que el cliente solicite la desactivación autónoma de su cuenta. */
	public static final String DARSE_DE_BAJA = "Darse de Baja";
	/** Texto descriptivo o comando para reactivar los permisos de acceso de un usuario. */
	public static final String HABILITAR_USUARIO = "Habilitar Usuario";
	/** Texto descriptivo o comando para suspender de forma temporal o indefinida el acceso a un usuario. */
	public static final String DESHABILITAR_USUARIO = "Deshabilitar Usuario";
	/** Texto descriptivo o comando para alternar a la pantalla de control global de las cuentas de usuario. */
	public static final String GESTION_USUARIOS = "Gestión de Usuarios";
	/** Texto descriptivo o comando para procesar filtros específicos en el histórico de transacciones. */
	public static final String BUSCAR_TRANSACCION = "Buscar Transacción";
	
}