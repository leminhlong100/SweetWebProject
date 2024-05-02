package site.sugarnest.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private Long id;

    private String accountName;

    private String password;

    private String fullName;

    private Date birthday;

    private String address;

    private String email;

    private String phone;

    private String isDelete;

    private String isActive;

    private Date createAt;

    private String image;

    private Date updateAt;

    private Date deleteAt;

    private Integer type;

    private String idOther;

    private String currentPassword;

    private Date timestamp;

    private Integer number_login_fail;


}
