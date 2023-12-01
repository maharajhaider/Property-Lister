package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

public record CityPropertyCount(Province province, String cityName, Integer properties) {}
