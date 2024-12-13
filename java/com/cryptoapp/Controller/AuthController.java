package com.cryptoapp.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptoapp.dtos.LoginUserDto;
import com.cryptoapp.dtos.RegisterUserDto;
import com.cryptoapp.entities.User;
import com.cryptoapp.req_res.AuthResponse;
import com.cryptoapp.services.UserService;
import com.cryptoapp.services.WatchlistService;
import com.cryptoapp.servicesimpl.JwtService;
import com.cryptoapp.servicesimpl.OtpService;
import com.cryptoapp.servicesimpl.SecurityUserDetailService;



@RestController
@RequestMapping("/auth")


public class AuthController {

     Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
   
    @Autowired
    private JwtService jwtService;

    @Autowired
    private SecurityUserDetailService securityUserDetailService;

  @Autowired
  private WatchlistService watchlistService;

  @Autowired
  private OtpService otpService;

    // @GetMapping("/home")
    // public String home(){
    //     return "home";
    // }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody RegisterUserDto registerUserDto,BindingResult bindingResult ) throws Exception{
         if (bindingResult.hasErrors()) {
                    // Collect validation errors
                    Map<String, String> errors = new HashMap<>();
                    bindingResult.getFieldErrors().forEach(error -> 
                        errors.put(error.getField(), error.getDefaultMessage())
                    );
                    return ResponseEntity.badRequest().body(new AuthResponse());
                }
        


        if(userService.userExist(registerUserDto.getEmail())){
            throw new Exception("Username is aleready Used Pleaselogin");
        }

        User newUser=new User();
        newUser.setName(registerUserDto.getName());
        newUser.setEmail(registerUserDto.getEmail());
        newUser.setPassword(registerUserDto.getPassword());
        newUser.setPhonenumber(registerUserDto.getPhonenumber());

        User savedUser=userService.saveUser(newUser);
otpService.generateOtp(registerUserDto.getEmail());
        watchlistService.createWatchlist(savedUser);
      
        Authentication auth= new UsernamePasswordAuthenticationToken(
            registerUserDto.getEmail(),
            registerUserDto.getPassword()
            
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
   
            String jwt=jwtService.generateToken(auth);
        
       AuthResponse res=new AuthResponse();
     res.setJwt(jwt);
       res.setStatus(true);
       res.setMessage("Registered succesfully");
       
        return new ResponseEntity(res,HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginUserDto> login(@RequestBody LoginUserDto loginUserDto,Authentication authentication ) throws Exception{
            String username=loginUserDto.getEmail();
            String paasword=loginUserDto.getPassword();

          
            
        
         if(!userService.getByEmail(username).isPresent()){
            throw new Exception("User not found");
        }
        Authentication auth=authenticate(username,paasword);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt=jwtService.generateToken(auth);
        
        
        AuthResponse res=new AuthResponse();
       res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login succesfully");
 
         return new ResponseEntity(res,HttpStatus.CREATED);
       
    }


    private Authentication authenticate(String username, String paasword) throws Exception {
        UserDetails userDetails = securityUserDetailService.loadUserByUsername(username);

            if(userDetails==null){
                throw new Exception("Invalid username ");
            }
            if(!paasword.equals(userDetails.getPassword())){
                throw new Exception("Invalid password");
            }


          return new UsernamePasswordAuthenticationToken(userDetails,paasword,userDetails.getAuthorities());

    }




    
}
