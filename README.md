# 🚀 CletaEats - Delivery App (Kotlin)  

<div align="center">
  <img src="https://img.freepik.com/vector-gratis/plantilla-logotipo-repartidor-comida_23-2148686840.jpg" width="200">
  <h2>¡Pedidos rápidos, sabrosos y sobre ruedas! 🍔🚴‍♂️</h2>
</div>

CletaEats es una aplicación móvil desarrollada en **Kotlin** que ofrece una experiencia fluida para realizar pedidos de comida. Consume la API: [🔗 Api-FoodDelivery](https://github.com/Manuel01011/Api-_foodDelivery)

---

## 🌟 **Funcionalidades clave**  

### 👨‍🍳 **Experiencia del Cliente**
| Característica | Icono | Detalle |
|---------------|-------|---------|
| **Autenticación** | 🔐 | Registro/Login con validación de estado |
| **Restaurantes** | 🏪 | 7 categorías (china, rápida, saludable, etc.) |
| **Combos** | 🍱 | 9 opciones (₡4,000 - ₡12,000) |
| **Facturación** | 🧾 | Cálculo automático con IVA 13% |

```kotlin
// Ejemplo cálculo de factura
fun calcularTotal(subtotal: Double): Double {
    val iva = 0.13
    val transporte = 1000 // ₡/km
    return subtotal + (subtotal * iva) + transporte
}
