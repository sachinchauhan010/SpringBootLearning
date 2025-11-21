package org.learning.ecom.exceptions;

public class ResourceNotFound extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFound() {
    }

    public ResourceNotFound(String resourceName, String fieldName, String field) {
        super(String.format("%s not found with %s: %s", fieldName, field, resourceName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }
    public ResourceNotFound(String resourceName,  String field, Long fieldId) {
        super(String.format("%s not found with %s: %d", field, resourceName, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }


}
