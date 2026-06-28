package com.example.demo.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.LoginSolicitud;
import com.example.demo.DTO.RegistroClienteSolicitud;
import com.example.demo.DTO.RespuestaApi;
import com.example.demo.DTO.VerificarTokenSolicitud;
import com.example.demo.Service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * CONTROLADOR DE AUTENTICACIÓN (LOGIN / REGISTER / LOGOUT)
 *
 * USO FRONTEND (ANGULAR):
 *
 * 1. POST /api/auth/registro → Registrar cliente nuevo - El cliente llena
 * formulario de registro - Backend envía email con token de verificación -
 * Frontend muestra MODAL pidiendo el código de 6 dígitos
 *
 * 2. POST /api/auth/verificar-token → Verificar email con token - Cliente
 * ingresa código del email en el modal - Si coincide → registro completo,
 * redirigir a login - Si no → mostrar error, opción de reenviar
 *
 * 3. POST /api/auth/login → Iniciar sesión (todos los roles) - Misma pantalla
 * de login para ADMIN, VETERINARIO y CLIENTE - Backend devuelve rol → Angular
 * redirige según corresponda: • ADMIN → /admin/veterinarios • VETERINARIO →
 * /veterinario/citas-pendientes • CLIENTE → /cliente/mis-mascotas - IMPORTANTE:
 * Angular debe usar { withCredentials: true }
 *
 * 4. POST /api/auth/logout → Cerrar sesión - Botón "Cerrar Sesión" en el navbar
 * de todas las pantallas - Después de logout, limpiar localStorage y redirigir
 * a /login
 *
 * 5. GET /api/auth/perfil → Obtener datos del usuario logueado - Usar al cargar
 * la app para mantener sesión activa - Si devuelve error → redirigir a login
 * (sesión expirada)
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

    private final UsuarioService usuarioServicio;
    private final PasswordEncoder codificadorPassword;

    /**
     * REGISTRO DE CLIENTE - PASO 1
     *
     * PARA ANGULAR: - URL: POST http://localhost:8080/api/auth/registro - Body:
     * { "correo": "...", "contrasena": "...", "nombres": "...", "apellidos":
     * "...", "telefono": "...", "dni": "..." } - Respuesta exito=true → mostrar
     * modal con input para token - Respuesta exito=false → mostrar mensaje de
     * error (correo ya existe, etc.)
     */
    @PostMapping("/registro")
    public ResponseEntity<RespuestaApi> registrar(@Valid @RequestBody RegistroClienteSolicitud solicitud) {
        return ResponseEntity.ok(usuarioServicio.registrarCliente(solicitud));
    }

    /**
     * VERIFICAR TOKEN - PASO 2
     *
     * PARA ANGULAR: - URL: POST http://localhost:8080/api/auth/verificar-token
     * - Body: { "correo": "...", "token": "123456" } - Desde el MODAL de
     * verificación - Respuesta exito=true → "Registro exitoso", redirigir a
     * /login - Respuesta exito=false → mostrar error, botón "Reenviar código"
     */
    @PostMapping("/verificar-token")
    public ResponseEntity<RespuestaApi> verificarToken(@Valid @RequestBody VerificarTokenSolicitud solicitud) {
        return ResponseEntity.ok(usuarioServicio.verificarToken(solicitud));
    }

    /**
     * LOGIN (Todos los roles usan el mismo endpoint)
     *
     * PARA ANGULAR: - URL: POST http://localhost:8080/api/auth/login - Body: {
     * "correo": "...", "contrasena": "..." } - withCredentials: true ←
     * OBLIGATORIO - Respuesta exito=true: { "exito": true, "mensaje": "Login
     * exitoso", "datos": { "rol": "CLIENTE", // ← Usar esto para redirección
     * "correo": "...", "nombres": "...", "clienteId": 1 // ← Guardar para
     * peticiones futuras } } - Redirección según rol: if (datos.rol ===
     * 'ADMIN') → this.router.navigate(['/admin/veterinarios']) if (datos.rol
     * === 'VETERINARIO') → this.router.navigate(['/veterinario/citas']) if
     * (datos.rol === 'CLIENTE') → this.router.navigate(['/cliente/mascotas'])
     */
    @PostMapping("/login")
    public ResponseEntity<RespuestaApi> login(@Valid @RequestBody LoginSolicitud solicitud, jakarta.servlet.http.HttpServletRequest request) {
        return ResponseEntity.ok(usuarioServicio.login(solicitud, request));
    }

    /**
     * LOGOUT
     *
     * PARA ANGULAR: - URL: POST http://localhost:8080/api/auth/logout -
     * withCredentials: true - Llamar al hacer clic en "Cerrar Sesión" -
     * Después: localStorage.clear() y redirigir a /login
     */
    @PostMapping("/logout")
    public ResponseEntity<RespuestaApi> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok(usuarioServicio.logout());
    }

    /**
     * OBTENER PERFIL DEL USUARIO LOGUEADO
     *
     * PARA ANGULAR: - URL: GET http://localhost:8080/api/auth/perfil -
     * withCredentials: true - Usar en: • app.component.ts al iniciar la app
     * (recuperar sesión) • Guardias de rutas (verificar autenticación) • Navbar
     * (mostrar nombre del usuario) - Si devuelve 401 o exito=false → sesión
     * expirada, redirigir a login
     */
    @GetMapping("/perfil")
    public ResponseEntity<RespuestaApi> obtenerPerfil() {
        return ResponseEntity.ok(usuarioServicio.obtenerPerfil());
    }
}
