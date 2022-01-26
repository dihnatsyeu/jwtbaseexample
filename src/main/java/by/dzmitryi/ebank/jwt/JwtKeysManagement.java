package by.dzmitryi.ebank.jwt;

import by.dzmitryi.ebank.constants.JWTConstant;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

class JwtKeysManagement {

    static SecretKey getSecretKeyEncrypted() {
        return Keys.hmacShaKeyFor(JWTConstant.JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
