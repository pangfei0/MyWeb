package juli.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by LYL on 15/12/27.
 */
@Entity
@Table(name = "user_elevator")
public class UserElevator extends BaseEntity {
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String elevatorId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }
}
