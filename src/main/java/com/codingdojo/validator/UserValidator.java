package com.codingdojo.validator;


import com.codingdojo.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

//especifica que una instancia del modelo de dominio de User se puede validar con este validador personalizado
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

//crear nuestra validación personalizada. Podemos agregar errores a través de .rejectValue(String, String).
    @Override
    public void validate(Object object, Errors errors) {

        User user = (User) object;

        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
           //el primer argumento es la variable miembro de nuestro modelo de dominio que estamos validando.
            // El segundo argumento es un código que usamos para establecer un mensaje de error.
            errors.rejectValue("passwordConfirmation", "Match");
        }
    }
}
