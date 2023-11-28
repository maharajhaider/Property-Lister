package ca.ubc.cs304.model;

public interface HasID extends EntityModel {
    String getIdSQL();
    Integer defaultId();
}
