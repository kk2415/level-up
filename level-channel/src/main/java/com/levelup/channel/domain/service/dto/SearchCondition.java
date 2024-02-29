package com.levelup.channel.domain.service.dto;

import lombok.Getter;

@Getter
public class SearchCondition {

    public static String TITLE = "title";
    public static String WRITER = "writer";

    private String field;
    private String query;

    protected SearchCondition() {}

    private SearchCondition(String field, String query) {
        this.field = field;
        this.query = query;
    }

    public static SearchCondition of(String field, String query) {
        return new SearchCondition(field, query);
    }

    public boolean isTitleSearch() {
        return TITLE.equals(field) && !("".equals(query));
    }

    public boolean isWriterSearch() {
        return WRITER.equals(field) && !("".equals(query));
    }
}
