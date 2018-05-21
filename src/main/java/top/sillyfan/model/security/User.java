package top.sillyfan.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {

    @Id
    private Long id;

    private String username;

    private String password;

    private String email;

    @Builder.Default
    private List<String> authorizes = new ArrayList<>();

    @Builder.Default
    private Boolean enabled = true;

    private Date lastPasswordResetDate;
}
