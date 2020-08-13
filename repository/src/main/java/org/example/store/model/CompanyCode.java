package org.example.store.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "companyCodes", schema = "public")
public class CompanyCode extends AbstractEntity {

    @Column(name = "code", nullable = false, unique = true, length = 2)
    private String code;


    public CompanyCode() {
    }

    public CompanyCode(Long id) {
        super(id);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompanyCode other = (CompanyCode) o;
        return Objects.equals(code, other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code);
    }

    @Override
    public String toString() {
        return "CompanyCode{" +
                super.toString() +
                ", code='" + code + '\'' +
                '}';
    }
}
