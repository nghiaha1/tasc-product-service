package com.tasc.productservice.database.specification;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchBody {
    private int page;

    private int pageSize;

    private String name;

    private String description;

    private String sort;

    private int isRoot;

    public final static class SearchBodyBuilder {
        private int page;

        private int limit;

        private String name;

        private String description;

        private String sort;

        private int isRoot;

        private SearchBodyBuilder() {
        }

        public static SearchBodyBuilder aSearchBody() {
            return new SearchBodyBuilder();
        }

        public SearchBodyBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public SearchBodyBuilder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public SearchBodyBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SearchBodyBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SearchBodyBuilder withSort(String sort) {
            this.sort = sort;
            return this;
        }

        public SearchBodyBuilder withIsRoot(int isRoot) {
            this.isRoot = isRoot;
            return this;
        }

        public SearchBody build() {
            SearchBody searchBody = new SearchBody();
            searchBody.setPage(page);
            searchBody.setPageSize(limit);
            searchBody.setName(name);
            searchBody.setIsRoot(isRoot);
            searchBody.setDescription(description);
            searchBody.setSort(sort);

            return searchBody;
        }
    }
}
