package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

public record City(Province province, String name, Double taxRate) {}
