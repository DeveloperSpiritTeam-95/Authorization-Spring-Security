package authentication.com.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class AuthUser {
    @Id
    private String id;
    private String userName;
    private String password;
    private boolean active = false;
}

