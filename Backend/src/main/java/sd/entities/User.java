package sd.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Role is required")
    @Column(nullable = false)
    private String role;


    @Column(nullable = false)
    private int weeklyRequestCount;


    @Column(nullable = false)
    private LocalDateTime lastRequestTime;



    public User() {
        this.lastRequestTime = LocalDateTime.now(); 
    }

    public boolean isAdmin() {
        return "admin".equals(role);
    }

    public boolean isPremium() {
        if(getRole().equals("Premium")){
            return true;
        }else{
            return false;
        }
    }
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getWeeklyRequestCount() {
        return weeklyRequestCount;
    }

    public void setWeeklyRequestCount(int weeklyRequestCount) {
        this.weeklyRequestCount = weeklyRequestCount;
    }
}
