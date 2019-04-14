package com.masivian.example.treeoperations;

public class Router {
    public static final String HEALTH = "/health";
    public static final String SCORE = "/score";
    public static final String BOOST = "/boost";
    public static final String TREE = "/tree";
    public static final String SCHEDULE = "/schedule";
    public static final String AVAILABLE = "/available";
    public static final String BOOST_CORE = BOOST + "/core";
    public static final String MASTER_CSV = BOOST + "/csv";
    public static final String CITY_BOOST = TREE + BOOST;
    public static final String BY_CITY = "/{cityId}";
    public static final String BY_ID = "/{id}";
    public static final String MULTIPLE = "/multiple";
    public static final String UPDATE_ENABLED = "/{id}/{enabled}";
}
