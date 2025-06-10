# 🚀 CletaEats - Delivery App (Kotlin)  

**¡Pedidos rápidos, sabrosos y sobre ruedas!**  

CletaEats es una aplicación móvil desarrollada en **Kotlin**
La app ofrece una experiencia fluida para realizar pedidos de combos, gestionar repartidores y generar facturas automatizadas, todo con un diseño moderno y eficiente.  

---

## 📱 **Funcionalidades clave**  

### 🍔 **Para Clientes**  
- **Registro/Login seguro**: Autenticación de usuarios con estado (activo/suspendido).  
- **Selección de restaurantes**: 7 opciones con tipos de comida (china, rápida, saludable, etc.).  
- **Pedidos personalizados**: Combos del 1 al 9 (precios desde ₡4000 hasta ₡12000).  
- **Facturación automática**: Incluye subtotal, IVA (13%), costo de transporte y total.  

### 🚴 **Para Repartidores**  
- **Gestión de entregas**: Asignación automática (prioriza disponibilidad y amonestaciones < 4).  
- **Tarifas dinámicas**: ₡1000/km (días hábiles) o ₡1500/km (feriados).  
- **Registro de quejas**: Trazabilidad de amonestaciones por calificaciones de clientes.  

### 📊 **BackEnd y Persistencia**  
- **Arquitectura multicapa**: Datos, lógica de negocio, control y modelo.  
- **Persistencia en MYSQL**: Guarda clientes, restaurantes, repartidores y pedidos.
- **Reportes empresariales**:  
  - Restaurantes más/menos populares.    
  - Montos totales vendidos.  

---

## 🛠️ **Tecnologías y Requisitos**  
- **Lenguaje**: Kotlin (Android Studio).  
- **FrontEnd**: Jetpack Compose, Navigation Drawer, RecyclerView.  
- **Patrones de diseño**: MVC o MVVM (según módulo 1 del curso). 
