package sn.ucad.restou.Exception;

public class ResourceNotFoundException extends RuntimeException {
    private String resource;
    private String champ;
    private Object value;

    public ResourceNotFoundException(String resource, String champ,
            Object value) {
        super(String.format("%s not found with %s : '%s'", resource,
                champ, value));
        this.resource = resource;
        this.champ = champ;
        this.value = value;
    }

    public String getResource() {
        return resource;
    }

    public String getChamp() {
        return champ;
    }

    public Object getValue() {
        return value;
    }
}