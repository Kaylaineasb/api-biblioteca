package com.biblioteca.api_biblioteca.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Async
    public void sendEmail(String destinatario,String token) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("Recuperação de Senha - BiblioTech");

            String link = "http://localhost:4200/reset-password?token=" + token;

            String htmlContent = """
                <div style="font-family: sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f3f4f6;">
                    <div style="background-color: #ffffff; padding: 40px; border-radius: 16px; text-align: center; box-shadow: 0 4px 15px rgba(0,0,0,0.05);">
                        <h2 style="color: #1f2937; margin-bottom: 20px;">Recuperar Senha</h2>
                        <p style="color: #6b7280; font-size: 16px; margin-bottom: 30px;">
                            Recebemos uma solicitação para redefinir a senha da sua conta.
                            Clique no botão abaixo para criar uma nova senha:
                        </p>
                        
                        <a href="%s" style="background-color: #4f46e5; color: #ffffff; padding: 12px 24px; border-radius: 8px; text-decoration: none; font-weight: bold; display: inline-block;">
                            Redefinir Minha Senha
                        </a>
                        
                        <p style="color: #9ca3af; font-size: 12px; margin-top: 30px;">
                            Se você não solicitou isso, pode ignorar este e-mail com segurança.
                            O link expira em 30 minutos.
                        </p>
                    </div>
                </div>
            """.formatted(link);

            helper.setText(htmlContent,true);

            mailSender.send(message);
            System.out.println("Email enviado com sucesso para: " + destinatario);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
