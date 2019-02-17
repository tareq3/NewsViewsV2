
package com.mti.newviewsv2.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Fortune {

    @SerializedName("articles")
    private List<Article> mArticles;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalResults")
    private Long mTotalResults;

    public List<Article> getArticles() {
        return mArticles;
    }

    public String getStatus() {
        return mStatus;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public static class Builder {

        private List<Article> mArticles;
        private String mStatus;
        private Long mTotalResults;

        public Fortune.Builder withArticles(List<Article> articles) {
            mArticles = articles;
            return this;
        }

        public Fortune.Builder withStatus(String status) {
            mStatus = status;
            return this;
        }

        public Fortune.Builder withTotalResults(Long totalResults) {
            mTotalResults = totalResults;
            return this;
        }

        public Fortune build() {
            Fortune fortune = new Fortune();
            fortune.mArticles = mArticles;
            fortune.mStatus = mStatus;
            fortune.mTotalResults = mTotalResults;
            return fortune;
        }

    }

}
