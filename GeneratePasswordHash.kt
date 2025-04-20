import org.springframework.security.crypto.bcrypt.BCrypt

fun main() {
    val rawPassword = "lezhin123!"
    // Using the default work factor of 10, which appears to be used in the existing hashes
    val salt = BCrypt.gensalt(10) 
    val hashedPassword = BCrypt.hashpw(rawPassword, salt)
    
    println("Raw password: $rawPassword")
    println("Hashed password: $hashedPassword")
}