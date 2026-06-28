package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * SERVICIO CORREO
 *
 * USO FRONTEND: Este servicio se ejecuta automáticamente cuando un cliente se
 * registra. El frontend no lo llama directamente.
 *
 * FUNCIONALIDAD: Envía el email con el código de verificación al cliente. El
 * cliente recibe un HTML bonito con el token de 6 dígitos.
 */
@Service
@RequiredArgsConstructor
public class CorreoService {

    private final JavaMailSender enviadorCorreo;
    private final TemplateEngine motorPlantillas;

    @Value("${spring.mail.username}")
    private String correoRemitente;

    /**
     * Envía correo de verificación con token.
     *
     * PARA EL FRONTEND: No necesitas llamar esto. Se ejecuta automáticamente
     * cuando el cliente hace POST /api/auth/registro.
     *
     * @param correoDestino Email del cliente que se registra
     * @param token Código de 6 dígitos que debe ingresar en el modal
     * @param nombres Nombre del cliente para personalizar el email
     */
    public void enviarCorreoVerificacion(String correoDestino, String token, String nombres) {
        try {
            String contenidoHtml = """
                <html>
                <body style="font-family: Arial, sans-serif; text-align: center; background-color: #f4f4f4; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 30px; border-radius: 10px;">
                        <h1 style="color: #4CAF50;">¡Hola %s!</h1>
                        <p>Tu código de verificación es:</p>
                        <h1 style="color: #4CAF50; font-size: 48px; letter-spacing: 10px; background-color: #f0f8f0; padding: 20px; border-radius: 10px;">%s</h1>
                        <p>Este código expira en <strong>15 minutos</strong>.</p>
                        <p style="color: #666; font-size: 12px; margin-top: 30px;">Veterinaria - Todos los derechos reservados</p>
                    </div>
                </body>
                </html>
                """.formatted(nombres, token);

            MimeMessage mensaje = enviadorCorreo.createMimeMessage();
            MimeMessageHelper ayudante = new MimeMessageHelper(mensaje, true, "UTF-8");

            ayudante.setFrom(correoRemitente);
            ayudante.setTo(correoDestino);
            ayudante.setSubject("Código de Verificación - Veterinaria");
            ayudante.setText(contenidoHtml, true);

            enviadorCorreo.send(mensaje);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage());
        }
    }
}
