package entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "logs", schema = "public", catalog = "LoginProject")
public class EntityLogs {
    private int id;
    private int userId;
    private boolean success;
    private String cause;
    private Timestamp date;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "success", nullable = false)
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Basic
    @Column(name = "cause", nullable = true, length = -1)
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityLogs that = (EntityLogs) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (success != that.success) return false;
        if (cause != null ? !cause.equals(that.cause) : that.cause != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + (cause != null ? cause.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
