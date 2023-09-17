//package com.springboot.UserManagementSystem.configs;
//
//import com.springboot.UserManagementSystem.dao.UserRepository;
//import com.springboot.UserManagementSystem.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = this.userRepository.getUserByEmail(username);
//        if (user==null){
//            throw new UsernameNotFoundException("User not found with this email!!");
//        }
//        CustomUserDetails customUserDetails = new CustomUserDetails(user);
//        return customUserDetails;
//    }
//}
