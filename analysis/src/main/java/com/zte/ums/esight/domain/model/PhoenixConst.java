package com.zte.ums.esight.domain.model;

import com.zte.ums.esight.domain.config.EnvConfiguration;

public interface PhoenixConst {
	String JDBC_URL= EnvConfiguration.jdbcUrl();
	String DEFAULT_COLLECTTIME= "N/A";
	String BLANK= " ";
	String TWO_BLANK= BLANK + BLANK;
	String FOUR_BLANK= TWO_BLANK + TWO_BLANK;
	String SIX_BLANK= TWO_BLANK + TWO_BLANK + TWO_BLANK;
	String NEW_LINE = "\n";
	String MAX = "MAX";
	String MIN = "MIN";
	int TOPN= 5;
}
