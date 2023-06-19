package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;
    @Column(name = "userName",unique = true,nullable = false)
    private String userName;
    @Size(min = 6,message = "Mật Khẩu trên 6 kí tự")
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "created")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
    @Email(message = "Email không đúng định dạng")
    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "phone")
    @Pattern(regexp = "^0\\d{9}$",message = "Không đúng định dạng, bắt đầu bằng số 0 và gồm 9 số đằng sau")
    private String phone;
    @Column(name = "userStatus")
    private boolean userStatus;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns =@JoinColumn(name = "userId"),
    inverseJoinColumns =@JoinColumn(name = "roleId"))
    private Set<Roles> listRoles = new HashSet<>();
}
