package com.verdianc.wisiee.Entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FormFieldId implements Serializable {
    private String formId;
    private int order;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FormFieldId that)) return false;
        return order==that.order && Objects.equals(formId, that.formId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, order);
    }

    public FormFieldId() {
    } // JPA용 기본 생성자

    public FormFieldId(String formId, int order) {
        this.formId = formId;
        this.order = order;
    }
}
