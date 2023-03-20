package com.codingdojo.services;


import com.codingdojo.models.Role;
import com.codingdojo.models.User;
import com.codingdojo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailsServiceImplentation implements UserDetailsService {

    private UserRepository userRepository;

    /*loadUserByUserName(String username): Encuentra al usuario por su nombre de usuario.
    Si se encuentra un usuario, lo devuelve con las autoridades correspondientes.
    Si el nombre de usuario no existe, el método arroja un error.*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional <User> user=userRepository.findByUserName(username);

       if(!user.isPresent()){
           throw new UsernameNotFoundException("User not found");
       }
       return new org.springframework.security.core.userdetails.User(user.get().getUserName(),user.get().getPassword(),
               getAuthorities(user.get()));
    }
    /*getAuthorities(User user): devuelve una lista de autorizaciones/permisos para un usuario específico.
    Por ejemplo, nuestros clientes pueden ser 'user', 'admin' o ambos.
    Para que Spring Security implemente la autorización, debemos obtener el nombre de los roles posibles para
    el usuario actual de nuestra base de datos y crear un nuevo objeto `SimpleGrantedAuthority' con esos roles.*/
    private List<GrantedAuthority> getAuthorities(User user){
     /*   return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
*/
        return user.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
   /*
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(grantedAuthority);
        }
        return authorities;
*/
    }


}
