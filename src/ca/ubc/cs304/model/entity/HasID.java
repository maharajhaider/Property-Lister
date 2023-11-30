package ca.ubc.cs304.model.entity;

public interface HasID extends EntityModel {
    String getIdSQL();
    Integer defaultId();
}
