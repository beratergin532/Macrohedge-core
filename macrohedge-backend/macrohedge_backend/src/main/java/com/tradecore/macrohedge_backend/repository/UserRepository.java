package com.tradecore.macrohedge_backend.repository;

import com.tradecore.macrohedge_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Email adresine göre kullanıcı arama (Giriş/Login işlemleri için)
    Optional<User> findByEmail(String email);
    
    // Email adresi sistemde zaten var mı kontrolü
    boolean existsByEmail(String email);
}