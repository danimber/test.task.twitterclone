package test.task.twitterclone.payload

import lombok.AccessLevel
import lombok.Data
import lombok.experimental.FieldDefaults

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class SignupRequest {
    String username
    String email
    String password
}
