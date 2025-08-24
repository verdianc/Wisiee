package com.verdianc.wisiee.Entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FormFieldId implements Serializable {
    private Long formId;
    private int sortOrder;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FormFieldId that)) return false;
        return sortOrder==that.sortOrder && Objects.equals(formId, that.formId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, sortOrder);
    }

    public FormFieldId() {
    } // JPA용 기본 생성자

    public FormFieldId(Long formId, int order) {
        this.formId = formId;
        this.sortOrder = order;
    }
}
